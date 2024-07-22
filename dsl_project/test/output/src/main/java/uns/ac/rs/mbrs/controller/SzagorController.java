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
@RequestMapping(value = "/api/szagor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SzagorController {

    @Autowired
    private SzagorService szagorService;

    @GetMapping
    public ResponseEntity<List<SzagorDTO>> findAll() {
        return ResponseEntity.ok().body(szagorService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<SzagorDTO>> get() throws NotFoundException {
        List<SzagorDTO> szagor = szagorService.get();
        return ResponseEntity.ok().body(szagor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SzagorDTO> findOne(@PathVariable Long id) throws NotFoundException {
        SzagorDTO szagor = szagorService.findOne(id);
        return ResponseEntity.ok().body(szagor);
    }




    @PostMapping
    public ResponseEntity<SzagorDTO> post(@RequestBody SzagorDTO szagor) {
        SzagorDTO szagor1 = szagorService.save(szagor);
        if (szagor == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(szagor1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SzagorDTO> put(@PathVariable Long id, @RequestBody SzagorDTO szagor) {
        SzagorDTO szagor1 = szagorService.update(id, szagor);
        return szagor != null ? ResponseEntity.ok(szagor1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        szagorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        szagorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}