package com.hallym.cloud.cloudpotato.api;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class aladinApi {

    // tag값의 정보를 가져오는 함수
    public String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        return result;
    }
}