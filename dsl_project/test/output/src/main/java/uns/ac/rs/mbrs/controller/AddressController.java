package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.Address;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        return ResponseEntity.ok().body(addressService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDTO>> get() throws NotFoundException {
        List<AddressDTO> address = addressService.get();
        return ResponseEntity.ok().body(address);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findOne(@PathVariable Long id) throws NotFoundException {
        AddressDTO address = addressService.findOne(id);
        return ResponseEntity.ok().body(address);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> post(@RequestBody AddressDTO address) {
        AddressDTO address1 = addressService.save(address);
        if (address == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(address1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> put(@PathVariable Long id, @RequestBody AddressDTO address) {
        AddressDTO address1 = addressService.update(id, address);
        return address != null ? ResponseEntity.ok(address1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}