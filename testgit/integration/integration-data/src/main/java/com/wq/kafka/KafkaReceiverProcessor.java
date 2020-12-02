package com.wq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class KafkaReceiverProcessor {

	private Logger logger = LoggerFactory.getLogger(KafkaReceiverProcessor.class);
	
	private Long maxFetchDataTime = 30 * 1000L;
			
	private KafkaConsumer<String, String> consumer = null;

	private IKafkaDataHandle kafkaDataHandle;


	public KafkaReceiverProcessor(String topic, IKafkaDataHandle kafkaDataHandleLogic){
		init(topic);
		this.kafkaDataHandle = kafkaDataHandleLogic;
	}

	public void pollData() {

		ConsumerRecords<String, String> rs = consumer.poll(maxFetchDataTime);
		for (TopicPartition partition : rs.partitions()) {
			List<ConsumerRecord<String, String>> partitionRecords = rs.records(partition);
			for (ConsumerRecord<String, String> record : partitionRecords) {
				// do logic
				logger.info("receive kafka message offset: " + record.offset());
				kafkaDataHandle.doDataHandleLogic(record.value());
			}
		}
		consumer.commitAsync();
	}




	//TODO 配置放入配置文件
	private void init(String topic) {
		
		Properties props = generateConsumerConfigProperty();
		consumer = new KafkaConsumer<String, String>(
				props);
//		consumer.subscribe(Arrays.asList(topic));
		consumer.assign( Arrays.asList(new TopicPartition(topic, 0)));
		
	}

	private Properties generateConsumerConfigProperty() {
		Properties props = new Properties();
		// kafka 集群地址设置
		props.put("bootstrap.servers", "127.0.0.1");
		// 订阅topic的用户组设置
		// props.put("group.id", this.groupId);
		props.put("group.id", "local_sync");
		props.put("max.poll.records", "10");

		// 复位OFFSET, 断线重连时需用
		props.put("auto.offset.reset", "earliest");

		// 自动提交设置
		props.put("enable.auto.commit", "false");

		// 自动提交 offset 间隔时间
		props.put("auto.commit.interval.ms", "3000");

		props.put("heartbeat.interval.ms", "9000");

		// session 连接超时时间
//		props.put("session.timeout.ms",  30 * 1000);
		props.put("fetch.max.wait.ms", 5 * 1000);
//		props.put("request.timeout.ms",   35 * 1000);

		// 最大大小为2M
		props.put("max.partition.fetch.bytes", 2*1024*1024);

		// key&value 反序列化设置
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		return props;
	}



}
