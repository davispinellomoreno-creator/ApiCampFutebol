package com.CampFutebol.CampFutebol.Controller;


import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Times;
import com.CampFutebol.CampFutebol.Service.TimesFutebolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Times")
public class TimeController {

    public TimeController(TimesFutebolService timeservice, Times time) {
        this.timeservice = timeservice;
        this.time = time;
    }

    private  final TimesFutebolService timeservice;
    private  final Times time;

    @GetMapping
   public ResponseEntity<Times> buscarTime (@RequestParam Long id){
      return ResponseEntity.ok(TimesFutebolService.buscarTime(id));
       }
    @PostMapping
    public ResponseEntity<Void> salvarTime (@RequestBody Times time ){
    timeservice.salvarTime(time);

    return ResponseEntity.ok().build();

    }
    @PutMapping

    public ResponseEntity <Times> atualizarTimePorId (@RequestParam Long id, @RequestBody Times time){
        timeservice.atualizarTimePorID(id, time);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping

    public ResponseEntity <Void> deletarTimePorId (@RequestParam Long id){
        timeservice.deletarTime(id);

        return ResponseEntity.ok().build();
    }

}
