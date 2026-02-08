package uns.ac.rs.mbrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.mbrs.dtos.LoginDTO;
import uns.ac.rs.mbrs.exception.BadRequestException;
import uns.ac.rs.mbrs.model.Customer;
import uns.ac.rs.mbrs.service.UserService;
import uns.ac.rs.mbrs.utils.TokenUtils;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {


    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> addUser(@RequestBody LoginDTO newUserDTO){
        try {

            userService.addUser(newUserDTO);
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }catch (BadRequestException e){
            return new ResponseEntity<>("Email already exists!", HttpStatus.FORBIDDEN);
        }
    }
}