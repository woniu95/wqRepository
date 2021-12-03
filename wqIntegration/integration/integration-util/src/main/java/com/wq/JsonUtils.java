package com.wq;

import com.alibaba.fastjson.JSON;
import org.codehaus.jackson.type.TypeReference;
import com.github.pagehelper.Page;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName:com.wq
 * @ClassName:JsonUtils
 * @Description:
 * @author: wq
 * @date 2021/12/1 23:11
 */
public class JsonUtils {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(JsonMethod.ALL, JsonAutoDetect.Visibility.ANY);

        // list等对象不会序列化 size等属性
        Page p = new Page();
        p.setTotal(1);
        System.out.println(objectMapper.writeValueAsString(p));
        System.out.println( JSON.toJSON(p));

        RecordTimeUtils timeUtils = new RecordTimeUtils();
        timeUtils.varstart();
        for(int i=0, count =100;i<count;i++){
            List<A> listB = getTestData(20000);
            RecordTimeUtils.start();
            String s = JSON.toJSONString(listB);
            List<A> a = JSON.parseArray(s, A.class);
            System.out.println("Fastjson consume time :" +RecordTimeUtils.end());
        }
        System.out.println("Fastjson Total consume time :" +timeUtils.varend());

        System.out.println("================================================================================");

        timeUtils.varstart();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, A.class);
        for(int i=0, count =100;i<count;i++){
            List<A> listA = getTestData(20000);
            RecordTimeUtils.start();
            String s = objectMapper.writeValueAsString(listA);
            List<A> a = objectMapper.readValue(s, javaType);
            System.out.println("Jackson JavaType consume time :" +RecordTimeUtils.end());
        }
        System.out.println("Jackson JavaType Total consume time :" +timeUtils.varend());

        timeUtils.varstart();
        TypeReference typeReference = new TypeReference<List<A>>(){};
        for(int i=0, count =100;i<count;i++){
            List<A> listA = getTestData(20000);
            RecordTimeUtils.start();
            String s = objectMapper.writeValueAsString(listA);
            List<A> a = objectMapper.readValue(s, typeReference);
            System.out.println("Jackson TypeReference consume time :" +RecordTimeUtils.end());
        }
        System.out.println("Jackson TypeReference  Total consume time :" +timeUtils.varend());

    }

    private static List<A> getTestData(int size) {
        List<A> listB = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            listB.add(new A());
        }
        return listB;
    }


    public static class A{
        private String str = "a";

        private Long longPacked = 1L;
        private long longPrime = 2L;
        private Integer integer = 3;
        private int intPrime = 3;
        private Float aFloat = 4.0F;
        private float afloat = 4.0F;
        private int[] intarr = {1,1,2};
        private long[] longarr = {1l,1l,2l};
        private List<B> listB = new ArrayList<>();
        private B b = new B();
        {
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());
            listB.add(new B());

        }
    }

    public static class B{
        private String str = "b";

        private Long longPacked = 1L;
        private long longPrime = 2L;
        private Integer integer = 3;
        private int intPrime = 3;
        private Float aFloat = 4.0F;
        private float afloat = 4.0F;
        private int[] intarr = {1,1,2};
        private long[] longarr = {1l,1l,2l};

    }
}
