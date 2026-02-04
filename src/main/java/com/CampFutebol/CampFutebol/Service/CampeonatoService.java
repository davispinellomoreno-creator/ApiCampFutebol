package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camp;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryCampeonato;
import org.springframework.stereotype.Service;
import javax.naming.directory.Attribute;
import java.sql.Time;

@Service
public class CampeonatoService {

    private RepositoryCampeonato repositorycamp;

    public CampeonatoService(RepositoryCampeonato repositorycamp) {
        this.repositorycamp = repositorycamp;
    }

    public void salvarCamp(Camp camp){
        repositorycamp.saveAndFlush(camp);
    }
    public Camp buscarPorId(Long id){
       return repositorycamp.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Campeonato não encontrado")
                );
    }
    public void deletarTime(Long id){
        repositorycamp.deleteById(id);
    }
    public Camp atualizarCampPorID(Long id, Camp camp) {

        Camp usuarioEntity = repositorycamp.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Camp campAtualizado = Camp.builder()
                .id(camp.getId() != null
                        ? camp.getId()
                        : usuarioEntity.getId())
                .nome(camp.getNome() != null
                        ? camp.getNome()
                        : usuarioEntity.getNome())
                .build();

        return repositorycamp.save(campAtualizado);
    }
}
