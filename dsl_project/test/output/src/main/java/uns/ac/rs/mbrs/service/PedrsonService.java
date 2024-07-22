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
public class PedrsonService  {

    private final PedrsonMapper pedrsonMapper;
    private final PedrsonRepository pedrsonRepository;

    public PedrsonService(
    PedrsonMapper pedrsonMapper,
    PedrsonRepository pedrsonRepository
) {

        this.pedrsonMapper = pedrsonMapper;
        this.pedrsonRepository = pedrsonRepository;

    }
  @Transactional
public PedrsonDTO save( PedrsonDTO pedrsondto){

        Pedrson pedrson = pedrsonMapper.toModel(pedrsondto);
    Pedrson s = pedrsonRepository.save(pedrson);
    return pedrsonMapper.toDTO(s);
}

    public PedrsonDTO update(long id,PedrsonDTO pedrsondto) {
    Optional<Pedrson> pedrson = pedrsonRepository.findById(id);
    if (pedrson.isPresent()){
            pedrson.get().setAge(pedrsondto.getAge());



            Pedrson s = pedrsonRepository.save(pedrson.get());
            return pedrsonMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Pedrson> partialUpdate(Pedrson pedrson) {

    return pedrsonRepository
        .findById(pedrson.getId())
        .map(existingPedrson -> {

            if (pedrson.getAge() != 0) {
                existingPedrson.setAge(pedrson.getAge());
            }

            return existingPedrson;
        })
        .map(pedrsonRepository::save);
}

@Transactional(readOnly = true)
public List<PedrsonDTO> findAll() {
    List<Pedrson> pedrsons = pedrsonRepository.findAll();
    List<PedrsonDTO> dtos = new ArrayList<>();
    for (Pedrson s : pedrsons){
        PedrsonDTO dto = pedrsonMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public PedrsonDTO findOne(Long id) throws NotFoundException {
    Optional<Pedrson> maybePedrson =  pedrsonRepository.findById(id);
    if (maybePedrson.isPresent()) {
        Pedrson pedrson = maybePedrson.get();
        return pedrsonMapper.toDTO(pedrson);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Pedrson> maybePedrson = pedrsonRepository.findById(id);

    if (maybePedrson.isPresent()) {
        Pedrson existingPedrson = maybePedrson.get();
        existingPedrson.setDeleted(true);

        pedrsonRepository.save(existingPedrson);
    }
}


     public List<PedrsonDTO> get() {
        List<Pedrson> list = pedrsonRepository.findAll();
        List<PedrsonDTO> list2 = new ArrayList<>();
        for(Pedrson a : list){
            list2.add(pedrsonMapper.toDTO(a));
        }
        return list2;
    }

}