package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.Product;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<ProductDTO> findOne(@PathVariable Long id) throws NotFoundException {
        ProductDTO product = productService.findOne(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> post(@RequestBody Product product) {
        ProductDTO product1 = productService.save(product);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @PutMapping("/id")
    public ResponseEntity<ProductDTO> put(@PathVariable Long id, @RequestBody Product product) {
        ProductDTO product1 = productService.update(product);
        return product != null ? ResponseEntity.ok(product1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/id")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}