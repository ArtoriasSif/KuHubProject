package DetalleReceta.msvc_DetalleReceta.exceptions;

import DetalleReceta.msvc_DetalleReceta.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
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

    @ExceptionHandler(ProductoExistenteException.class)
    public ResponseEntity<ErrorDTO> handleProductoExistenteException(ProductoExistenteException ex) {
        Map<String, String> errorMap = Collections.singletonMap("Producto existente", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(this.createErrorDTO(HttpStatus.CONFLICT.value(), new Date(), errorMap));
    }


}
