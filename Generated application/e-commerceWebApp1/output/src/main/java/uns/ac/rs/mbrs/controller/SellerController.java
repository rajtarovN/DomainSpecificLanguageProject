package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(value = "/api/seller")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<List<SellerDTO>> findAll() {
        return ResponseEntity.ok().body(sellerService.findAll());
    }

    @GetMapping("/")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<List<SellerDTO>> get() throws NotFoundException {
        List<SellerDTO> seller = sellerService.get();
        return ResponseEntity.ok().body(seller);
    }

    @GetMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<SellerDTO> findOne(@PathVariable Long id) throws NotFoundException {
        SellerDTO seller = sellerService.findOne(id);
        return ResponseEntity.ok().body(seller);
    }




    @PostMapping
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<SellerDTO> post(@RequestBody SellerDTO seller) {
        SellerDTO seller1 = sellerService.save(seller);
        if (seller == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(seller1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<SellerDTO> put(@PathVariable Long id, @RequestBody SellerDTO seller) {
        SellerDTO seller1 = sellerService.update(id, seller);
        return seller != null ? ResponseEntity.ok(seller1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}