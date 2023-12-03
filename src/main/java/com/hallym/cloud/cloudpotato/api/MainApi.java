package com.hallym.cloud.cloudpotato.api;

import com.hallym.cloud.cloudpotato.dto.BestsellerResultDto;
import com.hallym.cloud.cloudpotato.dto.Result;
import com.hallym.cloud.cloudpotato.service.ReviewInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainApi {
    private final ReviewInfoService reviewInfoService;

    private static List<String> resizeList(List<String> originalList, int maxSize) {
        // 리스트의 크기를 최대 18개로 제한
        int newSize = Math.min(originalList.size(), maxSize);

        // 새로운 리스트 생성
        List<String> resizedList = new ArrayList<>(originalList.subList(0, newSize));

        return resizedList;
    }

    @GetMapping("/bestseller")
    public Result aladinBestseller() {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        List<BestsellerResultDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbhyejmh1853001&QueryType=Bestseller&MaxResults=18&start=1&SearchTarget=Book&output=xml&Version=20131101&Cover=Big";

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

                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement),aapi.getTagValue("cover", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }

    @GetMapping("/main/1/{emoji}")
    public Result sortByEmoji(@PathVariable("emoji") String emoji){
        List<String> isbns = reviewInfoService.findByEmoji(emoji);
        List<String> convertIsbns = resizeList(isbns, 18);
        List<BestsellerResultDto> collect = new ArrayList<>(convertIsbns.size());

        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        try {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            for(int i=0;i<collect.size();i++) {
                // parsing할 url 지정(API 키 포함해서)
                String url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbhyejmh1853001&itemIdType=ISBN13&SearchTarget=Book&ItemId=" + convertIsbns.get(i) + "&output=xml&Version=20131101&Cover=Big";

                Document doc = dBuilder.parse(url);

                // 제일 첫번째 태그
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                Node nNode = nList.item(0);
                Element eElement = (Element) nNode;
                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement),aapi.getTagValue("cover", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }

    @GetMapping("/main/3")
    public Result sortByEmojis(){
        List<String> isbns = reviewInfoService.findByEmojis();
        List<String> convertIsbns = resizeList(isbns, 18);
        List<BestsellerResultDto> collect = new ArrayList<>(convertIsbns.size());

        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        try {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            for(int i=0;i<collect.size();i++) {
                // parsing할 url 지정(API 키 포함해서)
                String url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbhyejmh1853001&itemIdType=ISBN13&SearchTarget=Book&ItemId=" + convertIsbns.get(i) + "&output=xml&Version=20131101&Cover=Big";

                Document doc = dBuilder.parse(url);

                // 제일 첫번째 태그
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                Node nNode = nList.item(0);
                Element eElement = (Element) nNode;
                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement),aapi.getTagValue("cover", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }

    @GetMapping("/main/4")
    public Result sortByReviews(){
        List<String> isbns = reviewInfoService.findByReviews();
        List<String> convertIsbns = resizeList(isbns, 18);
        List<BestsellerResultDto> collect = new ArrayList<>(convertIsbns.size());

        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        String key = "";
        try {
            DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
            for(int i=0;i<collect.size();i++) {
                // parsing할 url 지정(API 키 포함해서)
                String url = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbhyejmh1853001&itemIdType=ISBN13&SearchTarget=Book&ItemId=" + convertIsbns.get(i) + "&output=xml&Version=20131101&Cover=Big";

                Document doc = dBuilder.parse(url);

                // 제일 첫번째 태그
                doc.getDocumentElement().normalize();

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");

                Node nNode = nList.item(0);
                Element eElement = (Element) nNode;
                BestsellerResultDto br = new BestsellerResultDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement),aapi.getTagValue("cover", eElement));
                collect.add(br);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }
}
