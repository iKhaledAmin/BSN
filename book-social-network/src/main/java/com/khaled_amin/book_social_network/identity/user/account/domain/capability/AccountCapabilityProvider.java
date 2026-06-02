package com.khaled_amin.book_social_network.identity.user.account.domain.capability;

import com.khaled_amin.book_social_network.identity.capability.application.provider.CapabilityProvider;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AccountCapabilityProvider implements CapabilityProvider {

    @Override
    public Set<AccountCapability> getCapabilities() {
        return Set.of(AccountCapability.values());
    }
}