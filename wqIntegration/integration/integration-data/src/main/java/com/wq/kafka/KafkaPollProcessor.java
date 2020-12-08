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


public class KafkaPollProcessor {

	private Logger logger = LoggerFactory.getLogger(KafkaPollProcessor.class);
	
	private Long maxFetchDataTime = 30 * 1000L;
			
	private KafkaConsumer<String, String> consumer = null;

	private IKafkaDataHandle kafkaDataHandle;


	public KafkaPollProcessor(String server, String topic, String group, IKafkaDataHandle kafkaDataHandleLogic){
		init(server, topic, group);
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


	private void init(String server, String topic, String group) {
		
		Properties props = generateConsumerConfigProperty(server, group);
		consumer = new KafkaConsumer(props);
//		consumer.subscribe(Arrays.asList(topic));
		consumer.assign( Arrays.asList(new TopicPartition(topic, 0)));
		
	}

	private Properties generateConsumerConfigProperty(String server, String group) {
		Properties props = new Properties();
		// kafka 集群地址设置
		props.put("bootstrap.servers", server);
		// 订阅topic的用户组设置
		// props.put("group.id", this.groupId);
		props.put("group.id", group);
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
