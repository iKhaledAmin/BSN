package com.khaled_amin.book_social_network.identity.client.infrastructure;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.identity.client.domain.model.Client;

import java.util.Optional;

public interface ClientJpaRepository  extends BaseRepository<Client,Long>{

    Optional<Client> findByClientId(String clientId);
}
