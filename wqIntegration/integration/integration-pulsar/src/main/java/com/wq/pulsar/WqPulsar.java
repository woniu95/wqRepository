package com.wq.pulsar;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Tenants;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.common.policies.data.TenantInfo;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangqiang
 * @date
 */
public class WqPulsar {

    public static PulsarClient getClient(Properties properties) throws PulsarClientException {
        System.out.println("getClient start, params: " + properties);
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(properties.getProperty("url"))
                .enableTlsHostnameVerification(false)
                .build();
        return client;

    }
    public static Producer getProducer(PulsarClient client, Properties properties) throws PulsarClientException {
        System.out.println("getProducer start, params: " + properties);

        Producer<String> stringProducer = client.newProducer(Schema.STRING)
                .topic(properties.getProperty("topic"))
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .sendTimeout(10, TimeUnit.SECONDS)
                .blockIfQueueFull(true)
                .create();

        return stringProducer;
    }

    public static Consumer getConsumer(PulsarClient client, Properties properties) throws PulsarClientException {
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

        Consumer consumer = client.newConsumer()
//                .topic(properties.getProperty("topic"))
                .topicsPattern(".{0,}/.{0,}/a-.*-b")
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

    public static void main(String[] args) throws PulsarClientException {
        try{
            Properties properties = new Properties();
            properties.put("url", "pulsar://localhost:6650");
            properties.put("topic", "public/default/a-c-b");
            PulsarClient client = WqPulsar.getClient(properties);
            Producer<String> stringProducer = WqPulsar.getProducer(client, properties);

            Map config = new HashMap();
            config.put("serviceUrl", "http://localhost:8080/");
            PulsarAdmin pulsarAdmin =  PulsarAdmin.builder().loadConf(config).build();

            System.out.println(pulsarAdmin.clusters().getClusters());

            createTenant(pulsarAdmin, "t002");
//            createNameSpace(pulsarAdmin, "t001/p001");
            properties.put("topic", "t001/p001/a-c-b");

            Producer<String> stringProducer2 = WqPulsar.getProducer(client, properties);

            Consumer consumer = WqPulsar.getConsumer(client, properties);

            Random random = new Random(1000);

            Thread pT = new Thread(
                    ()->{
                        while (true){
                            try {
                                String msg=String.valueOf(random.nextInt());
                                System.out.println("stringProducer.send: " + msg);

                                stringProducer.send(msg);
                                stringProducer2.send(msg);
                                TimeUnit.SECONDS.sleep(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
            );
            pT.setName("producer");
            pT.start();

            Thread pC = new Thread(()->{

                while (true) {

                    // Wait for a message
                    Message msg = null;
                    try {
                        msg = consumer.receive();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                    try {
                        // Do something with the message
                        System.out.println("Message received: " + new String(msg.getData()));

                        // Acknowledge the message so that it can be deleted by the message broker
                        consumer.acknowledge(msg);
                    } catch (Exception e) {
                        // Message failed to process, redeliver later
                        consumer.negativeAcknowledge(msg);
                    }
                }

            });
            pC.setName("consumer");
            pC.start();

        }catch (Throwable e){
            e.printStackTrace();
        }

    }
}
