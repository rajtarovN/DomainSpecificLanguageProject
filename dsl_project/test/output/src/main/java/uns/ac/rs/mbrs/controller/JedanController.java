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
@RequestMapping(value = "/api/jedan")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JedanController {

    @Autowired
    private JedanService jedanService;

    @GetMapping
    public ResponseEntity<List<JedanDTO>> findAll() {
        return ResponseEntity.ok().body(jedanService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<JedanDTO>> get() throws NotFoundException {
        List<JedanDTO> jedan = jedanService.get();
        return ResponseEntity.ok().body(jedan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JedanDTO> findOne(@PathVariable Long id) throws NotFoundException {
        JedanDTO jedan = jedanService.findOne(id);
        return ResponseEntity.ok().body(jedan);
    }




    @PostMapping
    public ResponseEntity<JedanDTO> post(@RequestBody JedanDTO jedan) {
        JedanDTO jedan1 = jedanService.save(jedan);
        if (jedan == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(jedan1, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<JedanDTO> put(@PathVariable Long id, @RequestBody JedanDTO jedan) {
        JedanDTO jedan1 = jedanService.update(id, jedan);
        return jedan != null ? ResponseEntity.ok(jedan1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        jedanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        jedanService.delete(id);
        return ResponseEntity.noContent().build();
    }


}