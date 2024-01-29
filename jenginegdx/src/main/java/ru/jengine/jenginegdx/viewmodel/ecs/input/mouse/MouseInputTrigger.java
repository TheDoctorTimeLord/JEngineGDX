package ru.jengine.jenginegdx.viewmodel.ecs.input.mouse;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.input.EventGenerator;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserInputTrigger;
import ru.jengine.jenginegdx.viewmodel.ecs.input.mouse.MouseEventComponent.MouseEventType;

@Bean
public class MouseInputTrigger extends UserInputTrigger {
    private final GameCamera gameCamera;
    private float currentMouseX;
    private float currentMouseY;
    private MouseEventType eventType;

    public MouseInputTrigger(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public void attachReceivedUserInput(EventGenerator eventGenerator) {
        if (eventType != null) {
            MouseEventType type = eventType;
            eventType = null;

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
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.TOUCH_UP;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.DRAGGING;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        currentMouseX = screenX;
        currentMouseY = screenY;
        eventType = MouseEventType.MOVE;
        return true;
    }
}
