package com.user.util;

import org.junit.Test;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * JsonUtils测试类
 *
 * @author haihuang95@zto.com
 * @date 2021-07-26 10:19:23
 */
public class LocalDateTimeUtilTest {

    @Test
    public void testObjectToJson() {
        System.out.println(LocalDateTimeUtil.convertToLocalDateTime(new Date()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }


}
