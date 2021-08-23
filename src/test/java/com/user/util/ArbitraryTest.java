package com.user.util;

import com.user.dao.entity.BaseEntity;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArbitraryTest {

    @Test
    public void test1() {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setUpdatedAt(new Timestamp(12345678L));
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(baseEntity);
        for (int i =3 ; i>=0; i--) {
            baseEntityList.add(baseEntityList.get(0));
        }
        System.out.println(baseEntityList.size());
    }


}
