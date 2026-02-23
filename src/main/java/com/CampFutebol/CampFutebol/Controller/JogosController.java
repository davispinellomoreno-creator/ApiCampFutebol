package com.CampFutebol.CampFutebol.Controller;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camps;
import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import com.CampFutebol.CampFutebol.Service.JogosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Jogos")
public class JogosController {
    private final JogosService jogoService;
   private final Jogos jogos;
    private final Camps camp;
    public JogosController(JogosService jogoService, RepositoryJogos repositoryJogos, Jogos jogos, Camps camp) {
        this.jogoService = jogoService;
        this.jogos = jogos;
        this.camp = camp;
    }


        @PostMapping("/gerar/{campeonatoId}")
        public ResponseEntity<String> gerarJogos(@PathVariable Long id) {

            jogoService.gerarJogos(camp.getId());

            return ResponseEntity.ok("Jogos gerados com sucesso!");
        }
    }

