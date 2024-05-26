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
public class PersonService  {

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public PersonService(
    PersonMapper personMapper,
    PersonRepository personRepository
            ,AddressRepository addressRepository
            ,AddressMapper addressMapper
            ,ProductRepository productRepository
            ,ProductMapper productMapper
) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;

        this.addressMapper = addressMapper;
        this.productRepository = productRepository;

        this.productMapper = productMapper;
    }

    @Transactional
    public PersonDTO save(PersonDTO persondto) {

        Person person = personMapper.toModel(persondto);

       //ovde

                    if(persondto.getAddress()!=null) {
                    Address address =addressRepository.getById(persondto.getAddress().getId()); //tuuu
                    person.setAddress(address);
                    address.getProbannn().add(person);
}

       //ovde

                    if(persondto.getProduct()!=null) {
                    Product product =productRepository.getById(persondto.getProduct().getId()); //tuuu
                    person.setProduct(product);
                    product.getPerson().add(person);
}

        Person s = personRepository.save(person);

        return personMapper.toDTO(person);
    }

    public PersonDTO update(long id,PersonDTO persondto) {
    Optional<Person> person = personRepository.findById(id);
    if (person.isPresent()){
            person.get().setAge(persondto.getAge());



       //ovde
                    if(persondto.getAddress()!=null) {

                    Address address =addressRepository.getById(persondto.getAddress().getId());
                    person.get().setAddress(address);
                    address.getProbannn().add(person.get());
}

       //ovde
                    if(persondto.getProduct()!=null) {

                    Product product =productRepository.getById(persondto.getProduct().getId());
                    person.get().setProduct(product);
                    product.getPerson().add(person.get());
}

            Person s = personRepository.save(person.get());
            return personMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Person> partialUpdate(Person person) {

    return personRepository
        .findById(person.getId())
        .map(existingPerson -> {

            if (person.getAge() != 0) {
                existingPerson.setAge(person.getAge());
            }

            return existingPerson;
        })
        .map(personRepository::save);
}

@Transactional(readOnly = true)
public List<PersonDTO> findAll() {
    List<Person> persons = personRepository.findAll();
    List<PersonDTO> dtos = new ArrayList<>();
    for (Person s : persons){
        PersonDTO dto = personMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public PersonDTO findOne(Long id) throws NotFoundException {
    Optional<Person> maybePerson =  personRepository.findById(id);
    if (maybePerson.isPresent()) {
        Person person = maybePerson.get();
        return personMapper.toDTO(person);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Person> maybePerson = personRepository.findById(id);

    if (maybePerson.isPresent()) {
        Person existingPerson = maybePerson.get();
        existingPerson.setDeleted(true);
        if (existingPerson.getAddress() != null){
            existingPerson.getAddress().setDeleted(true);
        }
        if (existingPerson.getProduct() != null){
            existingPerson.getProduct().setDeleted(true);
        }

        personRepository.save(existingPerson);
    }
}


     public List<PersonDTO> get() {
        List<Person> list = personRepository.findAll();
        List<PersonDTO> list2 = new ArrayList<>();
        for(Person a : list){
            list2.add(personMapper.toDTO(a));
        }
        return list2;
    }

}