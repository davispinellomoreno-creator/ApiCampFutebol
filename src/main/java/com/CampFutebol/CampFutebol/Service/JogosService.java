package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camps;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Infrasctuture.Enums.StatusCamps;
import com.CampFutebol.CampFutebol.Infrasctuture.Enums.StatusJogo;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryCampeonato;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JogosService {

    private final RepositoryCampeonato campeonatoRepository;
    private final RepositoryJogos jogosRepository;






    public JogosService(RepositoryCampeonato campeonatoRepository,
                        RepositoryJogos jogosRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.jogosRepository = jogosRepository;

    }

    public void gerarJogos(Long campeonatoId) {


        Camps campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));


        List<Times> times = campeonato.getTimes();

        if (times.size() < 2) {
            throw new RuntimeException("É necessário pelo menos 2 times");
        }


        if (!campeonato.getJogos().isEmpty()) {
            throw new RuntimeException("Os jogos já foram gerados para este campeonato");
        }


        List<Jogos> jogos = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            for (int j = i + 1; j < times.size(); j++) {

                Times timeCasa = times.get(i);
                Times timeFora = times.get(j);

                Jogos jogo = new Jogos();
                jogo.setTimecasa(timeCasa);
                jogo.setTimefora(timeFora);
                jogo.setCampeonato(campeonato);
                jogo.setStatus(StatusJogo.NAO_INICIADO);

                jogos.add(jogo);
            }
        }

        jogosRepository.saveAll(jogos);


        campeonato.setStatus(StatusCamps.EM_ANDAMENTO);
        campeonato.setJogos(jogos);

        campeonatoRepository.save(campeonato);
    }
}

