package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camp;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryCampeonato;
import org.springframework.stereotype.Service;

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
    public void atualizarCamp(Long id, Camp camp){
        repositorycamp.findById(id);
    }
}
