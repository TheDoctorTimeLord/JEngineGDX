package ru.jengine.jenginegdx.viewmodel.ecs.input;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;

import java.util.Collection;
import java.util.List;

@Bean
@Order()
public class InputProcessingSystem extends BaseSystem implements InputProcessor {
    private final Collection<UserInputTrigger> inputTriggers;
    private EventGenerator eventGenerator;

    public InputProcessingSystem(List<UserInputTrigger> inputTriggers) {
        this.inputTriggers = inputTriggers;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        eventGenerator = new EventGenerator(world);
    }

    @Override
    protected void processSystem() {
        EventGenerator generator = getEntityGenerator();

        for (UserInputTrigger trigger : inputTriggers) {
            trigger.attachReceivedUserInput(generator);
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
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyDown(keycode);
        }
        return handled;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyUp(keycode);
        }
        return handled;
    }

    @Override
    public boolean keyTyped(char character) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.keyTyped(character);
        }
        return handled;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchDown(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchUp(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchCancelled(screenX, screenY, pointer, button);
        }
        return handled;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.touchDragged(screenX, screenY, pointer);
        }
        return handled;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.mouseMoved(screenX, screenY);
        }
        return handled;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        boolean handled = false;
        for (UserInputTrigger trigger : inputTriggers) {
            handled = handled || trigger.scrolled(amountX, amountY);
        }
        return handled;
    }
}
