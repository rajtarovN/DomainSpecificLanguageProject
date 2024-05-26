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
public class DvaService  {

    private final DvaMapper dvaMapper;
    private final DvaRepository dvaRepository;
    private final JedanRepository jedanRepository;
    private final JedanMapper jedanMapper;
    private final TriRepository triRepository;
    private final TriMapper triMapper;

    public DvaService(
    DvaMapper dvaMapper,
    DvaRepository dvaRepository
            ,JedanRepository jedanRepository
            ,JedanMapper jedanMapper
            ,TriRepository triRepository
            ,TriMapper triMapper
) {
        this.dvaMapper = dvaMapper;
        this.dvaRepository = dvaRepository;
        this.jedanRepository = jedanRepository;

        this.jedanMapper = jedanMapper;
        this.triRepository = triRepository;

        this.triMapper = triMapper;
    }

    @Transactional
    public DvaDTO save(DvaDTO dvadto) {

        Dva dva = dvaMapper.toModel(dvadto);

        List<Jedan> jedans = new ArrayList<>();
        for (Long d : dvadto.getJedanIds()) {
            Jedan jedan = jedanRepository.getById(d);
            jedans.add(jedan);


                jedan.setValjda(dva);


        }

        dva.setJedan(jedans);
        Dva s = dvaRepository.save(dva);

        return dvaMapper.toDTO(dva);
    }

    public DvaDTO update(long id,DvaDTO dvadto) {
    Optional<Dva> dva = dvaRepository.findById(id);
    if (dva.isPresent()){
            dva.get().setSds(dvadto.getSds());



        List<Jedan> jedans = new ArrayList<>();
        for (Long d : dvadto.getJedanIds()) {
            Jedan jedan = jedanRepository.getById(d);
            jedans.add(jedan);


                jedan.setValjda(dva.get());


        }

        dva.get().setJedan(jedans);
            Dva s = dvaRepository.save(dva.get());
            return dvaMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Dva> partialUpdate(Dva dva) {

    return dvaRepository
        .findById(dva.getId())
        .map(existingDva -> {

            if (dva.getSds() != null) {
                existingDva.setSds(dva.getSds());
            }

            return existingDva;
        })
        .map(dvaRepository::save);
}

@Transactional(readOnly = true)
public List<DvaDTO> findAll() {
    List<Dva> dvas = dvaRepository.findAll();
    List<DvaDTO> dtos = new ArrayList<>();
    for (Dva s : dvas){
        DvaDTO dto = dvaMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public DvaDTO findOne(Long id) throws NotFoundException {
    Optional<Dva> maybeDva =  dvaRepository.findById(id);
    if (maybeDva.isPresent()) {
        Dva dva = maybeDva.get();
        return dvaMapper.toDTO(dva);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Dva> maybeDva = dvaRepository.findById(id);

    if (maybeDva.isPresent()) {
        Dva existingDva = maybeDva.get();
        existingDva.setDeleted(true);
        if (existingDva.getJedan() != null){
            for (Jedan p: existingDva.getJedan()){
                p.setDeleted(true);
            }
        }
        if (existingDva.getTri() != null){
            for (Tri p: existingDva.getTri()){
                p.setDeleted(true);
            }
        }

        dvaRepository.save(existingDva);
    }
}


     public List<DvaDTO> get() {
        List<Dva> list = dvaRepository.findAll();
        List<DvaDTO> list2 = new ArrayList<>();
        for(Dva a : list){
            list2.add(dvaMapper.toDTO(a));
        }
        return list2;
    }

}