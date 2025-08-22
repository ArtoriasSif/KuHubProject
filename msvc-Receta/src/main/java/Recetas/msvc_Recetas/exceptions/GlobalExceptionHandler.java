package Recetas.msvc_Recetas.exceptions;

import Recetas.msvc_Recetas.dtos.ErrorDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDTO createErrorDTO(int status, Date date, Map<String, String> errorMap) {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setStatus(status);
        errorDTO.setDate(date);
        errorDTO.setErrors(errorMap);

        return errorDTO;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationFields(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), new Date(), errorMap));
    }


    @ExceptionHandler(RecetaException.class)
    public ResponseEntity<ErrorDTO> handleProductoException(RecetaException exception){

        if(exception.getMessage().contains(" no encontrado")) {
            // Esto nos sirve para cuando no existe en la base de datos el Producto
            Map<String, String> errorMap = Collections.singletonMap("Producto no encontrado", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(this.createErrorDTO(HttpStatus.NOT_FOUND.value(), new Date(), errorMap));


        }else{
            // Esto nos sirve para cuando el producto ya existe en nuestra base de datos
            Map<String, String> errorMap = Collections.singletonMap("Producto existente", exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(this.createErrorDTO(HttpStatus.CONFLICT.value(), new Date(), errorMap));
        }
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignNotFound(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El detalle de la receta no existe en el servicio de DetalleReceta");
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignError(FeignException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de DetalleReceta no está disponible");
    }
}
