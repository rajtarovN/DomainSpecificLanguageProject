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
public class ItemService  {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;

    public ItemService(
    ItemMapper itemMapper,
    ItemRepository itemRepository
            ,ActionRepository actionRepository
            ,ActionMapper actionMapper
) {

        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
        this.actionRepository = actionRepository;

        this.actionMapper = actionMapper;

    }
  @Transactional
public ItemDTO save( ItemDTO itemdto) {

            Item item = itemMapper.toModel(itemdto);
    Item s = itemRepository.save(item);
    return itemMapper.toDTO(s);
}

    public ItemDTO update(long id,ItemDTO itemdto) {
    Optional<Item> item = itemRepository.findById(id);
    if (item.isPresent()){
            item.get().setName(itemdto.getName());
            item.get().setQuantity(itemdto.getQuantity());



            Item s = itemRepository.save(item.get());
            return itemMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Item> partialUpdate(Item item) {

    return itemRepository
        .findById(item.getId())
        .map(existingItem -> {

            if (item.getName() != null) {
                existingItem.setName(item.getName());
            }
            if (item.getQuantity() != 0) {
                existingItem.setQuantity(item.getQuantity());
            }

            return existingItem;
        })
        .map(itemRepository::save);
}

@Transactional(readOnly = true)
public List<ItemDTO> findAll() {
    List<Item> items = itemRepository.findAll();
    List<ItemDTO> dtos = new ArrayList<>();
    for (Item s : items){
        ItemDTO dto = itemMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public ItemDTO findOne(Long id) throws NotFoundException {
    Optional<Item> maybeItem =  itemRepository.findById(id);
    if (maybeItem.isPresent()) {
        Item item = maybeItem.get();
        return itemMapper.toDTO(item);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Item> maybeItem = itemRepository.findById(id);

    if (maybeItem.isPresent()) {
        Item existingItem = maybeItem.get();
        existingItem.setDeleted(true);
        if (existingItem.getAction() != null){
            for (Action p: existingItem.getAction()){
                p.setDeleted(true);
            }
        }

        itemRepository.save(existingItem);
    }
}


     public List<ItemDTO> get() {
        List<Item> list = itemRepository.findAll();
        List<ItemDTO> list2 = new ArrayList<>();
        for(Item a : list){
            list2.add(itemMapper.toDTO(a));
        }
        return list2;
    }


}