package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.Dva;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/dva")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DvaController {

    @Autowired
    private DvaService dvaService;

    @GetMapping
    public ResponseEntity<List<DvaDTO>> findAll() {
        return ResponseEntity.ok().body(dvaService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<DvaDTO>> get() throws NotFoundException {
        List<DvaDTO> dva = dvaService.get();
        return ResponseEntity.ok().body(dva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DvaDTO> findOne(@PathVariable Long id) throws NotFoundException {
        DvaDTO dva = dvaService.findOne(id);
        return ResponseEntity.ok().body(dva);
    }

    @PostMapping
    public ResponseEntity<DvaDTO> post(@RequestBody DvaDTO dva) {
        DvaDTO dva1 = dvaService.save(dva);
        if (dva == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dva1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DvaDTO> put(@PathVariable Long id, @RequestBody DvaDTO dva) {
        DvaDTO dva1 = dvaService.update(id, dva);
        return dva != null ? ResponseEntity.ok(dva1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        dvaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        dvaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}