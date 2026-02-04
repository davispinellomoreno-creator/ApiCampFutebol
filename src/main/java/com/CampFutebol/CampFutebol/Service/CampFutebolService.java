package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camp;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryTime;
import org.springframework.stereotype.Service;

@Service
public class CampFutebolService {

    private final RepositoryTime repository;

    public CampFutebolService(RepositoryTime repository) {
        this.repository = repository;
    }

    public void salvarTime(Times time){
    repository.saveAndFlush(time);
   }

   public void deletarTime(Times time){
        repository.deleteById(time.getId());
   }

   public Times buscarTime(Times time){
        return repository.findById(time.getId())
                .orElseThrow(
                        ()-> new RuntimeException("Time não encontrado")
                );
   }

    public Times atualizarUsuarioPorID(Long id, Times time) {

        Times timeEntity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        Times timeAtualizado = Times.builder()
                .id(time.getId() != null
                        ? time.getId()
                        : timeEntity.getId())
                .nome(time.getNome() != null
                        ? time.getNome()
                        : timeEntity.getNome())
                .pontos(time.getPontos() != null
                        ? time.getPontos()
                        : timeEntity.getPontos())
                .build();

        return repository.save(timeAtualizado);
    }

}
