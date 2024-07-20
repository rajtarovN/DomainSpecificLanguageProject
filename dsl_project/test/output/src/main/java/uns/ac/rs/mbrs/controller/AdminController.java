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
@RequestMapping(value = "/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminDTO>> findAll() {
        return ResponseEntity.ok().body(adminService.findAll());
    }

    @GetMapping("/")
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminDTO>> get() throws NotFoundException {
        List<AdminDTO> admin = adminService.get();
        return ResponseEntity.ok().body(admin);
    }

    @GetMapping("/{id}")
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDTO> findOne(@PathVariable Long id) throws NotFoundException {
        AdminDTO admin = adminService.findOne(id);
        return ResponseEntity.ok().body(admin);
    }




    @PostMapping
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDTO> post(@RequestBody AdminDTO admin) {
        AdminDTO admin1 = adminService.save(admin);
        if (admin == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(admin1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminDTO> put(@PathVariable Long id, @RequestBody AdminDTO admin) {
        AdminDTO admin1 = adminService.update(id, admin);
        return admin != null ? ResponseEntity.ok(admin1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
                @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
                @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }


}