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
public class SzagorService  {

    private final SzagorMapper szagorMapper;
    private final SzagorRepository szagorRepository;
    private final NovvRepository novvRepository;
    private final NovvMapper novvMapper;

    public SzagorService(
    SzagorMapper szagorMapper,
    SzagorRepository szagorRepository
            ,NovvRepository novvRepository
            ,NovvMapper novvMapper
) {

        this.szagorMapper = szagorMapper;
        this.szagorRepository = szagorRepository;
        this.novvRepository = novvRepository;

        this.novvMapper = novvMapper;

    }
  @Transactional
public SzagorDTO save( SzagorDTO szagordto){

        Szagor szagor = szagorMapper.toModel(szagordto);
                                    if(szagordto.getNovv()!=null) {
                                        Novv novv = novvRepository.getById(szagordto.getNovv().getId());
                                        szagor.setNovv(novv);
                                            novv.setSzagor(szagor);
                                    }
    Szagor s = szagorRepository.save(szagor);
    return szagorMapper.toDTO(s);
}

    public SzagorDTO update(long id,SzagorDTO szagordto) {
    Optional<Szagor> szagor = szagorRepository.findById(id);
    if (szagor.isPresent()){
            szagor.get().setName(szagordto.getName());



       //ovde
                    if(szagordto.getNovv()!=null) {

                    Novv novv =novvRepository.getById(szagordto.getNovv().getId());
                    szagor.get().setNovv(novv);
                    novv.setSzagor(szagor.get());

}

            Szagor s = szagorRepository.save(szagor.get());
            return szagorMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Szagor> partialUpdate(Szagor szagor) {

    return szagorRepository
        .findById(szagor.getId())
        .map(existingSzagor -> {

            if (szagor.getName() != null) {
                existingSzagor.setName(szagor.getName());
            }

            return existingSzagor;
        })
        .map(szagorRepository::save);
}

@Transactional(readOnly = true)
public List<SzagorDTO> findAll() {
    List<Szagor> szagors = szagorRepository.findAll();
    List<SzagorDTO> dtos = new ArrayList<>();
    for (Szagor s : szagors){
        SzagorDTO dto = szagorMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public SzagorDTO findOne(Long id) throws NotFoundException {
    Optional<Szagor> maybeSzagor =  szagorRepository.findById(id);
    if (maybeSzagor.isPresent()) {
        Szagor szagor = maybeSzagor.get();
        return szagorMapper.toDTO(szagor);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Szagor> maybeSzagor = szagorRepository.findById(id);

    if (maybeSzagor.isPresent()) {
        Szagor existingSzagor = maybeSzagor.get();
        existingSzagor.setDeleted(true);
        if (existingSzagor.getNovv() != null){
            existingSzagor.getNovv().setDeleted(true);
        }

        szagorRepository.save(existingSzagor);
    }
}


     public List<SzagorDTO> get() {
        List<Szagor> list = szagorRepository.findAll();
        List<SzagorDTO> list2 = new ArrayList<>();
        for(Szagor a : list){
            list2.add(szagorMapper.toDTO(a));
        }
        return list2;
    }

}