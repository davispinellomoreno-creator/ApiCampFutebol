package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camps;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryCampeonato;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CampeonatoService {

    private RepositoryCampeonato repositorycamp;

    public CampeonatoService(RepositoryCampeonato repositorycamp) {
        this.repositorycamp = repositorycamp;
    }

    public ResponseEntity<Void> salvarCamp(Camps camp){
        repositorycamp.saveAndFlush(camp);

    }
    public Camps buscarPorId(Long id){
       return repositorycamp.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Campeonato não encontrado")
                );
    }
    public void deletarTime(Long id){
        repositorycamp.deleteById(id);
    }
    public Camps atualizarCampPorID(Long id, Camps camp) {

        Camps usuarioEntity = repositorycamp.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Camps campAtualizado = Camps.builder()
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
