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
public class SellerService  {

    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;

    public SellerService(
    SellerMapper sellerMapper,
    SellerRepository sellerRepository
) {

        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;

    }
  @Transactional
public SellerDTO save( SellerDTO sellerdto){

        Seller seller = sellerMapper.toModel(sellerdto);
    Seller s = sellerRepository.save(seller);
    return sellerMapper.toDTO(s);
}

    public SellerDTO update(long id,SellerDTO sellerdto) {
    Optional<Seller> seller = sellerRepository.findById(id);
    if (seller.isPresent()){



            Seller s = sellerRepository.save(seller.get());
            return sellerMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Seller> partialUpdate(Seller seller) {

    return sellerRepository
        .findById(seller.getId())
        .map(existingSeller -> {


            return existingSeller;
        })
        .map(sellerRepository::save);
}

@Transactional(readOnly = true)
public List<SellerDTO> findAll() {
    List<Seller> sellers = sellerRepository.findAll();
    List<SellerDTO> dtos = new ArrayList<>();
    for (Seller s : sellers){
        SellerDTO dto = sellerMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public SellerDTO findOne(Long id) throws NotFoundException {
    Optional<Seller> maybeSeller =  sellerRepository.findById(id);
    if (maybeSeller.isPresent()) {
        Seller seller = maybeSeller.get();
        return sellerMapper.toDTO(seller);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Seller> maybeSeller = sellerRepository.findById(id);

    if (maybeSeller.isPresent()) {
        Seller existingSeller = maybeSeller.get();
        existingSeller.setDeleted(true);

        sellerRepository.save(existingSeller);
    }
}


     public List<SellerDTO> get() {
        List<Seller> list = sellerRepository.findAll();
        List<SellerDTO> list2 = new ArrayList<>();
        for(Seller a : list){
            list2.add(sellerMapper.toDTO(a));
        }
        return list2;
    }

}