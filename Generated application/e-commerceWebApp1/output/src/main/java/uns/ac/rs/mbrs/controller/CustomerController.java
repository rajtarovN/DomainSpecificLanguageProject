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
@RequestMapping(value = "/api/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @GetMapping("/")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<List<CustomerDTO>> get() throws NotFoundException {
        List<CustomerDTO> customer = customerService.get();
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<CustomerDTO> findOne(@PathVariable Long id) throws NotFoundException {
        CustomerDTO customer = customerService.findOne(id);
        return ResponseEntity.ok().body(customer);
    }




    @PostMapping
                @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CustomerDTO> post(@RequestBody CustomerDTO customer) {
        CustomerDTO customer1 = customerService.save(customer);
        if (customer == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
                        @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'CUSTOMER')")
    public ResponseEntity<CustomerDTO> put(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        CustomerDTO customer1 = customerService.update(id, customer);
        return customer != null ? ResponseEntity.ok(customer1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")

                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}