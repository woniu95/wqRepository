package com.wq.pulsar;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.auth.AuthenticationBasic;
import org.apache.pulsar.common.policies.data.BacklogQuota;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangqiang
 * @date
 */
public class WqPulsar {

    public static final String adminUrl = "http://192.168.100.26:18080/";
    public static  final Properties clientConfig = new Properties();
    static {
        clientConfig.put("serviceUrl", "pulsar://192.168.100.26:6650");
    }


    public static PulsarClient getClient(Properties properties) throws PulsarClientException {
        System.out.println("getClient start, params: " + properties);

        AuthenticationBasic auth = new AuthenticationBasic();
        auth.configure("{\"userId\":\"superuser\",\"password\":\"admin\"}");
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(properties.getProperty("serviceUrl"))
                .authentication(
                        auth)
                .build();
        return client;

    }
    public static Producer getProducer(PulsarClient client, Schema schema, Properties properties) throws PulsarClientException {
        System.out.println("getProducer start, params: " + properties);

        Producer<String> stringProducer = client.newProducer(schema)
                .topic(properties.getProperty("topic"))
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(10, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();

        return stringProducer;
    }

    public static Consumer getConsumer(PulsarClient client, Schema schema, Properties properties) throws PulsarClientException {
        System.out.println("getConsumer start, params: " + properties);

        MessageListener myMessageListener = (consumer, msg) -> {

            try {
                System.out.println("##########Message topic:"+msg.getTopicName()+" received: " + new String(msg.getData()));
                consumer.acknowledge(msg);
                System.out.println("Message acknowledged: " + new String(msg.getData()));
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };

        Consumer consumer = client.newConsumer(schema)
                .topic(properties.getProperty("topic"))
                .subscriptionName("my-subscription")
                .messageListener(myMessageListener)
                .subscribe();
        return consumer;
    }

    public static void createTenant(PulsarAdmin pulsarAdmin, String tenantCode) throws PulsarClientException, PulsarAdminException {

        Set<String> roles = new HashSet<>();

        Set<String> cluster = new HashSet<>();
        cluster.add("standalone");

        Tenants tenants = pulsarAdmin.tenants();
        tenants.createTenant(tenantCode, TenantInfo.builder().adminRoles(roles).allowedClusters(cluster).build());
    }

    public static void createNameSpace(PulsarAdmin pulsarAdmin, String tenant, String nameSpace) throws PulsarAdminException {
        pulsarAdmin.namespaces().createNamespace(tenant+nameSpace);
    }

    @Data
    public static class A{
        private String a;
        private A b;
    }



    @Test
    public void createTenantTest() throws PulsarAdminException, PulsarClientException {
        Map config = new HashMap();
        config.put("serviceUrl", "http://localhost:8080/");


        AuthenticationBasic auth = new AuthenticationBasic();
        auth.configure("{\"userId\":\"superuser\",\"password\":\"admin\"}");
        Authentication authentication = AuthenticationFactory.token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdXBlcnVzZXIifQ.7I_9AfNcxOmxPJ0Hu1mpUqaP2BQil8LBvUO9UDbkZJ0");
        PulsarAdmin pulsarAdmin = PulsarAdmin.builder()
                .serviceHttpUrl("http://localhost:8080/")
                .authentication(
                        authentication)
                .build();

        System.out.println(pulsarAdmin.clusters().getClusters());

        createTenant(pulsarAdmin, "t015");
        createNameSpace(pulsarAdmin, "t015","/p001");
    }



    /**
     * 发送消息
     */
    @Test
    public void sendMsgTest() throws PulsarClientException {
        String topic = "persistent://public/default/consumerPullEarliestPositionTestTopic";
        PulsarClient pulsarClient = getClient(clientConfig);
        Producer producer = pulsarClient.newProducer(Schema.STRING).producerName(topic+"-Producer").
                topic(topic).create();

        for(int i=0;i<10;i++){
            String msg = "msg-"+System.currentTimeMillis();

            MessageId msID = producer.send(msg);
            System.out.println(String.format("topic: %s , send msg id: %s, msg data: %s", topic, msID.toString(), msg));

        }
    }

    /**
     * 测试 消费者 读取位置
     */
    public static void consumerPullPositionTest() throws PulsarClientException {
        PulsarClient pulsarClient = getClient(clientConfig);
        pulsarClient.newConsumer(Schema.STRING).topic("persistent://public/default/consumerPullEarliestPositionTestTopic").
                consumerName("consumerPullEarliestPositionTestConsumer").
                subscriptionName("consumerPullEarliestPositionTestSubscription").
                subscriptionInitialPosition(SubscriptionInitialPosition.Earliest).messageListener((consumer, msg) -> {
                    try {
                        System.out.println(String.format("msg topic %s, consumer %s, consumer last available msg id %s, msg id: %s data: %s",
                                msg.getTopicName(), consumer.getConsumerName(), consumer.getLastMessageId().toString(), msg.getMessageId().toString(), msg.getValue()));
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }).subscribe();

        pulsarClient.newConsumer(Schema.STRING).topic("persistent://public/default/consumerPullLatestPositionTestTopic").
                consumerName("consumerPullLatestPositionTestConsumer").
                subscriptionName("consumerPullLatestPositionTestSubscription").
                subscriptionInitialPosition(SubscriptionInitialPosition.Latest).messageListener((consumer, msg) -> {
                    try {
                        System.out.println(String.format("msg topic %s, consumer %s, consumer last available msg id %s, msg id: %s data: %s",
                                msg.getTopicName(), consumer.getConsumerName(), consumer.getLastMessageId().toString(), msg.getMessageId().toString(), msg.getValue()));
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }).subscribe();

    }

    /**
     * @author guohao
     * @date 2023/3/31
     */
    @Slf4j
    public static class TTLTest {

        @Test
        public static void testTTL() throws PulsarAdminException, PulsarClientException, InterruptedException {
            String tenant = "ttl-tenant";
            String namespace = "ttl-tenant/ttl-namespace-6";
            String serviceUrl = "pulsar://192.168.100.26:6650";
            String serviceHttpUrl = "http://192.168.100.26:18080";
            AuthenticationBasic auth = new AuthenticationBasic();
            auth.configure("{\"userId\":\"superuser\",\"password\":\"admin\"}");
            var topic = "persistent://" + namespace + "/ttl-msg-t1";

            System.out.println("admin: " + "admin");
            PulsarAdmin admin = null;
            try {
                admin = PulsarAdmin.builder().serviceHttpUrl(serviceHttpUrl).authentication(auth).build();
            } catch (Exception e) {
                System.out.println("admin: " + e.getMessage());
            }

            System.out.println("admin: " + "admin");

            // 检查租户和命名空间是否存在，不存在则创建
            if (!admin.tenants().getTenants().contains(tenant)) {
                admin.tenants().createTenant(tenant, TenantInfo.builder().adminRoles(Set.of("superuser")).allowedClusters(Set.of("standalone")).build());
            }
            if (!admin.namespaces().getNamespaces(tenant).contains(namespace)) {
                admin.namespaces().createNamespace(namespace);
            }

            ClientBuilder pulsarClientBuilder = PulsarClient.builder().serviceUrl(serviceUrl);

            var pulsarClient = pulsarClientBuilder.authentication(auth).build();
            var producer = pulsarClient.newProducer(Schema.STRING).topic(topic).create();

            System.out.println("ttl before set: " + admin.topicPolicies().getMessageTTL(topic));
            admin.topicPolicies().setMessageTTL(topic, 60 * 5);
            System.out.println("ttl after set: " + admin.topicPolicies().getMessageTTL(topic));

            AtomicInteger sendcount = new AtomicInteger();
            ScheduledExecutorService scheduledExecutorService  = Executors.newScheduledThreadPool(2);
            scheduledExecutorService.scheduleAtFixedRate(()->{
                for (int i = 0; i < 5000; i++) {
                    producer.newMessage().value("ttl-test: num:"+sendcount.getAndIncrement()+"-" + getTimeStringFromTimestamp(System.currentTimeMillis())).sendAsync();
                }
                System.out.println(String.format("last send message time: %s, num: %s topic: %s", getTimeStringFromTimestamp(System.currentTimeMillis()), sendcount.get(), topic));
            }, 0, 5, TimeUnit.MINUTES);

            TimeUnit.MINUTES.sleep(5);
            AtomicInteger count = new AtomicInteger();
            var subscriptionName = "benchmark-vertx";
            pulsarClient.newConsumer(Schema.STRING).topic(topic).subscriptionName(subscriptionName)
                    .subscriptionType(SubscriptionType.Shared)
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                    .messageListener((consumer, msg) -> {
                        try {
                            byte[] byteData = msg.getData();
                            var data = new String(byteData, StandardCharsets.UTF_8);
                            count.getAndIncrement();
                            if (count.get() % 100 == 0) {
                                System.out.println(String.format("received 10000 pulsar message, now: %s current msg: %s, topic: %s", getTimeStringFromTimestamp(System.currentTimeMillis()), data, topic));
                                TimeUnit.MINUTES.sleep(1);
                            }
                            consumer.acknowledge(msg);
                        } catch (Exception e) {
                            log.error("[PulsarVerticle] pulsar consumer error", e);
                        }
                    }).subscribe();
        }

        /**
         * 通过时间戳获取时间字符串
         *
         * @param timestamp 时间戳
         * @return 时间字符串
         */
        public static String getTimeStringFromTimestamp(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return sdf.format(new Date(timestamp));
        }
    }



    public static void main(String[] args) throws PulsarClientException, PulsarAdminException, InterruptedException {
        consumerPullPositionTest();
    }
}
