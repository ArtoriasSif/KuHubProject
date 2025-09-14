package Seccion.Msvc_Seccion.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidator jwtValidator;

    private static final String MICROSERVICE_NAME = "msvc-seccion";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            boolean valido = jwtValidator.validarToken(token, MICROSERVICE_NAME);
            if (!valido) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o sin permisos para este microservicio");
                return;
            }

            // ⚡ Podrías cargar el rol si quieres usarlo más adelante
            String rol = jwtValidator.extraerRol(token);
            request.setAttribute("rol", rol);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Falta cabecera Authorization");
            return;
        }

        filterChain.doFilter(request, response);
    }
}