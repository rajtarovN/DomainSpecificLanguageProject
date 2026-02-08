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
import uns.ac.rs.mbrs.utils.CallPyController;
import uns.ac.rs.mbrs.utils.DynamicCodeExecution;
import java.util.Date;


@Service
@Transactional
public class ActionService  {

    private final ActionMapper actionMapper;
    private final ActionRepository actionRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
             private final CallPyController callPyController;
             private final DynamicCodeExecution dynamicCodeExecution;

    public ActionService(
    ActionMapper actionMapper,
    ActionRepository actionRepository
            ,ItemRepository itemRepository
            ,ItemMapper itemMapper
            , CallPyController callPyController,
    DynamicCodeExecution dynamicCodeExecution
) {

        this.actionMapper = actionMapper;
        this.actionRepository = actionRepository;
        this.itemRepository = itemRepository;

        this.itemMapper = itemMapper;

        this.callPyController = callPyController;
        this.dynamicCodeExecution = dynamicCodeExecution;
    }
  @Transactional
public ActionDTO save( ActionDTO actiondto) {

            Action action = actionMapper.toModel(actiondto);
                List<Item> items = new ArrayList<>();
                for (Long d : actiondto.getItemIds()) {
                    Item item = itemRepository.getById(d);
                    items.add(item);

                                        item.getAction().add(action);
                }
                action.setItem(items);
        String transformedCode = callPyController.callPythonPost(actiondto.getOriginalCode());
        String part =transformedCode.split("model\":\"")[1].split("}")[0];

        action.setTransformedCode(part.substring(0, part.length() - 1));
        action.setOriginalCode(actiondto.getOriginalCode());

    Action s = actionRepository.save(action);
    return actionMapper.toDTO(s);
}
public String generateCode(String originalCode, long id){
    String code= "package uns.ac.rs.mbrs.gen;\nimport uns.ac.rs.mbrs.model.*;\n" +
            "\n" +
            "public class GeneratedCode"+id+" {\n" +
            "    public  void execute(Customer customer, Bill bill){\n" +originalCode +
            "}\n" +
            "\n" +
            "}";

return code;
}

public void doActionMake(Customer customer, Bill current_bill ) throws Exception {
        List<Action> actions = actionRepository.findValidActions(new Date());
        for (Action a : actions){
            dynamicCodeExecution.execute(generateCode(a.getTransformedCode(), a.getId()), a.getId(), customer, current_bill);
        }

}


    public ActionDTO update(long id,ActionDTO actiondto) {
    Optional<Action> action = actionRepository.findById(id);
    if (action.isPresent()){
            action.get().setName(actiondto.getName());



        List<Item> items = new ArrayList<>();
        for (Long d : actiondto.getItemIds()) {
            Item item = itemRepository.getById(d);
            items.add(item);

                 item.getAction().add(action.get());

        }

        action.get().setItem(items);
            Action s = actionRepository.save(action.get());
            return actionMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Action> partialUpdate(Action action) {

    return actionRepository
        .findById(action.getId())
        .map(existingAction -> {

            if (action.getName() != null) {
                existingAction.setName(action.getName());
            }

            return existingAction;
        })
        .map(actionRepository::save);
}

@Transactional(readOnly = true)
public List<ActionDTO> findAll() {
    List<Action> actions = actionRepository.findAll();
    List<ActionDTO> dtos = new ArrayList<>();
    for (Action s : actions){
        ActionDTO dto = actionMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public ActionDTO findOne(Long id) throws NotFoundException {
    Optional<Action> maybeAction =  actionRepository.findById(id);
    if (maybeAction.isPresent()) {
        Action action = maybeAction.get();
        return actionMapper.toDTO(action);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Action> maybeAction = actionRepository.findById(id);

    if (maybeAction.isPresent()) {
        Action existingAction = maybeAction.get();
        existingAction.setDeleted(true);
        if (existingAction.getItem() != null){
            for (Item p: existingAction.getItem()){
                p.setDeleted(true);
            }
        }

        actionRepository.save(existingAction);
    }
}


     public List<ActionDTO> get() {
        List<Action> list = actionRepository.findAll();
        List<ActionDTO> list2 = new ArrayList<>();
        for(Action a : list){
            list2.add(actionMapper.toDTO(a));
        }
        return list2;
    }

}