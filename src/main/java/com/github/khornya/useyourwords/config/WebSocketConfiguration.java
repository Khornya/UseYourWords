package com.github.khornya.useyourwords.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value(value = "${websocket.destination.prefix.broker}")
    private String brokerDestinationPrefix;

    @Value(value = "${websocket.destination.prefix.application}")
    private String applicationDestinationPrefix;

    @Value(value = "${websocket.stomp.endpoint}")
    private String stompEndpoint;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(brokerDestinationPrefix);
        config.setApplicationDestinationPrefixes(applicationDestinationPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(stompEndpoint).setAllowedOrigins("*").withSockJS();
    }

}
