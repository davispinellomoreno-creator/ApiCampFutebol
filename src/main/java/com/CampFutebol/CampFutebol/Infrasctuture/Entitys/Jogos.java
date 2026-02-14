package com.CampFutebol.CampFutebol.Infrasctuture.Entitys;

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

    @Column( name = "name")
    private Times timecasa;

    @Column( name = "name")
    private Times timefora;

    @Column( name = "name")
    private Integer pontos;

    @Column(name = "name")
    private Camps campeonato;

    @Column(name = "name")
    private int golsfora;

    @Column(name = "name")
    private int golscasa;




}
