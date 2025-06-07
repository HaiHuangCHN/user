package com.user.center.util;

import com.user.center.dao.entity.Address;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO Fill in desc
 *
 * @author haihuang95@zto.com
 * @version 0.1.0
 * @date 2022/2/22 14:27
 * @since 0.1.0
 */
public class TempTest {

    @Test
    public void tempTest1() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address();
        address1.setCity("city1");
        address1.setCountry("country1");
        Address address2 = new Address();
        address2.setCity("city2");
        address2.setCountry("country1");
        Address address3 = new Address();
        address3.setCity("city1");
        address3.setCountry("country2");
        addressList.add(address1);
        addressList.add(address2);
        addressList.add(address3);

        Map<String, List<Address>> a =
                addressList.stream().collect(Collectors.groupingBy(Address::getCountry));
        for (Map.Entry<String, List<Address>> entry : a.entrySet()) {
            System.out.println(JsonUtils.objectToJsonCamel(entry.getKey()));
            System.out.println(JsonUtils.objectToJsonCamel(entry.getValue()));
        }
        System.out.println(JsonUtils.objectToJsonCamel(a));
    }

    @Test
    public void tempTest2() {
        List<Address> addressList = new ArrayList<>();
        Address address1 = new Address();
        address1.setCity(null);
        address1.setCountry("country1");
        Address address2 = new Address();
        address2.setCity("   1  ");
        address2.setCountry("country2");
        Address address3 = new Address();
        address3.setCity("city3");
        address3.setCountry("country3");
        addressList.add(address1);
        addressList.add(address2);
        addressList.add(address3);

        List<String> a =
                addressList.stream().map(Address::getCity).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.println(JsonUtils.objectToJsonCamel(a));
    }

    @Test
    public void test2() {
        System.out.println(String.valueOf(Base64Utils.encodeToString("N2Q0MzNhNzMtNzI5Yi00NTg3LWI0NGQtZTRhOGIwOTllYzA5".getBytes())));
    }

}
