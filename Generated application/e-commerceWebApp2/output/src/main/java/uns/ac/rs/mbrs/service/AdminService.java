package uns.ac.rs.mbrs.service;

import javassist.NotFoundException;
import java.util.ArrayList;
import uns.ac.rs.mbrs.repository.*;
import uns.ac.rs.mbrs.model.*;
import uns.ac.rs.mbrs.mapper.*;
import uns.ac.rs.mbrs.dtos.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AdminService  {

    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    public AdminService(
    AdminMapper adminMapper,
    AdminRepository adminRepository
) {

        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;

    }
  @Transactional
public AdminDTO save( AdminDTO admindto){

        Admin admin = adminMapper.toModel(admindto);
    Admin s = adminRepository.save(admin);
    return adminMapper.toDTO(s);
}

    public AdminDTO update(long id,AdminDTO admindto) {
    Optional<Admin> admin = adminRepository.findById(id);
    if (admin.isPresent()){



            Admin s = adminRepository.save(admin.get());
            return adminMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Admin> partialUpdate(Admin admin) {

    return adminRepository
        .findById(admin.getId())
        .map(existingAdmin -> {


            return existingAdmin;
        })
        .map(adminRepository::save);
}

@Transactional(readOnly = true)
public List<AdminDTO> findAll() {
    List<Admin> admins = adminRepository.findAll();
    List<AdminDTO> dtos = new ArrayList<>();
    for (Admin s : admins){
        AdminDTO dto = adminMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public AdminDTO findOne(Long id) throws NotFoundException {
    Optional<Admin> maybeAdmin =  adminRepository.findById(id);
    if (maybeAdmin.isPresent()) {
        Admin admin = maybeAdmin.get();
        return adminMapper.toDTO(admin);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Admin> maybeAdmin = adminRepository.findById(id);

    if (maybeAdmin.isPresent()) {
        Admin existingAdmin = maybeAdmin.get();
        existingAdmin.setDeleted(true);

        adminRepository.save(existingAdmin);
    }
}


     public List<AdminDTO> get() {
        List<Admin> list = adminRepository.findAll();
        List<AdminDTO> list2 = new ArrayList<>();
        for(Admin a : list){
            list2.add(adminMapper.toDTO(a));
        }
        return list2;
    }

}