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
public class BillService  {

    private final BillMapper billMapper;
    private final BillRepository billRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ItemWithPriceRepository itemwithpriceRepository;
    private final ActionService  actionService;

    public BillService(
    BillMapper billMapper,
    BillRepository billRepository
            ,PersonRepository personRepository
            ,PersonMapper personMapper
             ,ItemWithPriceRepository itemwithpriceRepository
            , ActionService  actionService
) {
        this.billMapper = billMapper;
        this.billRepository = billRepository;
        this.personRepository = personRepository;

        this.personMapper = personMapper;
             this.itemwithpriceRepository = itemwithpriceRepository;
            this.actionService = actionService;
    }
//-------------------------------------------------------------
  @Transactional
public BillDTO save( BasketDTO basketdto ) throws Exception {

    Bill bill = new Bill();

                                    if(basketdto.getPerson()!=null) {
                                        Person person = personRepository.findByUsername(basketdto.getPerson().getUsername());
                                        bill.setPerson(person);
                                            person.getBill().add(bill);
                                    }

                                    List <ItemWithPrice> itemWithPrice = new ArrayList<>();
                                    double price = 0;
                                    for(ItemDTO o : basketdto.getItem()){
                                        ItemWithPrice iwp = itemwithpriceRepository.findByItemIdAndIsCurrent(o.getId());
                                        itemWithPrice.add(iwp);
                                        if (iwp.getItem().getQuantity()-o.getQuantity()<0){
                                            throw new Exception("Not enough items");
                                        }
                                        iwp.getItem().setQuantity(iwp.getItem().getQuantity()-o.getQuantity());
                                        price += itemWithPrice.get(itemWithPrice.size()-1).getCurrentPrice();

                                    }
                                    bill.setTotalPrice(price);
                                    bill.setItemWithPrice(itemWithPrice);
                                    Person person = personRepository.findByUsername(basketdto.getPerson().getUsername());

                                    actionService.doActionMake(person, bill);
    Bill s = billRepository.save(bill);
    return billMapper.toDTO(s);
}
//todo treba quantity smanjiti
//-------------------------------------------------------------

    public BillDTO update(long id,BillDTO billdto) {
    Optional<Bill> bill = billRepository.findById(id);
    if (bill.isPresent()){
            bill.get().setNeki_tekst(billdto.getNeki_tekst());



       //ovde
                    if(billdto.getPerson()!=null) {

                    Person person =personRepository.getById(billdto.getPerson().getId());
                    bill.get().setPerson(person);
                    person.getBill().add(bill.get());
}

            Bill s = billRepository.save(bill.get());
            return billMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Bill> partialUpdate(Bill bill) {

    return billRepository
        .findById(bill.getId())
        .map(existingBill -> {

            if (bill.getNeki_tekst() != null) {
                existingBill.setNeki_tekst(bill.getNeki_tekst());
            }

            return existingBill;
        })
        .map(billRepository::save);
}

@Transactional(readOnly = true)
public List<BillDTO> findAll() {
    List<Bill> bills = billRepository.findAll();
    List<BillDTO> dtos = new ArrayList<>();
    for (Bill s : bills){
        BillDTO dto = billMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public BillDTO findOne(Long id) throws NotFoundException {
    Optional<Bill> maybeBill =  billRepository.findById(id);
    if (maybeBill.isPresent()) {
        Bill bill = maybeBill.get();
        return billMapper.toDTO(bill);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Bill> maybeBill = billRepository.findById(id);

    if (maybeBill.isPresent()) {
        Bill existingBill = maybeBill.get();
        existingBill.setDeleted(true);
        if (existingBill.getPerson() != null){
            existingBill.getPerson().setDeleted(true);
        }

        billRepository.save(existingBill);
    }
}


     public List<BillDTO> get() {
        List<Bill> list = billRepository.findAll();
        List<BillDTO> list2 = new ArrayList<>();
        for(Bill a : list){
            list2.add(billMapper.toDTO(a));
        }
        return list2;
    }

}