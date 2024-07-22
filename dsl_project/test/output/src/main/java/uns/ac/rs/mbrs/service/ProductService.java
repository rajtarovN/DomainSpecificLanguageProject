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
public class ProductService  {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public ProductService(
    ProductMapper productMapper,
    ProductRepository productRepository
            ,PersonRepository personRepository
            ,PersonMapper personMapper
) {

        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.personRepository = personRepository;

        this.personMapper = personMapper;

    }
  @Transactional
public ProductDTO save( ProductDTO productdto){

        Product product = productMapper.toModel(productdto);
                List<Person> persons = new ArrayList<>();
                for (Long d : productdto.getPersonIds()) {
                    Person person = personRepository.getById(d);
                    persons.add(person);

                                        person.setProduct(product);
                }
                product.setPerson(persons);
    Product s = productRepository.save(product);
    return productMapper.toDTO(s);
}

    public ProductDTO update(long id,ProductDTO productdto) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()){
            product.get().setName(productdto.getName());
            product.get().setPrice(productdto.getPrice());



        List<Person> persons = new ArrayList<>();
        for (Long d : productdto.getPersonIds()) {
            Person person = personRepository.getById(d);
            persons.add(person);


                person.setProduct(product.get());


        }

        product.get().setPerson(persons);
            Product s = productRepository.save(product.get());
            return productMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Product> partialUpdate(Product product) {

    return productRepository
        .findById(product.getId())
        .map(existingProduct -> {

            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }
            if (product.getPrice() != 0) {
                existingProduct.setPrice(product.getPrice());
            }

            return existingProduct;
        })
        .map(productRepository::save);
}

@Transactional(readOnly = true)
public List<ProductDTO> findAll() {
    List<Product> products = productRepository.findAll();
    List<ProductDTO> dtos = new ArrayList<>();
    for (Product s : products){
        ProductDTO dto = productMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public ProductDTO findOne(Long id) throws NotFoundException {
    Optional<Product> maybeProduct =  productRepository.findById(id);
    if (maybeProduct.isPresent()) {
        Product product = maybeProduct.get();
        return productMapper.toDTO(product);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Product> maybeProduct = productRepository.findById(id);

    if (maybeProduct.isPresent()) {
        Product existingProduct = maybeProduct.get();
        existingProduct.setDeleted(true);
        if (existingProduct.getPerson() != null){
            for (Person p: existingProduct.getPerson()){
                p.setDeleted(true);
            }
        }

        productRepository.save(existingProduct);
    }
}


     public List<ProductDTO> get() {
        List<Product> list = productRepository.findAll();
        List<ProductDTO> list2 = new ArrayList<>();
        for(Product a : list){
            list2.add(productMapper.toDTO(a));
        }
        return list2;
    }

}