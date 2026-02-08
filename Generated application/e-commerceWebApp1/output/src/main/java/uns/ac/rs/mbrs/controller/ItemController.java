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
@RequestMapping(value = "/api/item")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<ItemDTO>> findAll() {
        return ResponseEntity.ok().body(itemService.findAll());
    }

    @GetMapping("/")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<ItemDTO>> get() throws NotFoundException {
        List<ItemDTO> item = itemService.get();
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<ItemDTO> findOne(@PathVariable Long id) throws NotFoundException {
        ItemDTO item = itemService.findOne(id);
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<ItemDTO> post(@RequestBody ItemDTO item) {
        ItemDTO item1 = itemService.save(item);
        if (item == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(item1, HttpStatus.CREATED);

    }






    @PutMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<ItemDTO> put(@PathVariable Long id, @RequestBody ItemDTO item) {
        ItemDTO item1 = itemService.update(id, item);
        return item != null ? ResponseEntity.ok(item1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
            @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }


}