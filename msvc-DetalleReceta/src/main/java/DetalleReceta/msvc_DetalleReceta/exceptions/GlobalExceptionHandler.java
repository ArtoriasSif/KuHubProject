package DetalleReceta.msvc_DetalleReceta.exceptions;

import DetalleReceta.msvc_DetalleReceta.dtos.ErrorDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDTO createErrorDTO(int status, Date date , Map<String,String> errorMap ){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status);
        errorDTO.setDate(date);
        errorDTO.setErrors(errorMap);
        return errorDTO;
    }

    // Manejo genérico
    @ExceptionHandler(DetalleRecetaException.class)
    public ResponseEntity<String> handleDetalleReceta(DetalleRecetaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Cuando no se encuentra un detalle de receta
    @ExceptionHandler(DetalleRecetaNotFoundException.class)
    public ResponseEntity<String> handleDetalleRecetaNotFound(DetalleRecetaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Cuando no se encuentra la receta
    @ExceptionHandler(RecetaNotFoundException.class)
    public ResponseEntity<String> handleRecetaNotFound(RecetaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Error con el microservicio de productos
    @ExceptionHandler(ProductoClientException.class)
    public ResponseEntity<String> handleProductoClient(ProductoClientException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    // Cuando ya existe el producto
    @ExceptionHandler(ProductoExistenteException.class)
    public ResponseEntity<String> handleProductoExistente(ProductoExistenteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    // --- NUEVO: validaciones de campos obligatorios ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), new Date(), errores));
    }

    // --- NUEVO: JSON mal formado o valor nulo en propiedad ---
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleJsonParseException(HttpMessageNotReadableException ex) {
        Map<String, String> errores = new HashMap<>();
        String causa = ex.getMostSpecificCause().getMessage();
        String mensaje;
        if (causa.contains("Cannot deserialize value of type `java.lang.Long`")) {
            mensaje = "Se esperaba un valor numérico para un campo de tipo Long. Revisa los datos enviados.";
        } else if (causa.contains("Unexpected character")) {
            mensaje = "JSON mal formado. Revisa comas, corchetes y llaves en tu solicitud.";
        } else if (causa.contains("not-null property references a null")) {
            mensaje = "Falta un campo obligatorio o tiene valor nulo: " + causa;
        } else {
            mensaje = "Error al procesar el JSON de entrada: " + causa;
        }
        errores.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), new Date(), errores));
    }

    // --- NUEVO: violaciones de integridad de base de datos ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> errores = new HashMap<>();
        Throwable causa = ex.getRootCause();
        String mensaje = "Violación de integridad de datos: " + (causa != null ? causa.getMessage() : ex.getMessage());
        errores.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), new Date(), errores));
    }


}
