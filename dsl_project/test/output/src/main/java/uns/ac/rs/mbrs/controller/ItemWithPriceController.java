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
@RequestMapping(value = "/api/itemWithPrice")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemWithPriceController {

    @Autowired
    private ItemWithPriceService itemWithPriceService;

    @GetMapping
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<ItemWithPriceDTO>> findAll() {
        return ResponseEntity.ok().body(itemWithPriceService.findAll());
    }

    @GetMapping("/")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<ItemWithPriceDTO>> get() throws NotFoundException {
        List<ItemWithPriceDTO> itemWithPrice = itemWithPriceService.get();
        return ResponseEntity.ok().body(itemWithPrice);
    }

    @GetMapping("/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<ItemWithPriceDTO> findOne(@PathVariable Long id) throws NotFoundException {
        ItemWithPriceDTO itemWithPrice = itemWithPriceService.findOne(id);
        return ResponseEntity.ok().body(itemWithPrice);
    }

    @PostMapping
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<ItemWithPriceDTO> post(@RequestBody ItemWithPriceDTO itemWithPrice) {
        ItemWithPriceDTO itemWithPrice1 = itemWithPriceService.save(itemWithPrice);
        if (itemWithPrice == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(itemWithPrice1, HttpStatus.CREATED);

    }






    @PutMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<ItemWithPriceDTO> put(@PathVariable Long id, @RequestBody ItemWithPriceDTO itemWithPrice) {
        ItemWithPriceDTO itemWithPrice1 = itemWithPriceService.update(id, itemWithPrice);
        return itemWithPrice != null ? ResponseEntity.ok(itemWithPrice1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        itemWithPriceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        itemWithPriceService.delete(id);
        return ResponseEntity.noContent().build();
    }


}