package com.wq;

import com.wq.db.IDaoTest;
import com.wq.kafka.KafkaPollProcessor;
import com.wq.kafka.KafkaPushProcessor;
import com.wq.mongodb.IMongodbDao;
import com.wq.redis.RedisDataService;
import com.wq.spring.PropertyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;

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

    /**
     *  history:
     *      insert local single table(10*10000) ,test cost time:7017713 ms
     * @throws Exception
     */
    @Test
    public void calCost() throws Exception {
        Date start = new Date();
        for(int i=0;i<10*10000;i++){
            dbTest();
        }
        Date end = new Date();
        System.out.println("test cost time:"+ (end.getTime()-start.getTime()));

    }

    @Test
    public void dbTest() throws Exception {

//        System.out.println(daoTest.getData());
        List<String> columns = Arrays.asList("RECORD_NO", "MEMBER_RECORD_NO", "ORIGINAL_BEHAVIOR_RECORD_NO", "SEND_BY",
                "REF_RECORD_NO", "SEND_STATUS", "STATUS_INFO", "CAN_SEND_COUPON_IDS", "CREATE_TIME");

        List<List<Object>> rowList = new ArrayList<>();
        List<Object> record = new ArrayList<>();
        record.addAll(Arrays.asList(UUID.randomUUID().toString().replace("-",""),
                "5f2d76116eee43f19bd598cdf3542c89", null, "ZD",
                "41", "SUCCESS", null, "[144,128,141]", "2020-11-26 10:52:04"));
        rowList.add(record);

        daoTest.insertBatch( rowList,columns,"b_yx_coupon_send_prepare_info");
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
        String server = PropertyUtil.getConfigVal("bootstrap.servers");
        String group = PropertyUtil.getConfigVal("group.id");
        KafkaPushProcessor senderProcessor = new KafkaPushProcessor(server, group);
        senderProcessor.send("kafkaTestData1", "kafkaTest");

        KafkaPollProcessor kafkaPollProcessor = new KafkaPollProcessor(server,"kafkaTest",
                group, data -> {
            System.out.println("receive data: " + data);
        });

        kafkaPollProcessor.pollData();

    }

}
