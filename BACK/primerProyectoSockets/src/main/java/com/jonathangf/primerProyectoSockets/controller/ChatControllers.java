package com.jonathangf.primerProyectoSockets.controller;

import com.jonathangf.primerProyectoSockets.models.Mensaje;
import com.jonathangf.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

// Controlador para manejar mensajes en WebSockets
@Controller
public class ChatControllers {

    @Autowired
    private MensajeRepository mensajeRepository; // Inyección del repositorio

    @MessageMapping("/mensaje")  // Ruta de solicitud del cliente
    @SendTo("/chat/mensaje")     // Ruta de respuesta para los clientes
    public Mensaje recibeMensaje(Mensaje mensaje) {
        // Asigna la hora actual al mensaje
        mensaje.setFechaEnvio(LocalDateTime.now()); // Hora de envío

        // Asigna un nombre genérico si el usuario no proporcionó uno
        if (mensaje.getUsername() == null || mensaje.getUsername().isEmpty()) {
            mensaje.setUsername("Usuario Anónimo");
        }

        // Guardar el mensaje en la base de datos
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);
        System.out.println("Mensaje guardado: " + mensajeGuardado);

        return mensajeGuardado; // Enviar el mensaje de vuelta a todos los suscriptores
    }
}

