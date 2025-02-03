package com.jonathangf.primerProyectoSockets.controller;

import com.jonathangf.primerProyectoSockets.models.Mensaje;
import com.jonathangf.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.time.LocalDateTime;


@Controller
public class ChatControllers {


    @Autowired
    private MensajeRepository mensajeRepository; // Inyección del repositorio


    @MessageMapping("/mensaje")  // Ruta de solicitud del cliente
    @SendTo("/chat/mensaje")     // Ruta de respuesta para los clientes
    public Mensaje recibeMensaje(Mensaje mensaje) {
        // Configurar atributos adicionales del mensaje
        mensaje.setFechaEnvio(LocalDateTime.now()); // Hora de envío


        // Validar si el usuario envió un nick; si no, asignar uno genérico
        if (mensaje.getUsername() == null || mensaje.getUsername().isEmpty()) {
            mensaje.setUsername("Usuario Anónimo");
        }


        // Guardar el mensaje en la base de datos
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);


        // Log para depuración
        System.out.println("Mensaje guardado: " + mensajeGuardado);


        return mensajeGuardado; // Enviar el mensaje de vuelta a todos los suscriptores
    }
}

