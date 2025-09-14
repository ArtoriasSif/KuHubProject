package Usuario.Msvc_Usuario.security.jwt;

import GlobalServerPorts.MicroserviciosConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Autowired
    private MicroserviciosConfig msvcConfig;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generarAccessToken(UserDetails userDetails, List<String> audiences) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("rol", userDetails.getAuthorities().iterator().next().getAuthority())
                .claim("aud", audiences)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8)) // 8 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    public String extraerRol(String token) {
        return extraerClaims(token).get("rol", String.class);
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        return extraerUsername(token).equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean estaExpirado(String token) {
        return extraerClaims(token).getExpiration().before(new Date());
    }

    // Mapear rol → audiencias usando MicroserviciosConfig
    public List<String> mapRolToAudiences(UserDetails userDetails) {
        String rol = userDetails.getAuthorities().iterator().next().getAuthority();
        List<String> audiences = new ArrayList<>();

        // Todos los usuarios siempre acceden al msvc-usuario
        audiences.add("msvc-usuario");

        switch (rol) {
            case "ROLE_ADMINISTRADOR":
                audiences.add("msvc-rol");
                audiences.add("msvc-asignatura");
                audiences.add("msvc-seccion"); // ✅ Debe estar exactamente así
                break;

            case "ROLE_CO_ADMINISTRADOR":
                audiences.add("msvc-rol");
                audiences.add("msvc-asignatura");
                audiences.add("msvc-seccion");
                break;

            case "ROLE_DOCENTE":
            case "ROLE_DOCENTE_COORDINADOR":
                audiences.add("msvc-rol");
                audiences.add("msvc-seccion");
                break;

            case "ROLE_ENCARGADO_BODEGA":
            case "ROLE_AUXILIAR_ENCARGADO_BODEGA":
                audiences.add("msvc-inventario");
                audiences.add("msvc-seccion");
                break;
        }

        return audiences;
    }
}

