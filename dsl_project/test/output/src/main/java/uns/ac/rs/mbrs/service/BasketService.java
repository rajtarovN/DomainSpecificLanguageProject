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
public class BasketService  {

    private final BasketMapper basketMapper;
    private final BasketRepository basketRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public BasketService(
    BasketMapper basketMapper,
    BasketRepository basketRepository
            ,PersonRepository personRepository
            ,PersonMapper personMapper
) {
        this.basketMapper = basketMapper;
        this.basketRepository = basketRepository;
        this.personRepository = personRepository;

        this.personMapper = personMapper;
    }
//-------------------------------------------------------------
  @Transactional
public BasketDTO save(BasketDTO basketdto ) {

    Basket basket = basketMapper.toModel(basketdto);

                                    if(basketdto.getPerson()!=null) {
                                        Person person = personRepository.getById(basketdto.getPerson().getId());
                                        basket.setPerson(person);
                                            person.setBasket(basket);
                                    }
    Basket s = basketRepository.save(basket);
    return basketMapper.toDTO(s);
}
//todo treba quantity smanjiti
//-------------------------------------------------------------

    public BasketDTO update(long id,BasketDTO basketdto) {
    Optional<Basket> basket = basketRepository.findById(id);
    if (basket.isPresent()){
            basket.get().setFormular(basketdto.getFormular());



       //ovde
                    if(basketdto.getPerson()!=null) {

                    Person person =personRepository.getById(basketdto.getPerson().getId());
                    basket.get().setPerson(person);
                    person.setBasket(basket.get());

}

            Basket s = basketRepository.save(basket.get());
            return basketMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Basket> partialUpdate(Basket basket) {

    return basketRepository
        .findById(basket.getId())
        .map(existingBasket -> {

            if (basket.getFormular() != null) {
                existingBasket.setFormular(basket.getFormular());
            }

            return existingBasket;
        })
        .map(basketRepository::save);
}

@Transactional(readOnly = true)
public List<BasketDTO> findAll() {
    List<Basket> baskets = basketRepository.findAll();
    List<BasketDTO> dtos = new ArrayList<>();
    for (Basket s : baskets){
        BasketDTO dto = basketMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public BasketDTO findOne(Long id) throws NotFoundException {
    Optional<Basket> maybeBasket =  basketRepository.findById(id);
    if (maybeBasket.isPresent()) {
        Basket basket = maybeBasket.get();
        return basketMapper.toDTO(basket);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Basket> maybeBasket = basketRepository.findById(id);

    if (maybeBasket.isPresent()) {
        Basket existingBasket = maybeBasket.get();
        existingBasket.setDeleted(true);
        if (existingBasket.getPerson() != null){
            existingBasket.getPerson().setDeleted(true);
        }

        basketRepository.save(existingBasket);
    }
}


     public List<BasketDTO> get() {
        List<Basket> list = basketRepository.findAll();
        List<BasketDTO> list2 = new ArrayList<>();
        for(Basket a : list){
            list2.add(basketMapper.toDTO(a));
        }
        return list2;
    }

}