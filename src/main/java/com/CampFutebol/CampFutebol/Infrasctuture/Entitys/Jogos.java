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

    private String timefora;
    private String timecasa;
    private Integer pontos;
}
