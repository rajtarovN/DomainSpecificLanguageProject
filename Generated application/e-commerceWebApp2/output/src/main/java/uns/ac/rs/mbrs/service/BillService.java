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
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ItemWithPriceRepository itemwithpriceRepository;
    private final ActionService  actionService;
            private final BasketRepository basketRepository;

    public BillService(
    BillMapper billMapper,
    BillRepository billRepository
            ,AddressRepository addressRepository
            ,AddressMapper addressMapper
            ,CustomerRepository customerRepository
            ,CustomerMapper customerMapper
             ,ItemWithPriceRepository itemwithpriceRepository
            , ActionService  actionService
           ,BasketRepository basketRepository
) {

        this.billMapper = billMapper;
        this.billRepository = billRepository;
        this.addressRepository = addressRepository;

        this.addressMapper = addressMapper;
        this.customerRepository = customerRepository;

        this.customerMapper = customerMapper;
             this.itemwithpriceRepository = itemwithpriceRepository;
            this.actionService = actionService;

           this.basketRepository = basketRepository;
    }
  @Transactional
public BillDTO save( BasketDTO basketdto )  throws Exception{

            Bill bill = new Bill();
                                    if( basketdto.getCustomer()!=null) {
                                        Customer customer = customerRepository.getById( basketdto.getCustomer().getId());
                                        bill.setCustomer(customer);
                                            customer.getBill().add(bill);
                                    }


                                    List <ItemWithPrice> itemWithPrice = new ArrayList<>();
                                    double price = 0;
                                    for(ItemDTO o : basketdto.getItem()){
                                        itemWithPrice.add(itemwithpriceRepository.findByItemIdAndIsCurrent(o.getId()));
                                        price += itemWithPrice.get(itemWithPrice.size()-1).getCurrentPrice();
                                    }
                                    bill.setTotalPrice(price);
                                    bill.setItemWithPrice(itemWithPrice);
                                     Customer customer = customerRepository.findByUsername(basketdto.getCustomer().getUsername());

                                    actionService.doActionMake(customer, bill);
    Bill s = billRepository.save(bill);
    return billMapper.toDTO(s);
}

    public BillDTO update(long id,BillDTO billdto) {
    Optional<Bill> bill = billRepository.findById(id);
    if (bill.isPresent()){
            bill.get().setCashier(billdto.getCashier());




                    if(billdto.getAddress()!=null) {

                     Address address =addressRepository.getById(billdto.getAddress().getId());

                    bill.get().setAddress(address);
                    address.setBill(bill.get());

}


                    if(billdto.getCustomer()!=null) {

                     Customer customer =customerRepository.getById(billdto.getCustomer().getId());

                    bill.get().setCustomer(customer);
                    customer.getBill().add(bill.get());
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

            if (bill.getCashier() != null) {
                existingBill.setCashier(bill.getCashier());
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
        if (existingBill.getAddress() != null){
            existingBill.getAddress().setDeleted(true);
        }
        if (existingBill.getCustomer() != null){
            existingBill.getCustomer().setDeleted(true);
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

         @Transactional
        public BillDTO saveWithId( long id) throws Exception {
        Bill bill = new Bill();
            Basket basket = basketRepository.getById(id);
            Optional<Customer> customer = customerRepository.findById(basket.getCustomer().getId());
            if (customer.isPresent()){
            bill.setCustomer(customer.get());
            customer.get().getBill().add(bill);
        List <ItemWithPrice> itemWithPrice = new ArrayList<>();
        double price = 0;
        int counter=0;
        for(Item o : customer.get().getBasket().getItem()){
            ItemWithPrice iwp = itemwithpriceRepository.findByItemIdAndIsCurrent(o.getId());
            itemWithPrice.add(iwp);
            if (iwp.getItem().getQuantity()-customer.get().getBasket().getQuantity().get(counter)<0){
                throw new Exception("Not enough items");
            }
            iwp.getItem().setQuantity(iwp.getItem().getQuantity()-customer.get().getBasket().getQuantity().get(counter));
            price += itemWithPrice.get(itemWithPrice.size()-1).getCurrentPrice();
            customer.get().getBasket().setItem(new ArrayList<>());
            customer.get().getBasket().setQuantity(new ArrayList<>());
            counter++;
            }
            bill.setItemWithPrice(itemWithPrice);
        bill.setTotalPrice(price);
        actionService.doActionMake(customer.get(), bill);
        Bill s = billRepository.save(bill);
        return billMapper.toDTO(s);}
        throw new Exception("There is no customer with that id");
    }
}