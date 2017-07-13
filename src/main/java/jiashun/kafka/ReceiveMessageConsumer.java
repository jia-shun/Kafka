package jiashun.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ReceiveMessageConsumer {
	public static final String SERVERS = "kafka-single:9095";
	public static final String TOPIC = "jiashun-topic";
	public static void main(String[] args) {

		Properties props = new Properties();
		// 定义消息消费者的连接服务器地址
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
		// 消息消费者一定要设置反序列化的程序类，与消息生产者完全对应
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				IntegerDeserializer.class.getName());
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		// 定义消费者处理对象
		Consumer<String, Integer> consumer = new KafkaConsumer<String, Integer>(
				props);
		consumer.subscribe(Arrays.asList(TOPIC)); // 设置消费者读取的主题名称
		boolean flag = true; // 消费者需要一直进行数据的读取处理操作
		while (flag) { // 一直读取消息
			ConsumerRecords<String, Integer> allRecorders = consumer.poll(200);
			for (ConsumerRecord<String, Integer> record : allRecorders) {
				System.out.println(
						"key = " + record.key() + "、消费端value = " + record.value());
			}
		}
		consumer.close();
	}
}
