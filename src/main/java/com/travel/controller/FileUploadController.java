package com.travel.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.entity.Imagen;
import com.travel.entity.Producto;
import com.travel.exception.StorageFileNotFoundException;
import com.travel.service.ProductoService;
import com.travel.service.StorageService;

@RequestMapping("/travel/imagenes")
@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
    ProductoService productoService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam(required = true, name = "file") MultipartFile file, @RequestParam(required = true, name = "productId") String productId,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"Imagen cargada exitosamente " + file.getOriginalFilename() + "!");

		Imagen imagen = new Imagen();
		Producto producto = productoService.listarProductosPorId(Long.valueOf(productId));

		if(Objects.isNull(producto)) {
			redirectAttributes.addFlashAttribute("message",
				"El producto ingresado no existe " + productId + "!");
		} else {
			imagen.setProducto(producto);
			imagen.setUrl(file.getOriginalFilename());
			productoService.adicionarImagen(imagen);
		}

		return "redirect:/travel/imagenes/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}