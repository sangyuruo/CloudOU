package com.emcloud.ou.config;

import com.emcloud.ou.messaging.ConsumerChannel;
import com.emcloud.ou.messaging.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(value = {Source.class, ProducerChannel.class, ConsumerChannel.class})
public class MessagingConfiguration {
}

