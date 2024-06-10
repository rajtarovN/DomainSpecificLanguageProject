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
    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    public PersonService(
    PersonMapper personMapper,
    PersonRepository personRepository
            ,BasketRepository basketRepository
            ,BasketMapper basketMapper
            ,BillRepository billRepository
            ,BillMapper billMapper
) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.basketRepository = basketRepository;

        this.basketMapper = basketMapper;
        this.billRepository = billRepository;

        this.billMapper = billMapper;
    }
//-------------------------------------------------------------
  @Transactional
public PersonDTO save(PersonDTO persondto ) {

    Person person = personMapper.toModel(persondto);

                                    if(persondto.getBasket()!=null) {
                                        Basket basket = basketRepository.getById(persondto.getBasket().getId());
                                        person.setBasket(basket);
                                            basket.setPerson(person);
                                    }else{
                                        Basket basket = new Basket();
                                        person.setBasket(basket);
                                        basket.setPerson(person);
                                    }
                List<Bill> bills = new ArrayList<>();
                for (Long d : persondto.getBillIds()) {
                    Bill bill = billRepository.getById(d);
                    bills.add(bill);

                                        bill.setPerson(person);
                }
                person.setBill(bills);
    Person s = personRepository.save(person);
    return personMapper.toDTO(s);
}
//todo treba quantity smanjiti
//-------------------------------------------------------------

    public PersonDTO update(long id,PersonDTO persondto) {
    Optional<Person> person = personRepository.findById(id);
    if (person.isPresent()){
            person.get().setName(persondto.getName());
            person.get().setLastName(persondto.getLastName());
            person.get().setUsername(persondto.getUsername());



       //ovde
                    if(persondto.getBasket()!=null) {

                    Basket basket =basketRepository.getById(persondto.getBasket().getId());
                    person.get().setBasket(basket);
                    basket.setPerson(person.get());

}

        List<Bill> bills = new ArrayList<>();
        for (Long d : persondto.getBillIds()) {
            Bill bill = billRepository.getById(d);
            bills.add(bill);


                bill.setPerson(person.get());


        }

        person.get().setBill(bills);
            Person s = personRepository.save(person.get());
            return personMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Person> partialUpdate(Person person) {

    return personRepository
        .findById(person.getId())
        .map(existingPerson -> {

            if (person.getName() != null) {
                existingPerson.setName(person.getName());
            }
            if (person.getLastName() != null) {
                existingPerson.setLastName(person.getLastName());
            }
            if (person.getUsername() != null) {
                existingPerson.setUsername(person.getUsername());
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
        if (existingPerson.getBasket() != null){
            existingPerson.getBasket().setDeleted(true);
        }
        if (existingPerson.getBill() != null){
            for (Bill p: existingPerson.getBill()){
                p.setDeleted(true);
            }
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