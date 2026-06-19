package be.bstorm.tf_java_2026_iac_bend.controllers;

import be.bstorm.tf_java_2026_iac_bend.entities.Product;
import be.bstorm.tf_java_2026_iac_bend.repositories.ProductRepository;
import be.bstorm.tf_java_2026_iac_bend.services.BlobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final BlobService blobService;

    public ProductController(ProductRepository productRepository, BlobService blobService) {
        this.productRepository = productRepository;
        this.blobService = blobService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> saveImage(MultipartFile file) {
        try {
            String path = blobService.uploadBlob("truc",file);
            return ResponseEntity.ok(path);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'upload du fichier : " + e.getMessage());
        }
    }
}
