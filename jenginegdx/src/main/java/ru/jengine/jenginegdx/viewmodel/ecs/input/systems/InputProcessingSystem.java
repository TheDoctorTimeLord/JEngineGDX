package ru.jengine.jenginegdx.viewmodel.ecs.input.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.input.EventGenerator;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputTrigger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Bean
@Order(50)
public class InputProcessingSystem extends BaseSystem implements InputProcessor {
    private final Collection<InputTrigger> inputTriggers = new ArrayList<>();
    private final Queue<InputTrigger> addedTriggers = new ConcurrentLinkedDeque<>();
    private final Queue<InputTrigger> removedTriggers = new ConcurrentLinkedDeque<>();
    private EventGenerator eventGenerator;

    public InputProcessingSystem() {
        Gdx.input.setInputProcessor(this);
    }

    public void registerTrigger(InputTrigger trigger) {
        addedTriggers.add(trigger);
    }

    public void unregisterTrigger(InputTrigger trigger) {
        removedTriggers.add(trigger);
    }

    @Override
    protected void initialize() {
        eventGenerator = new EventGenerator(world);
    }

    @Override
    protected void processSystem() {
        EventGenerator generator = getEntityGenerator();
        preprocessTriggers();

        for (InputTrigger trigger : inputTriggers) {
            trigger.attachReceivedUserInput(generator);
        }
    }

    private void preprocessTriggers() {
        while (!addedTriggers.isEmpty()) {
            inputTriggers.add(addedTriggers.poll());
        }

        while (!removedTriggers.isEmpty()) {
            inputTriggers.remove(removedTriggers.poll());
        }
    }

    private EventGenerator getEntityGenerator() {
        if (eventGenerator == null) {
            throw new IllegalStateException("System is running but was not attach to any world");
        }
        return eventGenerator;
    }

    @Override
    public boolean keyDown(int keycode) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyDown(keycode);
        }
        return handled;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyUp(keycode);
        }
        return handled;
    }

    @Override
    public boolean keyTyped(char character) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyTyped(character);
        }
        return handled;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchDown(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchUp(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchCancelled(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchDragged(screenX, screenY, pointer);
        }
        return handled;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.mouseMoved(screenX, screenY);
        }
        return handled;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        boolean handled = false;
        for (InputTrigger trigger : inputTriggers) {
            handled = handled || trigger.scrolled(amountX, amountY);
        }
        return handled;
    }
}
