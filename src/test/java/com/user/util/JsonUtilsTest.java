package com.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user.costant.DbErrorEnum;
import com.user.domain.dto.request.AddUserReq;
import org.junit.Test;

import java.util.Date;

/**
 * JsonUtils测试类
 *
 * @author haihuang95@zto.com
 * @date 2021-07-26 10:19:23
 */
public class JsonUtilsTest {

    @Test
    public void testObjectToJson() {
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setDay("2020-07-20T20:20:20");

       System.out.println(JsonUtils.objectToJsonCamel(addUserReq));
    }

    @Test
    public void testRandon() {
        DbErrorEnum.valueOf(null);
    }


}
