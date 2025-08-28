package Asignatura.Msvc_Asignatura.exceptions;

import Asignatura.Msvc_Asignatura.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(AsignaturaNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarAsignaturaNoEncontrada(AsignaturaNotFoundException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.NOT_FOUND.value());
        dto.setDate(new Date());
        dto.setErrors(errores);
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
