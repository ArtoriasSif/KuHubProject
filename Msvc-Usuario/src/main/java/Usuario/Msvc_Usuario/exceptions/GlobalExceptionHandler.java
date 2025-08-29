package Usuario.Msvc_Usuario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<Map<String, Object>> manejarUsuarioException(UsuarioException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Usuario no encontrado");
        body.put("message", ex.getMessage());
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioNotFoundByUsernameException.class)
    public ResponseEntity<Map<String, Object>> manejarUsuarioNotFoundByUsername(UsuarioNotFoundByUsernameException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Usuario no encontrado");
        body.put("message", ex.getMessage());
        body.put("username", ex.getUsername());
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Captura excepciones personalizadas de usuario
    @ExceptionHandler({ UsuarioNotFoundException.class, EmailUsuarioExistenteException.class,
            UsernameUsuarioExistenteException.class, SeccionNotFoundException.class })
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Captura todas las excepciones no controladas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
