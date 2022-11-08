package com.wq.pulsar;

import org.apache.pulsar.client.api.*;

import java.util.Properties;
import java.util.Random;
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
                System.out.println("Message received: " + new String(msg.getData()));
                consumer.acknowledge(msg);
                System.out.println("Message acknowledged: " + new String(msg.getData()));
            } catch (Exception e) {
                consumer.negativeAcknowledge(msg);
            }
        };

        Consumer consumer = client.newConsumer()
                .topic(properties.getProperty("topic"))
                .subscriptionName("my-subscription")
                .messageListener(myMessageListener)
                .subscribe();
        return consumer;
    }

    public static void main(String[] args) throws PulsarClientException {
        Properties properties = new Properties();
        properties.put("url", "pulsar://localhost:6650");
        properties.put("topic", "my-topic");
        PulsarClient client = WqPulsar.getClient(properties);
        Producer<String> stringProducer = WqPulsar.getProducer(client, properties);
        Consumer consumer = WqPulsar.getConsumer(client, properties);

        Random random = new Random(1000);

        Thread pT = new Thread(
                ()->{
                    while (true){
                        try {
                            String msg=String.valueOf(random.nextInt());
                            System.out.println("stringProducer.send: " + msg);

                            stringProducer.send(msg);

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
//        pC.start();

    }
}
