package com.CampFutebol.CampFutebol.Controller;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camps;
import com.CampFutebol.CampFutebol.Service.CampeonatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CAMP")
public class CampController {

    private final CampeonatoService campservice;
    private final Camps camp;
    public CampController( CampeonatoService campservice, Camps camp){
        this.campservice = campservice;
        this.camp = camp;
    }

    @GetMapping
    public ResponseEntity<Camps> buscarCamp (@RequestParam Long id){
        return ResponseEntity.ok(campservice.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<Void> salvarCamp (@RequestBody Camps camp){
         campservice.salvarCamp(camp);

         return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity <Camps> deletarCamp (@RequestParam Long id){
        campservice.deletarTime(id);

        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Camps> atualizarCamp (@RequestParam Long id, @RequestBody Camps camp){
        campservice.atualizarCampPorID(id, camp);

        return ResponseEntity.ok().build();
    }
}
