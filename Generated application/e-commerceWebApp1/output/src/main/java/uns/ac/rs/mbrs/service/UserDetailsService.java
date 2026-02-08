package uns.ac.rs.mbrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uns.ac.rs.mbrs.exception.NotFoundException;
import uns.ac.rs.mbrs.model.User;
import uns.ac.rs.mbrs.model.UserRole;
import uns.ac.rs.mbrs.repository.UserRepository;
import uns.ac.rs.mbrs.repository.UserRoleRepository;
import uns.ac.rs.mbrs.security.UserFactory;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findByUsernameAndNotDeleted(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username '%s' is not found!", username)));

        return UserFactory.create(user);
    }

    public String findRoleByUsername(String username){
        Optional<UserRole> ret = userRoleRepository.findByUsername(username);
        if(ret.isPresent()){
            return ret.get().getName();
        }
        return "";
    }


}