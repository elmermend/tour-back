package com.travel.service.impl;

import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.Favorito;
import com.travel.entity.ProductoImagen;
import com.travel.entity.UserEntity;
import com.travel.entity.Producto;
import com.travel.repository.FavoritoRepository;
import com.travel.repository.ProductoRepository;
import com.travel.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.travel.service.FavoritoService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;
    private final FavoritoRepository favoritoRepository;
    private final ModelMapper modelMapper;

    public FavoritoServiceImpl(UserRepository userRepository, ProductoRepository productoRepository, FavoritoRepository favoritoRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productoRepository = productoRepository;
        this.favoritoRepository = favoritoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean guardarFavorito(String username, Long productoId) {
        UserEntity usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Verificar si ya es un favorito
        Optional<Favorito> favoritoExistente = favoritoRepository.findByUsuarioAndProducto(usuario, producto);

        // Si el favorito ya existe, no hacer nada
        if (favoritoExistente.isPresent()) {
            return false;  // Ya existe el favorito
        }

        // Crear un nuevo favorito y guardarlo
        Favorito nuevoFavorito = new Favorito();
        nuevoFavorito.setUsuario(usuario);
        nuevoFavorito.setProducto(producto);

        favoritoRepository.save(nuevoFavorito);
        return true;  // Favorito creado con éxito
    }


    public void quitarFavorito(String currentUserName, Long productoId) {

        UserEntity usuario = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        // Buscar el favorito en la base de datos
        Optional<Favorito> favoritoOptional = favoritoRepository.findByUsuarioAndProducto(usuario, producto);

        // Si el favorito existe, lo eliminamos
        if (favoritoOptional.isPresent()) {
            Favorito favorito = favoritoOptional.get();  // Desempaquetamos el valor de Optional
            favoritoRepository.delete(favorito);          // Eliminar el favorito
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no es un favorito del usuario");
        }
    }

    @Override
    public List<ProductoSalidaDto> obtenerFavoritosPorUsuario(String currentUserName) {
        UserEntity usuario = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Obtener los favoritos del usuario
        List<Favorito> favoritos = favoritoRepository.findByUsuario(usuario);

        // Extraer los productos favoritos de los Favoritos
        List<Producto> productosFavoritos = favoritos.stream()
                .map(Favorito::getProducto)
                .collect(Collectors.toList());

        // Convertir los productos a ProductoSalidaDto
        return productosFavoritos.stream()
                .map(this::convertirAProductoSalidaDto)
                .collect(Collectors.toList());
    }

    private ProductoSalidaDto convertirAProductoSalidaDto(Producto producto) {
        ProductoSalidaDto productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);
        productoSalidaDto.setImagenes(extraerImagenUrls(producto));
        return productoSalidaDto;
    }

    private List<String> extraerImagenUrls(Producto producto) {
        return producto.getImagenes().stream()
                .map(ProductoImagen::getImagen)
                .collect(Collectors.toList());
    }
}