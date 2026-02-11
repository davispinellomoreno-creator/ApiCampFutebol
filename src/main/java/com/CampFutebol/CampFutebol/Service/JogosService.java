package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import org.springframework.stereotype.Service;

@Service
public class JogosService {

    public JogosService(CampeonatoService campService, RepositoryJogos repositoryJogos) {
        this.campService = campService;
        this.repositoryJogos = repositoryJogos;
    }

    private final CampeonatoService campService;
    private final RepositoryJogos repositoryJogos;

    public void gerarjogos(Long id)  {

        Jogos jogo = repositoryJogos.findById(id)
                .orElseThrow( ()-> new RuntimeException("Jogo não encontrado")

                        );


    }
}
