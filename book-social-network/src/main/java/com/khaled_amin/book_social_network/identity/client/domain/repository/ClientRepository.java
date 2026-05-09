package com.khaled_amin.book_social_network.identity.client.domain.repository;

import com.khaled_amin.book_social_network.identity.client.domain.model.Client;
import java.util.Optional;

public interface ClientRepository {

    Optional<Client> findByClientId(String clientId);
}
