package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camps;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

            List<Times> times = Collections.singletonList(jogo.getTimefora());


            if (times.size() < 2) {
                throw new RuntimeException("É necessário pelo menos 2 times");
            }
        if (!jogo.get().isEmpty()) {
            throw new RuntimeException("Os jogos já foram gerados");
        }

        List<Jogos> jogos = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            for (int j = i + 1; j < times.size(); j++) {

                Times timeCasa = times.get(i);
                Times timeFora = times.get(j);

                Jogos jogo = new jogo();
                jogo.setTimecasa(timeCasa);
                jogo.setTimefora(timeFora);
                jogo.setCampeonato(Camps);
                jogo.setStatus(StatusJogo.NAO_JOGADO);

                jogos.add(jogo);
            }
        }

        repositoryJogos.saveAll(jogos);

        Jogos.(StatusCampeonato.EM_ANDAMENTO);
        campeonatoRepository.save(jogos);
    }




    }

