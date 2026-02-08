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
public class AddressService  {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    public AddressService(
    AddressMapper addressMapper,
    AddressRepository addressRepository
            ,BillRepository billRepository
            ,BillMapper billMapper
) {

        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.billRepository = billRepository;

        this.billMapper = billMapper;

    }
  @Transactional
public AddressDTO save( AddressDTO addressdto){

        Address address = addressMapper.toModel(addressdto);
    Address s = addressRepository.save(address);
    return addressMapper.toDTO(s);
}

    public AddressDTO update(long id,AddressDTO addressdto) {
    Optional<Address> address = addressRepository.findById(id);
    if (address.isPresent()){
            address.get().setStreet(addressdto.getStreet());
            address.get().setNumber(addressdto.getNumber());
            address.get().setZip(addressdto.getZip());




                    if(addressdto.getBill()!=null) {

                     Bill bill =billRepository.getById(addressdto.getBill().getId());

                    address.get().setBill(bill);
                    bill.setAddress(address.get());

}

            Address s = addressRepository.save(address.get());
            return addressMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Address> partialUpdate(Address address) {

    return addressRepository
        .findById(address.getId())
        .map(existingAddress -> {

            if (address.getStreet() != null) {
                existingAddress.setStreet(address.getStreet());
            }
            if (address.getNumber() != null) {
                existingAddress.setNumber(address.getNumber());
            }
            if (address.getZip() != null) {
                existingAddress.setZip(address.getZip());
            }

            return existingAddress;
        })
        .map(addressRepository::save);
}

@Transactional(readOnly = true)
public List<AddressDTO> findAll() {
    List<Address> addresss = addressRepository.findAll();
    List<AddressDTO> dtos = new ArrayList<>();
    for (Address s : addresss){
        AddressDTO dto = addressMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public AddressDTO findOne(Long id) throws NotFoundException {
    Optional<Address> maybeAddress =  addressRepository.findById(id);
    if (maybeAddress.isPresent()) {
        Address address = maybeAddress.get();
        return addressMapper.toDTO(address);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Address> maybeAddress = addressRepository.findById(id);

    if (maybeAddress.isPresent()) {
        Address existingAddress = maybeAddress.get();
        existingAddress.setDeleted(true);
        if (existingAddress.getBill() != null){
            existingAddress.getBill().setDeleted(true);
        }

        addressRepository.save(existingAddress);
    }
}


     public List<AddressDTO> get() {
        List<Address> list = addressRepository.findAll();
        List<AddressDTO> list2 = new ArrayList<>();
        for(Address a : list){
            list2.add(addressMapper.toDTO(a));
        }
        return list2;
    }

}