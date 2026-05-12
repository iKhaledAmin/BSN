package com.khaled_amin.book_social_network.identity.core.generator;


import com.github.f4b6a3.ulid.UlidCreator;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import org.springframework.stereotype.Component;

@Component
public class UlidActorCodeGenerator implements ActorCodeGenerator {

    @Override
    public ActorCode generate(ActorType actorType) {

        // Technical actors use stable predefined code
        if (actorType.isTechnicalActor()) {
            return ActorCode.of(actorType.getCodePrefix());
        }

        // Domain actors get generated unique identity
        String value = actorType.getCodePrefix() + "_" + UlidCreator.getUlid();

        return ActorCode.of(value);
    }
}