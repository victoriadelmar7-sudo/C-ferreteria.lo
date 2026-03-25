package com.ferreteria.eldesvare.clientes.service;

import com.ferreteria.eldesvare.clientes.model.Cliente;
import com.ferreteria.eldesvare.clientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // 🔍 Buscar por email
    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }

    // ✅ Guardar cliente (SIN duplicados)
    public Cliente guardar(Cliente cliente) {

        Optional<Cliente> existente = clienteRepository.findByEmail(cliente.getEmail());

        if (existente.isPresent()) {
            return existente.get(); // evita duplicados
        }

        return clienteRepository.save(cliente);
    }

    // 📋 Listar todos los clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // ❌ Eliminar cliente por ID
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    // 🔥 Obtener o crear (para carrito)
    public Cliente obtenerOCrearCliente(Cliente cliente) {
        return guardar(cliente);
    }
}