package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.input.EventGenerator;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserInputTrigger;

@Bean
public class MouseInputTrigger extends UserInputTrigger {
    private final GameCamera gameCamera;
    private float currentMouseX;
    private float currentMouseY;
    private MouseEventType eventType;
    private boolean hasInput = false;

    public MouseInputTrigger(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public void attachReceivedUserInput(EventGenerator eventGenerator) {
        if (hasInput) {
            hasInput = false;
            MouseEventType type = eventType;

            Vector3 screenCoordinates = new Vector3(currentMouseX, currentMouseY, 0);
            Vector3 worldCoordinates = gameCamera.getCamera().unproject(screenCoordinates);

            eventGenerator.generate(MouseEventComponent.class)
                    .mouseX(worldCoordinates.x)
                    .mouseY(worldCoordinates.y)
                    .eventType(type);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.TOUCH_DOWN;
        hasInput = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.DRAGGING.equals(eventType)
                ? MouseEventType.DRAGGED_TO
                : MouseEventType.TOUCH_UP;
        hasInput = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.DRAGGING;
        hasInput = true;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.MOVE;
        hasInput = true;
        return true;
    }
}
