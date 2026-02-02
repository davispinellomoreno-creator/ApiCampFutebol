package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camp;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryCampeonato;
import org.springframework.stereotype.Service;

@Service
public class CampeonatoService {

    private RepositoryCampeonato repositorycamp;
    public void salvarCamp(Camp camp){
        repositorycamp.saveAndFlush(camp);
    }
    public void buscarPorId(Long id){
        repositorycamp.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Time não encontrado")
                );
    }
    public void deletarTime(Long id){
        repositorycamp.deleteById(id);
    }
    public void atualizarTime(Long id){
        repositorycamp.findById(id);
    }
}
