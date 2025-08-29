package Usuario.Msvc_Usuario.services;

import Usuario.Msvc_Usuario.clients.RolClientRest;
import Usuario.Msvc_Usuario.clients.SeccionClientRest;
import Usuario.Msvc_Usuario.dtos.UpdateIdSeccionesUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByAdministratorRequestDTO;
import Usuario.Msvc_Usuario.dtos.UpdateUsuarioByUsuarioRequestDTO;
import Usuario.Msvc_Usuario.exceptions.*;
import Usuario.Msvc_Usuario.models.Seccion;
import Usuario.Msvc_Usuario.models.entity.Usuario;
import Usuario.Msvc_Usuario.repositories.UsuarioRepository;
import Usuario.Msvc_Usuario.utils.StringUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SeccionClientRest seccionClientRest;

    @Autowired
    private RolClientRest rolClientRest;

    @Transactional
    @Override
    public Usuario findByIdUsuario (Long idUsuario){
        return usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario)
        );
    }

    @Transactional
    @Override
    public Usuario findByUsername (String username){
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new UsuarioNotFoundByUsernameException(username)
        );
    }

    @Override
    public boolean existsByIdUsuario(Long idUsuario) {
        return usuarioRepository.existsById(idUsuario);
    }

    @Transactional
    @Override
    public List<Usuario> findAllUsuarios (){
        return usuarioRepository.findAll();
    }

    @Transactional
    @Override
    public Usuario saveUsuario(Usuario usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailUsuarioExistenteException(usuario.getEmail());
        }

        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new UsernameUsuarioExistenteException(usuario.getUsername());
        }

        usuario.setPrimeroNombre(StringUtils.capitalizarPalabras(usuario.getPrimeroNombre().trim()));
        usuario.setApellidoPaterno(StringUtils.capitalizarPalabras(usuario.getApellidoPaterno().trim()));
        if (usuario.getSegundoNombre() != null) {
            usuario.setSegundoNombre(StringUtils.capitalizarPalabras(usuario.getSegundoNombre().trim()));
        }
        if (usuario.getApellidoMaterno() != null) {
            usuario.setApellidoMaterno(StringUtils.capitalizarPalabras(usuario.getApellidoMaterno().trim()));
        }


        if (usuario.getIdSecciones() != null && !usuario.getIdSecciones().isEmpty()) {
            List<Long> seccionesValidas = usuario.getIdSecciones().stream()
                    .filter(idSeccion -> {
                        try {
                            Seccion seccion = seccionClientRest.findByIdSeccion(idSeccion).getBody();
                            return seccion != null;
                        } catch (FeignException.NotFound e) {
                            log.warn("Sección con id {} no encontrada, será ignorada.", idSeccion);
                            return false;
                        } catch (FeignException e) {
                            throw new RuntimeException(
                                    "Error al comunicarse con el servicio de secciones: " + e.status(), e
                            );
                        }
                    })
                    .collect(Collectors.toList());

            usuario.setIdSecciones(seccionesValidas);
        }

        if (usuario.getIdRol() == null) {
            usuario.setIdRol(1L); // asigna rol por defecto
        }else{
            if(!rolClientRest.existeRolById(usuario.getIdRol())){
                usuario.setIdRol(1L); // Si no existe el rol, asigna rol por defecto
            }
        }


        return usuarioRepository.save(usuario);
    }

    //No actualiza idseciones
    @Transactional
    @Override
    public Usuario updateUsuarioAdministrador
    (Long idUsuario,UpdateUsuarioByAdministratorRequestDTO request) {
        Usuario usuario = null;
        if(request.getIdSecciones() != null){
            usuario = UpdateAddIdSeccionesUsuarioByAdministrator
                    (idUsuario,
                    new UpdateIdSeccionesUsuarioByAdministratorRequestDTO(request.getIdSecciones()));
        }else {
            usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                    () -> new UsuarioNotFoundException(idUsuario)
            );
        }

        if (request.getIdRol() != null) {
            if(usuario.getIdRol() != request.getIdRol()){
                if(!rolClientRest.existeRolById(request.getIdRol())){
                    usuario.setIdRol(1L); // Si no existe el rol, asigna rol por defecto
                }else{
                    usuario.setIdRol(request.getIdRol());
                }
            }
        }

        if(request.getPrimeroNombre() != null){
            String primerNombre = StringUtils.capitalizarPalabras(request.getPrimeroNombre());
            if(!usuario.getPrimeroNombre().equals(primerNombre)){
                usuario.setPrimeroNombre(primerNombre);
            }
        }

        if(request.getApellidoPaterno() != null){
            String apellidoPaterno = StringUtils.capitalizarPalabras(request.getApellidoPaterno());
            if(!usuario.getApellidoPaterno().equals(apellidoPaterno)){
                usuario.setApellidoPaterno(apellidoPaterno);
            }
        }

        if(request.getSegundoNombre() != null){
            String segundoNombre = StringUtils.capitalizarPalabras(request.getSegundoNombre());
            if(!usuario.getSegundoNombre().equals(segundoNombre)){
                usuario.setSegundoNombre(segundoNombre);
            }
        }

        if(request.getApellidoMaterno() != null){
            String apellidoMaterno = StringUtils.capitalizarPalabras(request.getApellidoMaterno());
            if(!usuario.getApellidoMaterno().equals(apellidoMaterno)){
                usuario.setApellidoMaterno(apellidoMaterno);
            }
        }

        if(request.getEmail() != null){
            if(!usuario.getEmail().equals(request.getEmail())){
                if(usuarioRepository.existsByEmail(request.getEmail())){
                    throw new EmailUsuarioExistenteException(request.getEmail());
                }
                usuario.setEmail(request.getEmail());
            }
        }

        if(request.getUsername() != null){
            if(!usuario.getUsername().equals(request.getUsername())){
                if(usuarioRepository.existsByUsername(request.getUsername())){
                    throw new UsernameUsuarioExistenteException(request.getUsername());
                }
                usuario.setUsername(request.getUsername());
            }
        }

        if(request.getPassword() != null){
            if(!usuario.getPassword().equals(request.getPassword())){
                usuario.setPassword(request.getPassword());
            }
        }

        return usuarioRepository.save(usuario);
    }


    @Transactional
    @Override
    public Usuario UpdateAddIdSeccionesUsuarioByAdministrator
            (Long idUsuario, UpdateIdSeccionesUsuarioByAdministratorRequestDTO listaRequest) {

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario)
        );

        List<Long> listaIdSecciones = usuario.getIdSecciones();

        if (listaRequest != null && listaRequest.getIdSecciones() != null) {
            for (Long idSeccionRequest : listaRequest.getIdSecciones()) {
                // Solo agregamos si NO está ya en la lista y además existe en el microservicio
                if (!listaIdSecciones.contains(idSeccionRequest) &&
                        seccionClientRest.existeSeccionById(idSeccionRequest)) {

                    listaIdSecciones.add(idSeccionRequest);
                }
            }
        }

        usuario.setIdSecciones(listaIdSecciones);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Usuario UpdateRemoveIdSeccionesUsuarioByAdministrator(
            Long idUsuario,
            UpdateIdSeccionesUsuarioByAdministratorRequestDTO listaRequest) {

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario)
        );

        List<Long> listaIdSecciones = usuario.getIdSecciones();

        if (listaRequest != null && listaRequest.getIdSecciones() != null) {
            for (Long idSeccionRequest : listaRequest.getIdSecciones()) {
                // Eliminamos solo si el usuario ya la tiene
                listaIdSecciones.remove(idSeccionRequest);
            }
        }

        usuario.setIdSecciones(listaIdSecciones);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public Usuario UpdateUsuarioByUsuarioWithId
            (Long idUsuario, UpdateUsuarioByUsuarioRequestDTO requestDTO) {

        Usuario U =usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new UsuarioNotFoundException(idUsuario)
        );

        if(requestDTO.getUsername() != null){
            if(!requestDTO.getUsername().equals(U.getUsername())){
                U.setUsername(requestDTO.getUsername());
            }
        }
        if(requestDTO.getPassword() != null){
            if(!requestDTO.getPassword().equals(U.getPassword())){
                U.setPassword(requestDTO.getPassword());
            }
        }

        return usuarioRepository.save(U);
    }

    @Transactional
    @Override
    public Usuario UpdateUsuarioByUsuarioWithUsername(
            String userName, UpdateUsuarioByUsuarioRequestDTO requestDTO){

        Usuario U = usuarioRepository.findByUsername(userName).orElseThrow(
                () -> new UsuarioNotFoundByUsernameException(userName)
        );

        if(requestDTO.getUsername() != null){
            if(!requestDTO.getUsername().equals(U.getUsername())){
                U.setUsername(requestDTO.getUsername());
            }
        }
        if(requestDTO.getPassword() != null){
            if(!requestDTO.getPassword().equals(U.getPassword())){
                U.setPassword(requestDTO.getPassword());
            }
        }

        return usuarioRepository.save(U);
    }

    @Transactional
    @Override
    public void deleteUsuarioById(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }




}
