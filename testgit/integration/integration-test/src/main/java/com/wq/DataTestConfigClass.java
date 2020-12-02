package com.wq;

import com.mongodb.util.JSON;
import com.wq.db.IDaoTest;
import com.wq.kafka.IKafkaDataHandle;
import com.wq.kafka.KafkaReceiverProcessor;
import com.wq.kafka.KafkaSenderProcessor;
import com.wq.mongodb.IMongodbDao;
import com.wq.redis.RedisDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-01 12:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-servlet.xml"})
public class DataTestConfigClass {

    @Resource
    IDaoTest daoTest;

    @Resource
    RedisDataService redisService;

    @Resource
    IMongodbDao mongodbDao;

    @Test
    public void dbTest(){
        System.out.println(daoTest.getData());
    }

    @Test
    public void redisTest(){

        redisService.del("test_order_seq_no");
        System.out.println(redisService.set("test_order", 0));
        System.out.println(redisService.get("test_order"));
        System.out.println(redisService.setNum("test_order_seq_no", 0));
        System.out.println(redisService.incrNum("test_order_seq_no", 1L));
        System.out.println(redisService.getNum("test_order_seq_no"));
        redisService.del("test_order_seq_no");
    }

    @Test
    public void mongoTest() {

        Query query = new BasicQuery("{$and:[{\"MemberAccount_accountLevelId\":10000}]}", "{_id:true}");

        String COLLECTION_NAME = "MEMBER_CONDITION_NJ";
        Long memberCount = mongodbDao.findCount(COLLECTION_NAME, query);

        System.out.println("memberCount = {}" + memberCount);

        int pageSize = 200;
        long pageCount = ((memberCount % pageSize) == 0) ? memberCount / pageSize : (memberCount / pageSize + 1);

        int pageIndex = 2;

        int startIndex = ((pageIndex - 1) < 0 ? 0 : (pageIndex - 1)) * pageSize;
        query.skip(startIndex);
        query.limit(pageSize);
        List<Map> memberRecordMaps = mongodbDao.findList(COLLECTION_NAME, Map.class, query, pageIndex, pageSize);

        System.out.println(memberRecordMaps);
    }

    @Test
    public void kafkaTest(){

        KafkaSenderProcessor senderProcessor = new KafkaSenderProcessor();
        senderProcessor.send("kafkaTestData1", "kafkaTest");

        KafkaReceiverProcessor kafkaReceiverProcessor = new KafkaReceiverProcessor("kafkaTest", new IKafkaDataHandle(){
            @Override
            public void doDataHandleLogic(String data) {
                System.out.println("receive data: " + data);
            }
        });

        kafkaReceiverProcessor.pollData();

    }

}
