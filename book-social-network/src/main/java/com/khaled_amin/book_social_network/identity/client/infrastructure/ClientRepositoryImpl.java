package com.khaled_amin.book_social_network.identity.client.infrastructure;

import com.khaled_amin.book_social_network.identity.client.domain.model.Client;
import com.khaled_amin.book_social_network.identity.client.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientJpaRepository clientJpaRepository;

    @Override
    public Optional<Client> findByClientId(String clientId) {
        return clientJpaRepository.findByClientId(clientId);
    }
}
