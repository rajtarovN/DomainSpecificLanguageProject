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

    public PersonService(
    PersonMapper personMapper,
    PersonRepository personRepository
) {

        this.personMapper = personMapper;
        this.personRepository = personRepository;

    }
  @Transactional
public PersonDTO save( PersonDTO persondto){

        Person person = personMapper.toModel(persondto);
    Person s = personRepository.save(person);
    return personMapper.toDTO(s);
}

    public PersonDTO update(long id,PersonDTO persondto) {
    Optional<Person> person = personRepository.findById(id);
    if (person.isPresent()){
            person.get().setAge(persondto.getAge());



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