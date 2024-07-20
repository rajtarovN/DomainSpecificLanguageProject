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
@RequestMapping(value = "/api/bill")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<BillDTO>> findAll() {
        return ResponseEntity.ok().body(billService.findAll());
    }

    @GetMapping("/")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<BillDTO>> get() throws NotFoundException {
        List<BillDTO> bill = billService.get();
        return ResponseEntity.ok().body(bill);
    }

    @GetMapping("/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<BillDTO> findOne(@PathVariable Long id) throws NotFoundException {
        BillDTO bill = billService.findOne(id);
        return ResponseEntity.ok().body(bill);
    }





    @PostMapping
@PreAuthorize("hasAnyRole('CUSTOMER')")    public ResponseEntity<BillDTO> post(@RequestBody BasketDTO basket) {
        BillDTO bill1 = null;
        try {
            bill1 = billService.save(basket);
        } catch (Exception e) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bill1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bill1, HttpStatus.CREATED);
    }

    @PostMapping("/make-with-id/{id}")
@PreAuthorize("hasRole( 'CUSTOMER')")    public ResponseEntity<BillDTO> post(@PathVariable long id) {
        BillDTO bill1 = null;
        try {
            bill1 = billService.saveWithId(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bill1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bill1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<BillDTO> put(@PathVariable Long id, @RequestBody BillDTO bill) {
        BillDTO bill1 = billService.update(id, bill);
        return bill != null ? ResponseEntity.ok(bill1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")

                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        billService.delete(id);
        return ResponseEntity.noContent().build();
    }


}