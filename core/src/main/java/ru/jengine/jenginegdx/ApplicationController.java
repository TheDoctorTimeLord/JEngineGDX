package ru.jengine.jenginegdx;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.Constants.Contexts;
import ru.jengine.jenginegdx.Constants.InputEvents;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.utils.figures.RectangleBound;
import ru.jengine.jenginegdx.view.renderes.LabelRenderer;
import ru.jengine.jenginegdx.view.renderes.TextureRenderer;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.ecs.bounds.BoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyChildrenComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyParentComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyRootComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseUserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.label.LabelComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RendererComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.systems.FpsMarkerComponent;

public class ApplicationController extends JEngineAdapter {
	private Texture img;

	@Override
	protected void configureContainer(DefaultContainerConfigurationBuilder configurationBuilder) {
		configurationBuilder.addBeans(Contexts.JENGINE,
				new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
		);
	}

	@Override
	protected void createGameWithContainer(JEngineContainer container) {
		img = new Texture("badlogic.jpg");

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		spawnHierarchyEntities(container);
//		spawnGridEntities(container, width, height);
//		spawnManyEntities(container);

		spawnFpsEntity(container, width, height);
		//spawnMouseTracker(container, width, height);

		EventBus eventBus = container.getBean(EventBus.class);
		eventBus.registerHandler(new SinglePostHandler<UserEvent>() {
			@Override
			public void handle(UserEvent event) {
				System.out.println("Handled event [" + event.getUserEvent() + "]");
			}
		});
		eventBus.registerHandler(new SinglePostHandler<MouseUserEvent>() {
			@Override
			public void handle(MouseUserEvent event) {
                System.out.println("Handled event [" + event.getUserEvent() +
						", x = " + event.getScreenX() +
						", y = " + event.getScreenY() +
						"]");
			}
		});

		Gdx.app.log("dependency", TestDependency.VALUE);
	}

	private void spawnHierarchyEntities(JEngineContainer container) {
		World world = container.getBean(WorldHolder.class).getWorld();
		TextureRenderer textureRenderer = container.getBean(TextureRenderer.class);

		EntityEdit root = world.createEntity().edit();
		root.create(CoordinatesComponent.class).coordinates = Vector3.Zero;
		root.create(HierarchyRootComponent.class);

		EntityEdit badlogicEntity = world.createEntity().edit();
		badlogicEntity.create(CoordinatesComponent.class).coordinates = Vector3.Zero;
		badlogicEntity.create(TextureComponent.class).texture = img;
		badlogicEntity.create(RendererComponent.class).renderer = textureRenderer;
		badlogicEntity.create(HierarchyParentComponent.class).parent = root.getEntityId();
		badlogicEntity.create(BoundComponent.class).bound = new RectangleBound(img.getWidth(), img.getHeight());
		badlogicEntity.create(UserEventHandlingComponent.class)
				.addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touchedEntity1")
				.addHandling(InputEvents.MOUSE_DRAGGING, UserEvents.DRAG_AND_DROP);

		EntityEdit badlogicEntity2 = world.createEntity().edit();
		badlogicEntity2.create(CoordinatesComponent.class).coordinates = new Vector3(100, 100, 10);
		badlogicEntity2.create(TextureComponent.class).texture = img;
		badlogicEntity2.create(RendererComponent.class).renderer = textureRenderer;
		badlogicEntity2.create(HierarchyParentComponent.class).parent = root.getEntityId();
		badlogicEntity2.create(BoundComponent.class).bound = new RectangleBound(img.getWidth(), img.getHeight());
		badlogicEntity2.create(UserEventHandlingComponent.class).addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touchedEntity2");

		root.create(HierarchyChildrenComponent.class)
				.addChildren(badlogicEntity.getEntityId())
				.addChildren(badlogicEntity2.getEntityId());
	}

	private void spawnGridEntities(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();
		TextureRenderer textureRenderer = container.getBean(TextureRenderer.class);

		int counter = 1;
		for (int x = -width / 2; x < width / 2; x += 100) {
			for (int y = -height / 2; y < height / 2; y += 100) {
				EntityEdit entity = world.createEntity().edit();
				entity.create(CoordinatesComponent.class).coordinates(new Vector3(x, y, 0));
				entity.create(TextureComponent.class).texture = img;
				entity.create(RendererComponent.class).renderer = textureRenderer;
				entity.create(BoundComponent.class).bound = new RectangleBound(img.getWidth(), img.getHeight());
				entity.create(UserEventHandlingComponent.class).addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touchedEntity" + counter++);
			}
		}
	}

	private void spawnManyEntities(JEngineContainer container) {
		World world = container.getBean(WorldHolder.class).getWorld();
		TextureRenderer textureRenderer = container.getBean(TextureRenderer.class);

		for (int i = 0; i < 10000; i++) {
			EntityEdit entity = world.createEntity().edit();
			entity.create(CoordinatesComponent.class).coordinates(Vector3.Zero);
			entity.create(TextureComponent.class).texture = img;
			entity.create(RendererComponent.class).renderer = textureRenderer;
			entity.create(BoundComponent.class).bound = new RectangleBound(img.getWidth(), img.getHeight());
			entity.create(UserEventHandlingComponent.class).addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touchedEntity" + i);
		}
	}

	private void spawnFpsEntity(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();
		LabelRenderer labelRenderer = container.getBean(LabelRenderer.class);

		EntityEdit fpsEntity = world.createEntity().edit();
		fpsEntity.create(CoordinatesComponent.class).coordinates(new Vector3(width / 2 - 20, height / 2, 100));
		fpsEntity.create(LabelComponent.class).font(new BitmapFont());
		fpsEntity.create(RendererComponent.class).renderer = labelRenderer;
		fpsEntity.create(FpsMarkerComponent.class);
	}

	private void spawnMouseTracker(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();

		EntityEdit mouseTracker = world.createEntity().edit();
		mouseTracker.create(CoordinatesComponent.class).coordinates(new Vector3(-width / 2, -height /2, 100));
		mouseTracker.create(BoundComponent.class).bound(new RectangleBound(width, height));
		mouseTracker.create(UserEventHandlingComponent.class)
				.addHandling(InputEvents.MOUSE_TOUCH_DOWN, "Mouse down")
				.addHandling(InputEvents.MOUSE_TOUCH_UP, "Mouse up")
				.addHandling(InputEvents.MOUSE_DRAGGED_TO, "Mouse dragged to")
				.addHandling(InputEvents.MOUSE_MOVE, "Mouse move")
				.addHandling(InputEvents.MOUSE_DRAGGING, "Mouse dragging");
	}

	@Override
	public void dispose () {
		super.dispose();
		img.dispose();
	}
}
