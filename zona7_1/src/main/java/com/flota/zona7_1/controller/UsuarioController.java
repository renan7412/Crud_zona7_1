package com.flota.zona7_1.controller;

import com.flota.zona7_1.model.Usuario;
import com.flota.zona7_1.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioR;

    @GetMapping("/")
    public String ListaUsuarios(Model model) {

        // Cargo la lista de todos los usuarios
        List<Usuario> usuarios = usuarioR.findAll();

        // Asocio la variable usuarios la lista de usuarios cargada en la BD
        model.addAttribute("usuarios", usuarios);

        // Cargamos la vista (html)
        return "listaUsuarios";

    }

    @GetMapping("/usuario/nuevo")
    public String nuevoUsuario(Model model) {
        // Creamos un objeto de tipo Entidad Usuario
        Usuario usuario = new Usuario();
        // Se lo pasamos al modelo para que llegue al formulario
        model.addAttribute("usuario", usuario);
        // Cargamos el formulario de crear nuevo usuario
        return "crearUsuario";
    }


    @GetMapping("/usuario/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> datoBD = usuarioR.findById(id);
        if (datoBD.isPresent()) {
            model.addAttribute("usuario", datoBD.get());
            return "editar";
        }
        model.addAttribute("error", "registro no encontrado en la BD");
        return "error";
    }

    // Obtenemos un usuario y su información de la BD
    @GetMapping("/usuario/{id}")
    public String getUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> datoBD = usuarioR.findById(id);
        if (datoBD.isPresent()) {
            model.addAttribute("usuario", datoBD.get());
            return "form";
        }
        model.addAttribute("error", "registro no encontrado en la BD");
        return "error";
    }
    // Con este método creamos a un nuevo usuario
    @PostMapping("/usuario/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario) {
        usuarioR.save(usuario);
        return "redirect:/";
    }

    @PostMapping("/usuario/modificar/{id}")
    public String modificarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario){
        // Atrvés del id del usuario modificaamos al este:
        usuario.setId(id);
        // Modificamos y guardamos al usuario
        usuarioR.save(usuario);
        // Redireccionamos hacia el listado con el reteurn
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id, Model model) {

        // Eliminamos el usuario por su id
        usuarioR.deleteById(id);
        return "redirect:/";
    }

}
