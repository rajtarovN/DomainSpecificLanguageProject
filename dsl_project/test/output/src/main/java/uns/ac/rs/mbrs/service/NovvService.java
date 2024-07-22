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
public class NovvService  {

    private final NovvMapper novvMapper;
    private final NovvRepository novvRepository;
    private final SzagorRepository szagorRepository;
    private final SzagorMapper szagorMapper;

    public NovvService(
    NovvMapper novvMapper,
    NovvRepository novvRepository
            ,SzagorRepository szagorRepository
            ,SzagorMapper szagorMapper
) {

        this.novvMapper = novvMapper;
        this.novvRepository = novvRepository;
        this.szagorRepository = szagorRepository;

        this.szagorMapper = szagorMapper;

    }
  @Transactional
public NovvDTO save( NovvDTO novvdto){

        Novv novv = novvMapper.toModel(novvdto);
                                    if(novvdto.getSzagor()!=null) {
                                        Szagor szagor = szagorRepository.getById(novvdto.getSzagor().getId());
                                        novv.setSzagor(szagor);
                                            szagor.setNovv(novv);
                                    }
    Novv s = novvRepository.save(novv);
    return novvMapper.toDTO(s);
}

    public NovvDTO update(long id,NovvDTO novvdto) {
    Optional<Novv> novv = novvRepository.findById(id);
    if (novv.isPresent()){
            novv.get().setName(novvdto.getName());



       //ovde
                    if(novvdto.getSzagor()!=null) {

                    Szagor szagor =szagorRepository.getById(novvdto.getSzagor().getId());
                    novv.get().setSzagor(szagor);
                    szagor.setNovv(novv.get());

}

            Novv s = novvRepository.save(novv.get());
            return novvMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Novv> partialUpdate(Novv novv) {

    return novvRepository
        .findById(novv.getId())
        .map(existingNovv -> {

            if (novv.getName() != null) {
                existingNovv.setName(novv.getName());
            }

            return existingNovv;
        })
        .map(novvRepository::save);
}

@Transactional(readOnly = true)
public List<NovvDTO> findAll() {
    List<Novv> novvs = novvRepository.findAll();
    List<NovvDTO> dtos = new ArrayList<>();
    for (Novv s : novvs){
        NovvDTO dto = novvMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public NovvDTO findOne(Long id) throws NotFoundException {
    Optional<Novv> maybeNovv =  novvRepository.findById(id);
    if (maybeNovv.isPresent()) {
        Novv novv = maybeNovv.get();
        return novvMapper.toDTO(novv);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Novv> maybeNovv = novvRepository.findById(id);

    if (maybeNovv.isPresent()) {
        Novv existingNovv = maybeNovv.get();
        existingNovv.setDeleted(true);
        if (existingNovv.getSzagor() != null){
            existingNovv.getSzagor().setDeleted(true);
        }

        novvRepository.save(existingNovv);
    }
}


     public List<NovvDTO> get() {
        List<Novv> list = novvRepository.findAll();
        List<NovvDTO> list2 = new ArrayList<>();
        for(Novv a : list){
            list2.add(novvMapper.toDTO(a));
        }
        return list2;
    }

}