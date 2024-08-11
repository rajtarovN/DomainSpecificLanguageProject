package uns.ac.rs.mbrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.mbrs.dtos.LoginDTO;
import uns.ac.rs.mbrs.exception.BadRequestException;
import uns.ac.rs.mbrs.exception.NotFoundException;
import uns.ac.rs.mbrs.model.Customer;
import uns.ac.rs.mbrs.model.Seller;
import uns.ac.rs.mbrs.model.User;
import uns.ac.rs.mbrs.repository.UserRepository;
import uns.ac.rs.mbrs.repository.UserRoleRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void save(User user) {
        userRepository.save(user);
    }


    public String loggedFirstTime(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
        if(!user.isPresent()){
            throw new NotFoundException("User with username "+ loginDTO.getUsername() + " not found!");
        }
        SecurityContextHolder.clearContext();
        Authentication authentication;
        try {
            user.get().setPassword(passwordEncoder.encode(loginDTO.getPassword()));
            user.get().setLoggedFirstTime(false);
            save(user.get());
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Your credentials are bad. Please, try again");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Successfully changed password!";
    }

    public boolean findIsLoggedInFirstTimeByUsername(String username){
        return userRepository.findIsLoggedInFirstTimeByUsername(username);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        else{
            return user;
        }
    }

    public String addUser(LoginDTO newUserDTO) {
        if (userRepository.findByUsernameAndNotDeleted(newUserDTO.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists!");
        }

        if (newUserDTO.getRole().equals("CUSTOMER")) {
            Customer newCustomer = new Customer(newUserDTO);
            newCustomer.setRole(userRoleRepository.findByName("CUSTOMER").get());
            newCustomer.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
            userRepository.saveAndFlush(newCustomer);
        }else{
            Seller newSeller = new Seller(newUserDTO);
            newSeller.setRole(userRoleRepository.findByName("SELLER").get());
            newSeller.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
            userRepository.saveAndFlush(newSeller);
        }
        return newUserDTO.getUsername();
    }
}