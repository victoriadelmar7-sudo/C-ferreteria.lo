package com.ferreteria.eldesvare.usuarios.controller;

import com.ferreteria.eldesvare.usuarios.model.Usuario;
import com.ferreteria.eldesvare.usuarios.service.UsuarioService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // MOSTRAR LOGIN
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) Long productoId,
                               HttpSession session) {

        if (productoId != null) {
            session.setAttribute("productoPendiente", productoId);
        }

        return "usuarios/login";
    }

    // MOSTRAR REGISTRO
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "usuarios/registros";
    }

    // LOGIN FALLIDO
    @GetMapping("/login-aviso")
    public String loginAviso() {
        return "usuarios/login-aviso";
    }

    // PROCESAR LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String correo,
                        @RequestParam String contrasena,
                        HttpSession session){

        Optional<Usuario> usuarioOpt = service.buscarPorCorreo(correo);

        if(usuarioOpt.isPresent()){

            Usuario usuario = usuarioOpt.get();

            if(usuario.getContrasena().equals(contrasena)){

                // GUARDAR USUARIO EN SESION
                session.setAttribute("usuario", usuario);

                // PRODUCTO QUE QUERIA COMPRAR
                Long productoPendiente =
                        (Long) session.getAttribute("productoPendiente");

                if(productoPendiente != null){

                    session.removeAttribute("productoPendiente");

                    return "redirect:/carrito/agregar?productoId=" + productoPendiente;
                }

                // REDIRECCION SEGUN ROL
                if(usuario.getRol().name().equals("ADMIN")){
                    return "redirect:/admin/dashboard";
                }

                if(usuario.getRol().name().equals("VENDEDOR")){
                    return "redirect:/vendedor/dashboard";
                }

                // CLIENTE
                return "redirect:/ferreteria-el-desvare";
            }
        }

        return "redirect:/usuarios/login-aviso";
    }

    // CERRAR SESIÓN
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/ferreteria-el-desvare";
    }


    @GetMapping("/vista")
    public String vistaUsuarios(Model model) {

        model.addAttribute("usuarios", service.listarUsuarios());

        return "usuarios/usuarios";
    }

    // LISTAR USUARIOS (API)
    @GetMapping
    @ResponseBody
    public List<Usuario> listar() {
        return service.listarUsuarios();
    }

    // OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Usuario> obtener(@PathVariable Long id) {
        return service.obtenerUsuarioPorId(id);
    }

    // CREAR USUARIO
    @PostMapping
    @ResponseBody
    public Usuario crear(@RequestBody Usuario u) {
        return service.guardarUsuario(u);
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    @ResponseBody
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario u) {
        return service.actualizarUsuario(id, u);
    }

    // ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    @ResponseBody
    public void eliminar(@PathVariable Long id) {
        service.eliminarUsuario(id);
    }

    // BUSCAR POR CORREO
    @GetMapping("/correo/{correo}")
    @ResponseBody
    public Optional<Usuario> buscarPorCorreo(@PathVariable String correo) {
        return service.buscarPorCorreo(correo);
    }
}