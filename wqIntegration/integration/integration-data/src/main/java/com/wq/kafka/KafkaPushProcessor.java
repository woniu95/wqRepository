package com.wq.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaPushProcessor {

	private Logger logger = LoggerFactory.getLogger(KafkaPushProcessor.class);

	private Producer<String, String> kafkaProducer;

	public KafkaPushProcessor(String server, String group){
		init(server, group);
	}

	public void init(String server, String group) {

		Properties props = new Properties();
		long begin = System.currentTimeMillis();
		props.put("group.id", group);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
		logger.info(" kafka sender processor init ... ");
		kafkaProducer = new KafkaProducer(props);
		logger.info(" kafka sender processor init complete, time: " + (System.currentTimeMillis() - begin));
	}


	public boolean send(String value, String topic) {
		
		try {
			ProducerRecord<String, String> msg = new ProducerRecord<>(topic, 0, null, value);
			Future<RecordMetadata> f = kafkaProducer.send(msg);
			RecordMetadata resp = f.get();
			logger.info(" send message topic: " + topic + ", offset : " + resp.offset());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		} 

		return true;
	}

	
}
