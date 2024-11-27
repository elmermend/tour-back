package com.travel.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.travel.dto.entrada.FechaDisponibleDto;
import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.*;
import com.travel.exception.NotFoundException;
import com.travel.repository.CaracteristicaRepository;
import com.travel.repository.CategoriaRepository;
import com.travel.service.S3Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.repository.ProductoRepository;
import com.travel.service.ProductoService;
import org.springframework.web.multipart.MultipartFile;

// Clase de servicio para manejar la lógica de productos
@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            CaracteristicaRepository caracteristicaRepository,
            S3Service s3Service,
            ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.s3Service = s3Service;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductoSalidaDto> listarTodosLosProductos() {
        return StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .map(this::convertirAProductoSalidaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoSalidaDto crearProducto(ProductoDto productoDTO) {
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        producto.setId(null);

        producto.setCategoria(obtenerCategoriaPorId(productoDTO.getCategoriaId()));
        producto.setCaracteristicas(obtenerCaracteristicasPorIds(productoDTO.getCaracteristicaIds()));

        producto.setFechasDisponibles(mapearFechasDisponibles(productoDTO.getFechasDisponibles(), producto));

        List<ProductoImagen> productoImagenes = cargarImagenesEnS3(productoDTO.getImagenes(), producto);
        producto.setImagenes(productoImagenes);

        Producto productoGuardado = productoRepository.save(producto);
        return convertirAProductoSalidaDto(productoGuardado);
    }

    private List<FechaDisponible> mapearFechasDisponibles(List<FechaDisponibleDto> fechasDto, Producto producto) {
        return fechasDto.stream().map(fechaDto -> {
            FechaDisponible fecha = new FechaDisponible();
            fecha.setFecha(fechaDto.getFecha());
            fecha.setStock(fechaDto.getStock());
            fecha.setProducto(producto);
            return fecha;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductoSalidaDto listarProductoPorId(Long id) {
        return convertirAProductoSalidaDto(obtenerPorId(id));
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        producto.getImagenes().forEach(imagen -> s3Service.eliminarImagen(imagen.getImagen()));
        productoRepository.delete(producto);
    }

    private Categoria obtenerCategoriaPorId(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría con ID " + categoriaId + " no encontrada"));
    }

    private List<Caracteristica> obtenerCaracteristicasPorIds(List<Long> caracteristicaIds) {
        return caracteristicaIds.stream()
                .map(id -> caracteristicaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Característica con ID " + id + " no encontrada")))
                .collect(Collectors.toList());
    }

    private List<ProductoImagen> cargarImagenesEnS3(List<MultipartFile> imagenes, Producto producto) {
        return imagenes.stream().map(imagen -> {
            String imagenUrl = s3Service.subirImagen(imagen);
            ProductoImagen productoImagen = new ProductoImagen();
            productoImagen.setImagen(imagenUrl);
            productoImagen.setProducto(producto);
            return productoImagen;
        }).collect(Collectors.toList());
    }

    private Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto con ID " + id + " no encontrado"));
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

