package com.ferreteria.eldesvare.usuarios.service;

import com.ferreteria.eldesvare.usuarios.model.Usuario;
import com.ferreteria.eldesvare.usuarios.model.Rol;
import com.ferreteria.eldesvare.usuarios.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // LISTAR USUARIOS
    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    // OBTENER POR ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return repository.findById(id);
    }

    // GUARDAR USUARIO
    public Usuario guardarUsuario(Usuario u) {

        if(repository.existsByCorreo(u.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // SI NO TIENE ROL -> CLIENTE
        if(u.getRol() == null){
            u.setRol(Rol.CLIENTE);
        }

        return repository.save(u);
    }

    // ACTUALIZAR USUARIO
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {

        Optional<Usuario> existente = repository.findById(id);

        if(existente.isPresent()){

            Usuario u = existente.get();

            u.setNombre(datosActualizados.getNombre());
            u.setCorreo(datosActualizados.getCorreo());
            u.setContrasena(datosActualizados.getContrasena());
            u.setRol(datosActualizados.getRol());

            return repository.save(u);

        }else{
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    // ELIMINAR
    public void eliminarUsuario(Long id) {

        if(!repository.existsById(id)){
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        repository.deleteById(id);
    }

    // BUSCAR POR CORREO
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo);
    }

}