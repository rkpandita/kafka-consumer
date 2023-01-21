package com.kafka.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class LibraryEventsConsumerConfig {

	@Bean
	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {

		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory.getIfAvailable());
		
		// Spawns 3 threads	with same instance of kafka listener, check in console
		// Recommended, if Application is NOT running in Cloud like env (k8s)
		// factory.setConcurrency(3);

		// Override AckMode from default BATCH to MANUAL
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

		return factory;
	}

}
