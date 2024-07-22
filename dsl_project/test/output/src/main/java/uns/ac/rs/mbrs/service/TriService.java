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
public class TriService  {

    private final TriMapper triMapper;
    private final TriRepository triRepository;
    private final JedanRepository jedanRepository;
    private final JedanMapper jedanMapper;
    private final DvaRepository dvaRepository;
    private final DvaMapper dvaMapper;

    public TriService(
    TriMapper triMapper,
    TriRepository triRepository
            ,JedanRepository jedanRepository
            ,JedanMapper jedanMapper
            ,DvaRepository dvaRepository
            ,DvaMapper dvaMapper
) {

        this.triMapper = triMapper;
        this.triRepository = triRepository;
        this.jedanRepository = jedanRepository;

        this.jedanMapper = jedanMapper;
        this.dvaRepository = dvaRepository;

        this.dvaMapper = dvaMapper;

    }
  @Transactional
public TriDTO save( TriDTO tridto){

        Tri tri = triMapper.toModel(tridto);
                                    if(tridto.getJedan()!=null) {
                                        Jedan jedan = jedanRepository.getById(tridto.getJedan().getId());
                                        tri.setJedan(jedan);
                                            jedan.getTri().add(tri);
                                    }
                List<Dva> dvas = new ArrayList<>();
                for (Long d : tridto.getDvaIds()) {
                    Dva dva = dvaRepository.getById(d);
                    dvas.add(dva);

                                        dva.getTri().add(tri);
                }
                tri.setDva(dvas);
    Tri s = triRepository.save(tri);
    return triMapper.toDTO(s);
}

    public TriDTO update(long id,TriDTO tridto) {
    Optional<Tri> tri = triRepository.findById(id);
    if (tri.isPresent()){
            tri.get().setSdssdfe(tridto.getSdssdfe());



       //ovde
                    if(tridto.getJedan()!=null) {

                    Jedan jedan =jedanRepository.getById(tridto.getJedan().getId());
                    tri.get().setJedan(jedan);
                    jedan.getTri().add(tri.get());
}

        List<Dva> dvas = new ArrayList<>();
        for (Long d : tridto.getDvaIds()) {
            Dva dva = dvaRepository.getById(d);
            dvas.add(dva);

                //todo pazi na liste
                 dva.getTri().add(tri.get());

        }

        tri.get().setDva(dvas);
            Tri s = triRepository.save(tri.get());
            return triMapper.toDTO(s);
        }
        return null;


       }

     public Optional<Tri> partialUpdate(Tri tri) {

    return triRepository
        .findById(tri.getId())
        .map(existingTri -> {

            if (tri.getSdssdfe() != null) {
                existingTri.setSdssdfe(tri.getSdssdfe());
            }

            return existingTri;
        })
        .map(triRepository::save);
}

@Transactional(readOnly = true)
public List<TriDTO> findAll() {
    List<Tri> tris = triRepository.findAll();
    List<TriDTO> dtos = new ArrayList<>();
    for (Tri s : tris){
        TriDTO dto = triMapper.toDTO(s);
        dtos.add(dto);
    }
    return dtos;
}

@Transactional(readOnly = true)
public TriDTO findOne(Long id) throws NotFoundException {
    Optional<Tri> maybeTri =  triRepository.findById(id);
    if (maybeTri.isPresent()) {
        Tri tri = maybeTri.get();
        return triMapper.toDTO(tri);
    }
    throw new NotFoundException("");
}

public void delete(Long id) {
    Optional<Tri> maybeTri = triRepository.findById(id);

    if (maybeTri.isPresent()) {
        Tri existingTri = maybeTri.get();
        existingTri.setDeleted(true);
        if (existingTri.getJedan() != null){
            existingTri.getJedan().setDeleted(true);
        }
        if (existingTri.getDva() != null){
            for (Dva p: existingTri.getDva()){
                p.setDeleted(true);
            }
        }

        triRepository.save(existingTri);
    }
}


     public List<TriDTO> get() {
        List<Tri> list = triRepository.findAll();
        List<TriDTO> list2 = new ArrayList<>();
        for(Tri a : list){
            list2.add(triMapper.toDTO(a));
        }
        return list2;
    }

}