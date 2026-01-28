package com.CampFutebol.CampFutebol.Service;

import com.CampFutebol.CampFutebol.Infrasctuture.Repository.RepositoryTime;
import org.springframework.stereotype.Service;

@Service
public class CampFutebolService {

    private final RepositoryTime repository;

    public CampFutebolService(RepositoryTime repository) {
        this.repository = repository;
    }
}
