package com.jonathangf.primerProyectoSockets.controller;

import com.jonathangf.primerProyectoSockets.models.Mensaje;
import com.jonathangf.primerProyectoSockets.models.Usuario;
import com.jonathangf.primerProyectoSockets.services.MensajeService;
import com.jonathangf.primerProyectoSockets.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Controlador REST para manejar mensajes
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private UsuarioService usuarioService;



    // Endpoint para guardar un mensaje
    @PostMapping
    public Mensaje guardarMensaje(@RequestBody Mensaje mensaje) {
        return mensajeService.guardarMensaje(mensaje);
    }

    // Endpoint para obtener todos los mensajes
    @GetMapping
    public List<Mensaje> obtenerTodosLosMensajes() {
        return mensajeService.obtenerTodosLosMensajes();
    }

    // Endpoint para obtener mensajes de un usuario específico
    @GetMapping("/usuario/{username}")
    public List<Mensaje> obtenerMensajesPorUsuario(@PathVariable String username) {
        return mensajeService.obtenerMensajesPorUsuario(username);
    }

    //Registro de nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        String emailNormalizado = usuario.getEmail().toLowerCase().trim(); // Convertir a minúsculas y eliminar espacios

        if (usuarioService.existePorEmail(emailNormalizado)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
        }

        usuario.setEmail(emailNormalizado);
        usuario.setPassword(hashPassword(usuario.getPassword())); // Encriptar la contraseña antes de guardar
        usuarioService.guardarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente.");
    }


    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioDB = usuarioService.obtenerPorEmail(usuario.getEmail().toLowerCase().trim());

        if (usuarioDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos.");
        }

        Usuario usuarioEncontrado = usuarioDB.get();

        // Verificar si la contraseña ingresada (encriptada) coincide con la almacenada
        if (!usuarioEncontrado.getPassword().equals(hashPassword(usuario.getPassword()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos.");
        }

        return ResponseEntity.ok("Inicio de sesión exitoso");
    }



    // Método para encriptar contraseñas con SHA-256 (igual al que usaste en UsuarioService)
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

}

