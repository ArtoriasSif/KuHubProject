package Rol.Msvc_Rol.exceptions;

import Rol.Msvc_Rol.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarRolNoEncontrado(RolNotFoundException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());

        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.NOT_FOUND.value());
        dto.setDate(new Date());
        dto.setErrors(errores);

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RolExistenteException.class)
    public ResponseEntity<ErrorDTO> manejarRolExistente(RolExistenteException ex) {
        Map<String, String> errores = new HashMap<>();
        errores.put("mensaje", ex.getMessage());

        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        dto.setDate(new Date());
        dto.setErrors(errores);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }




}
