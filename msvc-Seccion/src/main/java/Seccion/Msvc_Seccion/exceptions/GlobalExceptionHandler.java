package Seccion.Msvc_Seccion.exceptions;

import Seccion.Msvc_Seccion.dtos.ErrorDTO;

import java.util.Date;
import java.util.Map;

public class GlobalExceptionHandler {
    private ErrorDTO createErrorDTO(int status, Date date, Map<String, String> errorMap) {
        ErrorDTO errorDTO = new ErrorDTO();

        errorDTO.setStatus(status);
        errorDTO.setDate(date);
        errorDTO.setErrors(errorMap);

        return errorDTO;
    }
}
