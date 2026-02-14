package com.CampFutebol.CampFutebol.Infrasctuture.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Cadastro_Camp")
public class Camps {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "nome",unique = true)
    private String nome;

    private List<Times> times;
    private List<Jogos> jogos;

}
