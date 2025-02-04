package com.jonathangf.primerProyectoSockets.repositories;

import com.jonathangf.primerProyectoSockets.models.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio para manejar operaciones con MongoDB
@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    // Método para obtener mensajes por nombre de usuario
    List<Mensaje> findByUsername(String username);
}

