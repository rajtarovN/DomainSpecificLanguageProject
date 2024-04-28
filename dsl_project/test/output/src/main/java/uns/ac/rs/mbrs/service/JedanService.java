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
public class JedanService  {

    private final JedanMapper jedanMapper;
    private final JedanRepository jedanRepository;
    private final TriRepository triRepository;
    private final TriMapper triMapper;
    private final DvaRepository dvaRepository;
    private final DvaMapper dvaMapper;

    public JedanService(
    JedanMapper jedanMapper,
    JedanRepository jedanRepository
            ,TriRepository triRepository
            ,TriMapper triMapper
            ,DvaRepository dvaRepository
            ,DvaMapper dvaMapper
) {
        this.jedanMapper = jedanMapper;
        this.jedanRepository = jedanRepository;
        this.triRepository = triRepository;

        this.triMapper = triMapper;
        this.dvaRepository = dvaRepository;

        this.dvaMapper = dvaMapper;
    }
    
    public JedanDTO save(Jedan jedan) {
        Jedan s = jedanRepository.save(jedan);
       
        return jedanMapper.toDTO(s);
    }

    public JedanDTO update(Jedan jedan) {
    
    
    
        Jedan s = jedanRepository.save(jedan);
        return jedanMapper.toDTO(s);
       }

     public Optional<Jedan> partialUpdate(Jedan jedan) {

    return jedanRepository
        .findById(jedan.getId())
        .map(existingJedan -> {

            if (jedan.getStreet() != null) {
                existingJedan.setStreet(jedan.getStreet());
            }

            return existingJedan;
        })
        .map(jedanRepository::save);
}

@Transactional(readOnly = true)
public List<JedanDTO> findAll() {
    List<Jedan> jedans = jedanRepository.findAll();
    List<JedanDTO> dtos = new ArrayList<>();
    for (Jedan s : jedans){
        JedanDTO dto = jedanMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public JedanDTO findOne(Long id) throws NotFoundException {
    Optional<Jedan> maybeJedan =  jedanRepository.findById(id);
    if (maybeJedan.isPresent()) {
        Jedan jedan = maybeJedan.get();
        return jedanMapper.toDTO(jedan);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Jedan> maybeJedan = jedanRepository.findById(id);

    if (maybeJedan.isPresent()) {
        Jedan existingJedan = maybeJedan.get();
        existingJedan.setDeleted(true);
        if (existingJedan.getTri() != null){
            for (Tri p: existingJedan.getTri()){
                p.setDeleted(true);
            }
        }
        if (existingJedan.getValjda() != null){
            existingJedan.getValjda().setDeleted(true);
        }

        jedanRepository.save(existingJedan);
    }
}

}