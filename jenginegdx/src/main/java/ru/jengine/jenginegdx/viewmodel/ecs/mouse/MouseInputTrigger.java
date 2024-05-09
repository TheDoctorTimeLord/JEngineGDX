package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import ru.jengine.jenginegdx.viewmodel.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.input.EventGenerator;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputTrigger;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseEventComponent;

import java.util.ArrayDeque;
import java.util.Queue;

public class MouseInputTrigger extends InputTrigger {
    private final GameCamera gameCamera;
    private final Pool<MouseEventData> dataPool = Pools.get(MouseEventData.class);
    private final Queue<MouseEventData> eventQueue = new ArrayDeque<>();
    private MouseEventType lastEventType = null;

    public MouseInputTrigger(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public void attachReceivedUserInput(EventGenerator eventGenerator) {
        while (!eventQueue.isEmpty()) {
            MouseEventData eventData = eventQueue.poll();

            eventGenerator.generate(MouseEventComponent.class)
                    .mousePosition(eventData.x, eventData.y)
                    .eventType(eventData.type);

            dataPool.free(eventData);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pushNewEvent(screenX, screenY, MouseEventType.TOUCH_DOWN);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        pushNewEvent(screenX, screenY, MouseEventType.DRAGGING.equals(lastEventType)
                ? MouseEventType.DRAGGED_TO
                : MouseEventType.TOUCH_UP);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pushNewEvent(screenX, screenY,
                MouseEventType.DRAGGING.equals(lastEventType) || MouseEventType.START_DRAGGING.equals(lastEventType)
                        ? MouseEventType.DRAGGING
                        : MouseEventType.START_DRAGGING);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        pushNewEvent(screenX, screenY, MouseEventType.MOVE);
        return true;
    }

    private void pushNewEvent(int screenX, int screenY, MouseEventType type) {
        Vector3 screenCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 unprojectedCoordinates = gameCamera.getCamera().unproject(screenCoordinates);

        lastEventType = type;

        MouseEventData mouseEventData = dataPool.obtain();
        mouseEventData.x = unprojectedCoordinates.x;
        mouseEventData.y = unprojectedCoordinates.y;
        mouseEventData.type = type;
        eventQueue.add(mouseEventData);
    }

    private static class MouseEventData implements Poolable {
        private float x;
        private float y;
        private MouseEventType type;

        @Override
        public void reset() {
            x = 0;
            y = 0;
            type = null;
        }
    }
}
