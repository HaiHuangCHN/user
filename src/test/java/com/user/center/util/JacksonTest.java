package com.user.center.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.io.IOException;

/**
 * TODO Fill in desc
 *
 * @author haihuang95@zto.com
 * @version 0.1.0
 * @date 2021/12/22 10:16
 * @since 0.1.0
 */
public class JacksonTest {

    @Getter
    @Setter
    private static class XML {
        private Msg msg;
    }

    @Getter
    @Setter
    private static class Msg {
        private Integer msgStatus;
        private String msgStatusDesc;
        private String phoneno;
        private String msgId;
    }

    @Test
    public void testXmlToJson() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        XML xml = xmlMapper.readValue("<?xml version=\"1.0\"?>\n" +
                "<xml><msg><msg_status>100</msg_status><msg_status_desc>Successfully submitted message. &#x6267;" +
                "&#x884C;&#x6210;&#x529F;</msg_status_desc><phoneno>8613249704417</phoneno><msg_id>15161879</msg_id" +
                "></msg></xml>", XML.class);
        System.out.println(xml.getMsg().getMsgStatus());
        System.out.println(xml.getMsg().getMsgStatusDesc());
        System.out.println(xml.getMsg().getPhoneno());
        System.out.println(xml.getMsg().getMsgId());
    }

}
