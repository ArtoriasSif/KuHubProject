package Asignatura.Msvc_Asignatura.exceptions;

import Asignatura.Msvc_Asignatura.dtos.ErrorDTO;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
