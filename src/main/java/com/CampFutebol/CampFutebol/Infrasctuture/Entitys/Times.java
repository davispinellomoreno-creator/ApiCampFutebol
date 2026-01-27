package com.CampFutebol.CampFutebol.Infrasctuture.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Cadastro_Time")
public class Times {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome" , unique = true)
    private String nome;


}
