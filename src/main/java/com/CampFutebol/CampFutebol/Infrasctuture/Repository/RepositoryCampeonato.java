package com.CampFutebol.CampFutebol.Infrasctuture.Repository;

import com.CampFutebol.CampFutebol.Infrasctuture.Entitys.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCampeonato extends JpaRepository<Camp, Long> {
}
