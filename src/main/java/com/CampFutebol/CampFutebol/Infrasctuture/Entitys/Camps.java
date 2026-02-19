package com.CampFutebol.CampFutebol.Infrasctuture.Entitys;

import com.CampFutebol.CampFutebol.Infrasctuture.Enums.StatusCamps;
import com.CampFutebol.CampFutebol.Infrasctuture.Enums.StatusJogo;
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

    @Enumerated(EnumType.STRING)
    private StatusCamps status;

    private List<Times> times;
    private List<Jogos> jogos;

}
