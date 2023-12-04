package com.hallym.cloud.cloudpotato.api;

import com.hallym.cloud.cloudpotato.domain.ReviewInfo;
import com.hallym.cloud.cloudpotato.dto.Result;
import com.hallym.cloud.cloudpotato.dto.bookdetail.*;
import com.hallym.cloud.cloudpotato.service.ReviewInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookdetailApi {

    private final ReviewInfoService reviewInfoService;
    private final ClovaSentiment clovaSentiment;

    @GetMapping("/bookdetail/{isbn}")
    public EmojiCountResponse emojiCountApi(@PathVariable("isbn") String isbn) {
        long emoji1Count = reviewInfoService.countByIsbnEmoji(isbn, "😄");
        long emoji2Count = reviewInfoService.countByIsbnEmoji(isbn, "😭");
        long emoji3Count = reviewInfoService.countByIsbnEmoji(isbn, "🥹");
        long emoji4Count = reviewInfoService.countByIsbnEmoji(isbn, "🥱");
        long emoji5Count = reviewInfoService.countByIsbnEmoji(isbn, "😡");
        long emoji6Count = reviewInfoService.countByIsbnEmoji(isbn, "😔");
        long emoji7Count = reviewInfoService.countByIsbnEmoji(isbn, "😍");

        return new EmojiCountResponse(emoji1Count, emoji2Count, emoji3Count, emoji4Count, emoji5Count, emoji6Count, emoji7Count);
    }

    @PostMapping("/bookdetail/new")
    public CommentResponse commentNew(@RequestBody CreateCommentRequest request) {

        LocalDateTime create_date = LocalDateTime.now().withNano(0);

        String resultAI = "";
        if(request.getResultAI().equals("positive")) {
            resultAI = "긍정";
        } else if(request.getResultAI().equals("negative")) {
            resultAI = "부정";
        } else {
            resultAI = "중립";
        }

        ReviewInfo reviewInfo = new ReviewInfo(request.getName(), create_date, request.getReviewPw(), request.getIsbn(),
                request.getContent(), request.getEmoji(), resultAI);

        reviewInfoService.saveReviewInfo(reviewInfo);

        return new CommentResponse("create comment success");
    }

    @GetMapping("/bookdetail/editForm/{reviewId}")
    public EditFormResponse commentEditForm(@PathVariable("reviewId") long reviewId) {
        ReviewInfo reviewInfo = reviewInfoService.findReviewInfo(reviewId);
        return new EditFormResponse(reviewInfo.getUserName(), reviewInfo.getContent(), reviewInfo.getEmoji());
    }

    @PutMapping("/bookdetail/edit/{reviewId}")
    public CommentResponse commentEdit(@PathVariable("reviewId") long reviewId,
                                       @RequestBody EditCommentRequest request) {

        String resultAI = "";
        if(request.getResultAI().equals("positive")) {
            resultAI = "긍정";
        } else if(request.getResultAI().equals("negative")) {
            resultAI = "부정";
        } else {
            resultAI = "중립";
        }

        System.out.println("========================" + resultAI + "===================");

        String responseMessage = reviewInfoService.editReviewComment(reviewId, request.getContent(), request.getEmoji(), resultAI);
        return new CommentResponse(responseMessage);
    }

    @PostMapping("/bookdetail/delete/{reviewId}")
    public CommentResponse commentDelete(@PathVariable("reviewId") long reviewId,
                                         @RequestBody DeleteCommentRequest request) {
        String responseMessage = reviewInfoService.deleteReviewComment(reviewId, request.getPasswd());
        return new CommentResponse(responseMessage);
    }

    @GetMapping("/bookdetail/list/{isbn}")
    public Result commentList(@PathVariable("isbn") String isbn) {
        List<ReviewInfo> allReviewInfo = reviewInfoService.findAllReviewInfo(isbn);
        List<ReviewInfoDto> collect = allReviewInfo.stream()
                .map(m -> new ReviewInfoDto(m.getReviewId(), m.getUserName(), m.getContent(), m.getEmoji(), m.getResultAI()))
                .toList();

        return new Result(collect);
    }

    @GetMapping("/bookdetail/passwd")
    public CommentResponse editPasswdCheck(@RequestParam("reviewId") long reviewId,
                                           @RequestParam("passwd") String passwd) {
        ReviewInfo reviewInfo = reviewInfoService.findReviewInfo(reviewId);
        if(passwd.equals(reviewInfo.getReviewPw())) {
            return new CommentResponse("true");
        }
        return new CommentResponse("false");
    }

    @GetMapping("/booksDetail/{isbn}")
    public Result booksDetail(@PathVariable("isbn") String isbn) {
        AladinApi aapi = new AladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        BooksDetailDto collect = new BooksDetailDto();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbhyejmh1853001&itemIdType=ISBN13&ItemId="+isbn+"&output=xml&Version=20131101&O&&Cover=Big";

            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            Document doc = dBuilder.parse(url);

            // 제일 첫번째 태그
            doc.getDocumentElement().normalize();

            // 파싱할 tag
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                Element eElement = (Element) nNode;

                collect = new BooksDetailDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("pubDate", eElement),aapi.getTagValue("description", eElement),aapi.getTagValue("isbn13", eElement), aapi.getTagValue("cover", eElement), aapi.getTagValue("categoryName", eElement), aapi.getTagValue("publisher", eElement));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }
}
