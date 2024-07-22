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
@RequestMapping(value = "/api/novv")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NovvController {

    @Autowired
    private NovvService novvService;

    @GetMapping
    public ResponseEntity<List<NovvDTO>> findAll() {
        return ResponseEntity.ok().body(novvService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<NovvDTO>> get() throws NotFoundException {
        List<NovvDTO> novv = novvService.get();
        return ResponseEntity.ok().body(novv);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NovvDTO> findOne(@PathVariable Long id) throws NotFoundException {
        NovvDTO novv = novvService.findOne(id);
        return ResponseEntity.ok().body(novv);
    }




    @PostMapping
    public ResponseEntity<NovvDTO> post(@RequestBody NovvDTO novv) {
        NovvDTO novv1 = novvService.save(novv);
        if (novv == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(novv1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<NovvDTO> put(@PathVariable Long id, @RequestBody NovvDTO novv) {
        NovvDTO novv1 = novvService.update(id, novv);
        return novv != null ? ResponseEntity.ok(novv1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        novvService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        novvService.delete(id);
        return ResponseEntity.noContent().build();
    }


}