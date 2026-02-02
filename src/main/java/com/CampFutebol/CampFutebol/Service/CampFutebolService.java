package com.CampFutebol.CampFutebol.Service;

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

}
