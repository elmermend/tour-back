package com.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
import com.travel.repository.ProductoRepository;
import com.travel.service.ProductoService;

// Clase de servicio para manejar la lógica de productos
@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;   

    // Método para listar todos los productos
    public List<Producto> listarProductos() {
        List<Producto> productos = StreamSupport
            .stream(productoRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

        return productos; // Devuelve la lista de productos
    }

    // Método para listar productos por id
    public Producto listarProductosPorId(long id) {
        return productoRepository.findById(id); // Devuelve un producto por id
    }

    // Método para listar productos por nombre
    public Producto listarProductosPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre); // Devuelve un producto por nombre
    }

    public Producto agregarProducto(Producto producto) throws TravelRepositoryException {
        Producto productoExistente = productoRepository.findByNombre(producto.getNombre());
        if(productoExistente != null) {
           throw new TravelRepositoryException("Producto con nombre: " + producto.getNombre() + " ya existe!");
        } else {
            Producto productoGuardado = productoRepository.save(producto);
            return productoGuardado;
        }
    }

    public void deleteProducto(long id) throws TravelRepositoryException{
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new TravelRepositoryException(id + "Producto no encontrado");
        }
    }


     
    

}


