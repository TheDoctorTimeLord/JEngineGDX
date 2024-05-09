package ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.SharedBeansProvider;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

import java.util.List;

@Bean
public class WorldValidationManager {
    private List<WorldValidator> validators = List.of();

    @SharedBeansProvider
    public void provideValidators(List<WorldValidator> validators) {
        this.validators = validators;
    }

    public void validate(WorldHolder worldHolder) {
        for (WorldValidator validator : validators) {
            validator.validate(worldHolder);
        }
    }
}
