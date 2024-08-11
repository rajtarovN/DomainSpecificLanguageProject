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
public class CustomerService  {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final BillRepository billRepository;
    private final BillMapper billMapper;
    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;

    public CustomerService(
    CustomerMapper customerMapper,
    CustomerRepository customerRepository
            ,BillRepository billRepository
            ,BillMapper billMapper
            ,BasketRepository basketRepository
            ,BasketMapper basketMapper
) {

        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.billRepository = billRepository;

        this.billMapper = billMapper;
        this.basketRepository = basketRepository;

        this.basketMapper = basketMapper;

    }
  @Transactional
public CustomerDTO save( CustomerDTO customerdto){

        Customer customer = customerMapper.toModel(customerdto);
                List<Bill> bills = new ArrayList<>();
                for (Long d : customerdto.getBillIds()) {
                    Bill bill = billRepository.getById(d);
                    bills.add(bill);

                                        bill.setCustomer(customer);
                }
                customer.setBill(bills);
    Customer s = customerRepository.save(customer);
    return customerMapper.toDTO(s);
}

    public CustomerDTO update(long id,CustomerDTO customerdto) {
    Optional<Customer> customer = customerRepository.findById(id);
    if (customer.isPresent()){



        List<Bill> bills = new ArrayList<>();
        for (Long d : customerdto.getBillIds()) {
            Bill bill = billRepository.getById(d);
            bills.add(bill);


                bill.setCustomer(customer.get());


        }

        customer.get().setBill(bills);

                    if(customerdto.getBasket()!=null) {

                     Basket basket =basketRepository.getById(customerdto.getBasket().getId());

                    customer.get().setBasket(basket);
                    basket.setCustomer(customer.get());

}

            Customer s = customerRepository.save(customer.get());
            return customerMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Customer> partialUpdate(Customer customer) {

    return customerRepository
        .findById(customer.getId())
        .map(existingCustomer -> {


            return existingCustomer;
        })
        .map(customerRepository::save);
}

@Transactional(readOnly = true)
public List<CustomerDTO> findAll() {
    List<Customer> customers = customerRepository.findAll();
    List<CustomerDTO> dtos = new ArrayList<>();
    for (Customer s : customers){
        CustomerDTO dto = customerMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public CustomerDTO findOne(Long id) throws NotFoundException {
    Optional<Customer> maybeCustomer =  customerRepository.findById(id);
    if (maybeCustomer.isPresent()) {
        Customer customer = maybeCustomer.get();
        return customerMapper.toDTO(customer);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Customer> maybeCustomer = customerRepository.findById(id);

    if (maybeCustomer.isPresent()) {
        Customer existingCustomer = maybeCustomer.get();
        existingCustomer.setDeleted(true);
        if (existingCustomer.getBill() != null){
            for (Bill p: existingCustomer.getBill()){
                p.setDeleted(true);
            }
        }
        if (existingCustomer.getBasket() != null){
            existingCustomer.getBasket().setDeleted(true);
        }

        customerRepository.save(existingCustomer);
    }
}


     public List<CustomerDTO> get() {
        List<Customer> list = customerRepository.findAll();
        List<CustomerDTO> list2 = new ArrayList<>();
        for(Customer a : list){
            list2.add(customerMapper.toDTO(a));
        }
        return list2;
    }


}