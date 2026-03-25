package com.ferreteria.eldesvare.proveedores.service;

import com.ferreteria.eldesvare.proveedores.model.Proveedor;
import com.ferreteria.eldesvare.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarProveedores(){
        return proveedorRepository.findAll();
    }

    public void guardar(Proveedor proveedor){
        proveedorRepository.save(proveedor);
    }

    public void eliminar(Long id){
        proveedorRepository.deleteById(id);
    }

}