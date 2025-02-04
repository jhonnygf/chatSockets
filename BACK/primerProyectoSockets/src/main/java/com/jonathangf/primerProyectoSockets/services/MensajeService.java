package com.jonathangf.primerProyectoSockets.services;

import com.jonathangf.primerProyectoSockets.models.Mensaje;
import com.jonathangf.primerProyectoSockets.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Servicio para manejar la lógica de los mensajes
@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    // Guarda un mensaje en la base de datos
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    // Obtiene todos los mensajes almacenados
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeRepository.findAll();
    }

    // Obtener mensajes de un usuario específico
    public List<Mensaje> obtenerMensajesPorUsuario(String username) {
        return mensajeRepository.findByUsername(username);
    }
}


