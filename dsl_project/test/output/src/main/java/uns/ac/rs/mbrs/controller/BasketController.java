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
@RequestMapping(value = "/api/basket")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping
                    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<BasketDTO>> findAll() {
        return ResponseEntity.ok().body(basketService.findAll());
    }

    @GetMapping("/")
                    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<BasketDTO>> get() throws NotFoundException {
        List<BasketDTO> basket = basketService.get();
        return ResponseEntity.ok().body(basket);
    }

    @GetMapping("/{id}")
                    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BasketDTO> findOne(@PathVariable Long id) throws NotFoundException {
        BasketDTO basket = basketService.findOne(id);
        return ResponseEntity.ok().body(basket);
       }

    @PostMapping
                    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BasketDTO> post(@RequestBody BasketDTO basket) {
        BasketDTO basket1 = basketService.save(basket);
        if (basket == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(basket1, HttpStatus.CREATED);

    }






    @PutMapping("/{id}")
                        @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<BasketDTO> put(@PathVariable Long id, @RequestBody BasketDTO basket) {
        BasketDTO basket1 = basketService.update(id, basket);
        return basket != null ? ResponseEntity.ok(basket1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")

                    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        basketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        basketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{basketId}/{itemId}/{quantity}")
@PreAuthorize("hasRole('CUSTOMER')")    public ResponseEntity<BasketDTO> put(@PathVariable Long basketId, @PathVariable Long itemId, @PathVariable int quantity) {
        BasketDTO basket1 = null;
        try {
            basket1 = basketService.updateWithItem(basketId, itemId, quantity);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket1 != null ? ResponseEntity.ok(basket1) : ResponseEntity.badRequest().build();
    }
    @PutMapping("/{basketId}/{itemId}")
@PreAuthorize("hasRole( 'CUSTOMER')")    public ResponseEntity<BasketDTO> removeItem(@PathVariable Long basketId, @PathVariable Long itemId) {
        BasketDTO basket1 = null;
        try {
            basket1 = basketService.removeItem(basketId, itemId);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket1 != null ? ResponseEntity.ok(basket1) : ResponseEntity.badRequest().build();
    }

}