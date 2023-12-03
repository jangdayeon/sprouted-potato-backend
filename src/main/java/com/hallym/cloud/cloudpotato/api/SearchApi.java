package com.hallym.cloud.cloudpotato.api;

import com.hallym.cloud.cloudpotato.dto.Result;
import com.hallym.cloud.cloudpotato.dto.search.BooksFindDto;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SearchApi {

    @GetMapping("/search/{searchOne}")
    public Result booksFind(@PathVariable("searchOne") String searchOne) {
        aladinApi aapi = new aladinApi();

        // 본인이 받은 api키를 추가
        List<BooksFindDto> collect = new ArrayList<>();

        try {
            // parsing할 url 지정(API 키 포함해서)
            String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbhyejmh1853001&SearchTarget=Book&QueryType=Keyword&Sort=SalesPoint&Query=" + searchOne + "&Cover=Big";

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

                Optional<BooksFindDto> book = Optional.of(new BooksFindDto(aapi.getTagValue("title", eElement), aapi.getTagValue("author", eElement), aapi.getTagValue("isbn13", eElement), aapi.getTagValue("publisher", eElement), aapi.getTagValue("cover", eElement)));
                collect.add(book.orElse(null));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(collect);
    }
}
