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
    private String timefora;

    @Column( name = "name")
    private String timecasa;

    @Column( name = "name")
    private Integer pontos;
}
