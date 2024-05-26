package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.Person;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/person")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<PersonDTO>> get() throws NotFoundException {
        List<PersonDTO> person = personService.get();
        return ResponseEntity.ok().body(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findOne(@PathVariable Long id) throws NotFoundException {
        PersonDTO person = personService.findOne(id);
        return ResponseEntity.ok().body(person);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> post(@RequestBody PersonDTO person) {
        PersonDTO person1 = personService.save(person);
        if (person == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(person1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> put(@PathVariable Long id, @RequestBody PersonDTO person) {
        PersonDTO person1 = personService.update(id, person);
        return person != null ? ResponseEntity.ok(person1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}