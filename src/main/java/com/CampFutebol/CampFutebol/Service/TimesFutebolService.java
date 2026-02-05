package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryTime;
import org.springframework.stereotype.Service;

@Service
public class TimesFutebolService {


    private static  RepositoryTime repository;

    public TimesFutebolService(RepositoryTime repository) {
        this.repository = repository;
    }

    public void salvarTime(Times time){
    repository.saveAndFlush(time);
   }

   public void deletarTime(Long id){
        repository.deleteById(id);
   }

   public static Times buscarTime(Long id){
        return repository.findById(id)
                .orElseThrow(
                        ()-> new RuntimeException("Time não encontrado")
                );
   }

    public Times atualizarTimePorID(Long id, Times time) {
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
                .vitorias(time.getVitorias() != null
                        ? time.getVitorias()
                        : timeEntity.getVitorias())
                .pontos(time.getPontos() != null
                        ? time.getPontos()
                        : timeEntity.getPontos())
                .derrotas(time.getDerrotas() != null
                        ? time.getDerrotas()
                        : timeEntity.getDerrotas())
                .build();

        return repository.save(timeAtualizado);
    }

}
