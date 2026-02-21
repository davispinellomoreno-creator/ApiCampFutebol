package com.CampFutebol.CampFutebol.Controller;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Jogos;
import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryJogos;
import com.CampFutebol.CampFutebol.Service.JogosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Jogos")
public class JogosController {
    private final JogosService jogoService;
   private final Jogos jogos;

    public JogosController(JogosService jogoService, RepositoryJogos repositoryJogos, Jogos jogos) {
        this.jogoService = jogoService;
        this.jogos = jogos;
    }
    @GetMapping
    ResponseEntity<Jogos> buscarJogo (@RequestParam Long id){
        return ResponseEntity.ok();

    }
}
