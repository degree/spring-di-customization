package com.github.monosoul.fortuneteller.domain.impl.fortuneteller;

import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.PersonalData;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class PersonalDataExtractor implements Function<FortuneRequest, PersonalData> {

    private final Predicate<FortuneRequest> requestValidator;

    public PersonalDataExtractor(@NonNull final Predicate<FortuneRequest> requestValidator) {
        this.requestValidator = requestValidator;
    }

    @Override
    public PersonalData apply(final FortuneRequest request) {
        if (!requestValidator.test(request)) {
            throw new IllegalArgumentException("Invalid request!");
        }

        return PersonalData.builder()
                           .name(request.getName())
                           .age(request.getAge())
                           .email(request.getEmail())
                           .build();
    }
}
