package com.CampFutebol.CampFutebol.Infrasctuture.Entitys;

import com.CampFutebol.CampFutebol.Infrasctuture.Enums.StatusJogo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Cadastro_Jogos")
public class Jogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time_casa_id")
    private Times timecasa;

    @ManyToOne
    @JoinColumn(name = "time_fora_id")
    private Times timefora;

    @Column( name = "name")
    private Integer pontos;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Camps campeonato;

    @Column(name = "name")
    private int golsfora;

    @Column(name = "name")
    private int golscasa;

    @Enumerated(EnumType.STRING)
    private StatusJogo status;


}
