package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogosService {

    public JogosService(CampeonatoService campService, RepositoryJogos repositoryJogos) {
        this.campService = campService;
        this.repositoryJogos = repositoryJogos;
    }

    private final CampeonatoService campService;
    private final RepositoryJogos repositoryJogos;

    public void gerarjogos(Long id)  {



            Jogos jogo = repositoryJogos.findById(Long id)
                    .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

            List<Times> times = jogo.gettimes();

            if (times.size() < 2) {
                throw new RuntimeException("É necessário pelo menos 2 times");
            }



    }
}
