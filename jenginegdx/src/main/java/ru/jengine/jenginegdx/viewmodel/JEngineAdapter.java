package ru.jengine.jenginegdx.viewmodel;

import com.badlogic.gdx.ApplicationAdapter;

import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.ContainerConfiguration;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.container.UpdatableManager;
import ru.jengine.jenginegdx.container.modules.MainGameModel;

public abstract class JEngineAdapter extends ApplicationAdapter {
    private JEngineContainer container;
    private UpdatableManager updatableManager;

    @Override
    public void create() {
        DefaultContainerConfigurationBuilder configurationBuilder = ContainerConfiguration.builder(MainGameModel.class);
        configureContainer(configurationBuilder);

        container = new JEngineContainer(configurationBuilder.build());
        container.initializeContainerByDefault();

        createGameWithContainer(container);

        updatableManager = container.getBean(UpdatableManager.class);
    }

    protected abstract void configureContainer(DefaultContainerConfigurationBuilder configurationBuilder);

    protected abstract void createGameWithContainer(JEngineContainer container);

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        updatableManager.updateSystems();
    }

    @Override
    public void dispose() {
        container.stop();
    }
}
