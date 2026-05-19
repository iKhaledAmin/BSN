package com.khaled_amin.book_social_network.identity.user.role.domain.definition;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.account.domain.capability.AccountCapability;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class UserRoleCapabilityDefinition implements SystemRoleCapabilityDefinition{
    @Override
    public SystemRole getRole() {
        return SystemRole.USER;
    }

    @Override
    public Set<CapabilityCode> getCapabilityCodes() {

        return Set.of(
                AccountCapability.ACCOUNT_READ_SELF.getCode(),
                AccountCapability.ACCOUNT_UPDATE_SELF.getCode()
        );
    }
}
