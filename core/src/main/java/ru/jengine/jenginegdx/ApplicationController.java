package ru.jengine.jenginegdx;

import com.artemis.ComponentMapper;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.Constants.Contexts;
import ru.jengine.jenginegdx.Constants.InputEvents;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;
import ru.jengine.jenginegdx.view.texture.TextureBoundComponent;
import ru.jengine.jenginegdx.view.texture.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FpsRenderingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.DroppedHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedContainerComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingSettingsComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.UserEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.components.VisibleComponent;

public class ApplicationController extends JEngineAdapter {
	private Texture img;

	@Override
	protected void configureContainer(DefaultContainerConfigurationBuilder configurationBuilder) {
		configurationBuilder
				.addBeans(Contexts.JENGINE, JEngineGdxConfiguration.build()
						.camera(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()))
				);
	}

	@Override
	protected void createGameWithContainer(JEngineContainer container) {
		img = new Texture("badlogic.jpg");

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		//spawnGridEntities(container, width, height);
		spawnSingleEntity(container,-100);
		spawnSingleEntity(container, 100);
		spawnDroppableEntity(container, width, height);

		attachMouseListener(container, width, height);
		attachFpsTracker(container);

		EventBus eventBus = container.getBean(EventBus.class);
		World world = container.getBean(WorldHolder.class).getWorld();
		eventBus.registerHandler(new UserEventSinglePostHandler(world));

		Gdx.app.log("dependency", TestDependency.VALUE);
	}

	private void spawnSingleEntity(JEngineContainer container, float xOffset) {
		World world = container.getBean(WorldHolder.class).getWorld();

		EntityEdit entity = world.createEntity().edit();
		entity.create(CoordinatesComponent.class).coordinates(xOffset, 0, 0);
		entity.create(TextureComponent.class).texture(img);
		entity.create(VisibleComponent.class);
		entity.create(MouseTouchBoundComponent.class).bounds(img.getWidth(), img.getHeight());
		entity.create(DraggingSettingsComponent.class).setDraggableType("simple");
		entity.create(UserEventHandlingComponent.class)
				.addHandling(InputEvents.MOUSE_START_DRAGGING, UserEvents.DRAG_AND_DROP)
				.addHandling(InputEvents.MOUSE_DRAGGED_TO, UserEvents.DROP_TO);
	}

	private void spawnDroppableEntity(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();

		EntityEdit entity = world.createEntity().edit();
		entity.create(CoordinatesComponent.class).coordinates((float) -width / 2, (float) -height / 2, -100);
		entity.create(TextureComponent.class).texture(img);
		entity.create(TextureBoundComponent.class).bound((float) width / 2, height);
		entity.create(VisibleComponent.class);
		entity.create(MouseTouchBoundComponent.class).bounds((float) width / 2, height);
		entity.create(DroppedContainerComponent.class).droppedHandler("simple", new DroppedHandler(world) {
			private ComponentMapper<TextureBoundComponent> textureBoundComponentMapper;
			private ComponentMapper<MouseTouchBoundComponent> mouseTouchBoundComponentMapper;
			@Override
			public void handle(int target, int container, float droppedX, float droppedY, float xOffsetToMouse, float yOffsetToMouse,
							   String draggingType)
			{
//				TextureBoundComponent textureBound = textureBoundComponentMapper.has(target)
//						? textureBoundComponentMapper.get(target)
//						: textureBoundComponentMapper.create(target);
//				textureBound.bound(100, 100);
//
//				if (mouseTouchBoundComponentMapper.has(target)) {
//					mouseTouchBoundComponentMapper.get(target).bounds(100, 100);
//				}
			}
		});
	}

	private void spawnGridEntities(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();

		int counter = 1;
		for (int x = -width / 2; x < width / 2; x += 100) {
			for (int y = -height / 2; y < height / 2; y += 100) {
				EntityEdit entity = world.createEntity().edit();
				entity.create(CoordinatesComponent.class).coordinates(x, y, 0);
				entity.create(TextureComponent.class).texture(img);
				entity.create(VisibleComponent.class);
				entity.create(MouseTouchBoundComponent.class).bounds(img.getWidth(), img.getHeight());
				entity.create(UserEventHandlingComponent.class)
						.addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touched" + counter++);
			}
		}
	}

	public void attachMouseListener(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();

		EntityEdit entity = world.createEntity().edit();
		entity.create(CoordinatesComponent.class).coordinates((float) -width / 2, (float) -height / 2, 0);
		entity.create(MouseTouchBoundComponent.class).bounds(width, height);
		entity.create(UserEventHandlingComponent.class)
				.addHandling(InputEvents.MOUSE_TOUCH_DOWN, InputEvents.MOUSE_TOUCH_DOWN)
				.addHandling(InputEvents.MOUSE_TOUCH_UP, InputEvents.MOUSE_TOUCH_UP)
				.addHandling(InputEvents.MOUSE_MOVE, InputEvents.MOUSE_MOVE)
				.addHandling(InputEvents.MOUSE_START_DRAGGING, InputEvents.MOUSE_START_DRAGGING)
				.addHandling(InputEvents.MOUSE_DRAGGING, InputEvents.MOUSE_DRAGGING)
				.addHandling(InputEvents.MOUSE_DRAGGED_TO, InputEvents.MOUSE_DRAGGED_TO);
	}

	public void attachFpsTracker(JEngineContainer container) {
		World world = container.getBean(WorldHolder.class).getWorld();

		world.createEntity().edit().create(FpsRenderingComponent.class);
	}

	@Override
	public void dispose () {
		super.dispose();
		img.dispose();
	}

	private static class UserEventSinglePostHandler extends SinglePostHandler<UserEvent> {
		private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

		public UserEventSinglePostHandler(World world) {
			world.inject(this);
		}

		@Override
		public void handle(UserEvent event) {
			StringBuilder builder = new StringBuilder();
			builder.append("Handled event [");
			builder.append(event.getEvent());

			if (mouseTouchedComponentMapper.has(event.getTargetEntityId())) {
				MouseTouchedComponent component = mouseTouchedComponentMapper.get(event.getTargetEntityId());
				builder.append(", on {");
				builder.append(component.getX());
				builder.append(",");
				builder.append(component.getY());
				builder.append("}");
			}

			builder.append("]");
			System.out.println(builder);
		}
	}
}
