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
@RequestMapping(value = "/api/tri")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TriController {

    @Autowired
    private TriService triService;

    @GetMapping
    public ResponseEntity<List<TriDTO>> findAll() {
        return ResponseEntity.ok().body(triService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<TriDTO>> get() throws NotFoundException {
        List<TriDTO> tri = triService.get();
        return ResponseEntity.ok().body(tri);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriDTO> findOne(@PathVariable Long id) throws NotFoundException {
        TriDTO tri = triService.findOne(id);
        return ResponseEntity.ok().body(tri);
    }




    @PostMapping
    public ResponseEntity<TriDTO> post(@RequestBody TriDTO tri) {
        TriDTO tri1 = triService.save(tri);
        if (tri == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(tri1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TriDTO> put(@PathVariable Long id, @RequestBody TriDTO tri) {
        TriDTO tri1 = triService.update(id, tri);
        return tri != null ? ResponseEntity.ok(tri1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        triService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        triService.delete(id);
        return ResponseEntity.noContent().build();
    }


}