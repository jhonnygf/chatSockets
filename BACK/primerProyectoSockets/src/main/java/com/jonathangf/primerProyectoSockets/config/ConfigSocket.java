package com.jonathangf.primerProyectoSockets.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Habilita WebSockets en la aplicación
@Configuration
@EnableWebSocketMessageBroker
public class ConfigSocket implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define el endpoint para la conexión de WebSockets
        registry.addEndpoint("/chat-websocket")
                .setAllowedOrigins("http://localhost:4200") // Dominios permitidos
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Habilita el broker de mensajes para gestionar comunicación en tiempo real
        registry.enableSimpleBroker("/chat/");
        // El broker se usará para enviar los mensajes a los clientes
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user"); // Manejo de mensajes dirigidos a usuarios específicos

    }
}

