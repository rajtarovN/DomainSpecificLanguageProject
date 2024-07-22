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
@RequestMapping(value = "/api/pedrson")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedrsonController {

    @Autowired
    private PedrsonService pedrsonService;

    @GetMapping
    public ResponseEntity<List<PedrsonDTO>> findAll() {
        return ResponseEntity.ok().body(pedrsonService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<PedrsonDTO>> get() throws NotFoundException {
        List<PedrsonDTO> pedrson = pedrsonService.get();
        return ResponseEntity.ok().body(pedrson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedrsonDTO> findOne(@PathVariable Long id) throws NotFoundException {
        PedrsonDTO pedrson = pedrsonService.findOne(id);
        return ResponseEntity.ok().body(pedrson);
    }




    @PostMapping
    public ResponseEntity<PedrsonDTO> post(@RequestBody PedrsonDTO pedrson) {
        PedrsonDTO pedrson1 = pedrsonService.save(pedrson);
        if (pedrson == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(pedrson1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PedrsonDTO> put(@PathVariable Long id, @RequestBody PedrsonDTO pedrson) {
        PedrsonDTO pedrson1 = pedrsonService.update(id, pedrson);
        return pedrson != null ? ResponseEntity.ok(pedrson1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        pedrsonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        pedrsonService.delete(id);
        return ResponseEntity.noContent().build();
    }


}