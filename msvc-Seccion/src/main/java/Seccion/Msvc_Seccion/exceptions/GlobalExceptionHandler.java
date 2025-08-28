package Seccion.Msvc_Seccion.exceptions;

import Seccion.Msvc_Seccion.dtos.ErrorDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDTO createErrorDTO(int status, Date date, Map<String, String> errores) {
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(status);
        dto.setDate(date);
        dto.setErrors(errores);
        return dto;
    }

    @ExceptionHandler({AsignaturaNotFoundException.class, SeccionNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNotFound(RuntimeException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = createErrorDTO(HttpStatus.NOT_FOUND.value(), new Date(), errores);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> handleFeignException(FeignException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", "Error al comunicarse con el servicio remoto: " + ex.getMessage());
        ErrorDTO dto = createErrorDTO(ex.status(), new Date(), errores);
        return new ResponseEntity<>(dto, HttpStatus.valueOf(ex.status()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), errores);
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOtherExceptions(Exception ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", "Ocurrió un error interno en el servidor");
        ErrorDTO dto = createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), errores);
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleJsonParseException(HttpMessageNotReadableException ex) {
        Map<String, String> errores = new HashMap<>();
        // Obtenemos el mensaje específico de la causa
        String causa = ex.getMostSpecificCause().getMessage();
        // Mensaje personalizado
        String mensaje;
        if (causa.contains("Cannot deserialize value of type `java.lang.Long`")) {
            mensaje = "Se esperaba un valor numérico para un campo de tipo Long. Revisa los datos enviados.";
        } else if (causa.contains("Cannot deserialize value of type `java.time.LocalDateTime`")) {
            mensaje = "Se esperaba una fecha en formato 'yyyy-MM-ddTHH:mm:ss' para un campo de tipo LocalDateTime.";
        } else if (causa.contains("Unexpected character")) {
            mensaje = "JSON mal formado. Revisa comas, corchetes y llaves en tu solicitud.";
        } else {
            mensaje = "Error al procesar el JSON de entrada: " + causa;
        }
        errores.put("mensaje", mensaje);
        ErrorDTO dto = createErrorDTO(HttpStatus.BAD_REQUEST.value(), new Date(), errores);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeccionExistenteException.class)
    public ResponseEntity<ErrorDTO> manejarSeccionExistente(SeccionExistenteException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setDate(new Date());
        dto.setErrors(errores);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FechasInvalidasException.class)
    public ResponseEntity<ErrorDTO> manejarFechasInvalidas(FechasInvalidasException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setDate(new Date());
        dto.setErrors(errores);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FechasVaciasParaEliminarException.class)
    public ResponseEntity<ErrorDTO> manejarFechasVaciasParaEliminar(FechasVaciasParaEliminarException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setDate(new Date());
        dto.setErrors(errores);
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }




}
