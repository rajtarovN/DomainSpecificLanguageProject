package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.Jedan;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/id")
    public ResponseEntity<JedanDTO> findOne(@PathVariable Long id) throws NotFoundException {
        JedanDTO jedan = jedanService.findOne(id);
        return ResponseEntity.ok().body(jedan);
    }

    @PostMapping
    public ResponseEntity<JedanDTO> post(@RequestBody Jedan jedan) {
        JedanDTO jedan1 = jedanService.save(jedan);
        if (jedan == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(jedan1, HttpStatus.CREATED);
    }

    @PutMapping("/id")
    public ResponseEntity<JedanDTO> put(@PathVariable Long id, @RequestBody Jedan jedan) {
        JedanDTO jedan1 = jedanService.update(jedan);
        return jedan != null ? ResponseEntity.ok(jedan1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        jedanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/id")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        jedanService.delete(id);
        return ResponseEntity.noContent().build();
    }

}