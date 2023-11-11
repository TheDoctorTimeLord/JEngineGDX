package ru.jengine.jenginegdx.viewmodel;

import com.badlogic.gdx.ApplicationAdapter;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.container.UpdatableManager;

public abstract class JEngineAdapter extends ApplicationAdapter {
    private JEngineContainer container;
    private UpdatableManager updatableManager;

    @Override
    public void create() {
        container = new JEngineContainer(prepareContainerConfiguration()
                //.addExternalModule(new MainModule()) TODO научиться включать внешние модули
                .build());
        container.initializeContainerByDefault();

        createGameWithContainer(container);

        updatableManager = container.getBean(UpdatableManager.class);
    }

    protected abstract DefaultContainerConfigurationBuilder prepareContainerConfiguration();

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
