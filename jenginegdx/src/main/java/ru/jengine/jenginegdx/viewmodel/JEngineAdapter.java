package ru.jengine.jenginegdx.viewmodel;

import com.badlogic.gdx.ApplicationAdapter;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.ContainerConfiguration;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.container.modules.MainGameModel;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;

public abstract class JEngineAdapter extends ApplicationAdapter {
    private JEngineContainer container;
    private WorldHolder worldHolder;

    @Override
    public void create() {
        DefaultContainerConfigurationBuilder configurationBuilder = ContainerConfiguration.builder(MainGameModel.class);
        configureContainer(configurationBuilder);

        container = new JEngineContainer(configurationBuilder.build());
        container.initializeContainerByDefault();

        createGameWithContainer(container);

        worldHolder = container.getBean(WorldHolder.class);
    }

    protected abstract void configureContainer(DefaultContainerConfigurationBuilder configurationBuilder);

    protected abstract void createGameWithContainer(JEngineContainer container);

    @Override
    public void resize(int width, int height) {
        //TODO добавить действия при ресайзе
    }

    @Override
    public void render() {
        worldHolder.update();
    }

    @Override
    public void dispose() {
        container.stop();
    }
}
