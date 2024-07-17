package uns.ac.rs.mbrs.controller;

import javassist.NotFoundException;
import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.service.*;
import uns.ac.rs.mbrs.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/action")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @GetMapping
    public ResponseEntity<List<ActionDTO>> findAll() {
        return ResponseEntity.ok().body(actionService.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<ActionDTO>> get() throws NotFoundException {
        List<ActionDTO> action = actionService.get();
        return ResponseEntity.ok().body(action);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionDTO> findOne(@PathVariable Long id) throws NotFoundException {
        ActionDTO action = actionService.findOne(id);
        return ResponseEntity.ok().body(action);
    }

    @PostMapping
    public ResponseEntity<ActionDTO> post(@RequestBody ActionDTO action) {
        ActionDTO action1 = actionService.save(action);
        if (action == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(action1, HttpStatus.CREATED);

    }






    @PutMapping("/{id}")
    public ResponseEntity<ActionDTO> put(@PathVariable Long id, @RequestBody ActionDTO action) {
        ActionDTO action1 = actionService.update(id, action);
        return action != null ? ResponseEntity.ok(action1) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        actionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/d/{id}")
    public ResponseEntity<?> deleOne(@PathVariable Long id) {
        actionService.delete(id);
        return ResponseEntity.noContent().build();
    }


}