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
public class ItemWithPriceService  {

    private final ItemWithPriceMapper itemwithpriceMapper;
    private final ItemWithPriceRepository itemwithpriceRepository;
     private final ItemRepository itemRepository;

    public ItemWithPriceService(
    ItemWithPriceMapper itemwithpriceMapper,
    ItemWithPriceRepository itemwithpriceRepository
     ,ItemRepository itemRepository
) {

        this.itemwithpriceMapper = itemwithpriceMapper;
        this.itemwithpriceRepository = itemwithpriceRepository;
         this.itemRepository =itemRepository;

    }
  @Transactional
public ItemWithPriceDTO save( ItemWithPriceDTO itemwithpricedto) {

            ItemWithPrice itemWithPrice = itemwithpriceMapper.toModel(itemwithpricedto);
    ItemWithPrice old = itemwithpriceRepository.findByItemIdAndIsCurrent(itemwithpricedto.getItem().getId());
      if (old!=null){
          old.setIscurrent(false);
          itemwithpriceRepository.save(old);
      }
      Item item = itemRepository.getById(itemwithpricedto.getItem().getId());
      item.getItemWithPrice().add(itemWithPrice);
      itemWithPrice.setItem(item);
    ItemWithPrice s = itemwithpriceRepository.save(itemWithPrice);
    return itemwithpriceMapper.toDTO(s);
}

    public ItemWithPriceDTO update(long id,ItemWithPriceDTO itemwithpricedto) {
    Optional<ItemWithPrice> itemWithPrice = itemwithpriceRepository.findById(id);
    if (itemWithPrice.isPresent()){



            ItemWithPrice s = itemwithpriceRepository.save(itemWithPrice.get());
            return itemwithpriceMapper.toDTO(s);
        }
        return null;


       }

     public Optional<ItemWithPrice> partialUpdate(ItemWithPrice itemwithprice) {

    return itemwithpriceRepository
        .findById(itemwithprice.getId())
        .map(existingItemWithPrice -> {


            return existingItemWithPrice;
        })
        .map(itemwithpriceRepository::save);
}

@Transactional(readOnly = true)
public List<ItemWithPriceDTO> findAll() {
    List<ItemWithPrice> itemwithprices = itemwithpriceRepository.findAll();
    List<ItemWithPriceDTO> dtos = new ArrayList<>();
    for (ItemWithPrice s : itemwithprices){
        ItemWithPriceDTO dto = itemwithpriceMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public ItemWithPriceDTO findOne(Long id) throws NotFoundException {
    Optional<ItemWithPrice> maybeItemWithPrice =  itemwithpriceRepository.findById(id);
    if (maybeItemWithPrice.isPresent()) {
        ItemWithPrice itemwithprice = maybeItemWithPrice.get();
        return itemwithpriceMapper.toDTO(itemwithprice);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<ItemWithPrice> maybeItemWithPrice = itemwithpriceRepository.findById(id);

    if (maybeItemWithPrice.isPresent()) {
        ItemWithPrice existingItemWithPrice = maybeItemWithPrice.get();
        existingItemWithPrice.setDeleted(true);

        itemwithpriceRepository.save(existingItemWithPrice);
    }
}


     public List<ItemWithPriceDTO> get() {
        List<ItemWithPrice> list = itemwithpriceRepository.findAll();
        List<ItemWithPriceDTO> list2 = new ArrayList<>();
        for(ItemWithPrice a : list){
            list2.add(itemwithpriceMapper.toDTO(a));
        }
        return list2;
    }


}