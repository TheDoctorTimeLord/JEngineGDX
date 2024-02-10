package ru.jengine.jenginegdx;

import com.artemis.ComponentMapper;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.Constants.Contexts;
import ru.jengine.jenginegdx.Constants.InputEvents;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;
import ru.jengine.jenginegdx.view.texture.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseTouchBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseTouchedComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.VisibleComponent;

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

		spawnGridEntities(container, width, height);

		EventBus eventBus = container.getBean(EventBus.class);
		eventBus.registerHandler(new UserEventSinglePostHandler(container.getBean(WorldHolder.class).getWorld()));

		Gdx.app.log("dependency", TestDependency.VALUE);
	}

	private void spawnGridEntities(JEngineContainer container, int width, int height) {
		World world = container.getBean(WorldHolder.class).getWorld();

		int counter = 1;
		for (int x = -width / 2; x < width / 2; x += 100) {
			for (int y = -height / 2; y < height / 2; y += 100) {
				EntityEdit entity = world.createEntity().edit();
				entity.create(CoordinatesComponent.class).coordinates(new Vector3(x, y, 0));
				entity.create(TextureComponent.class).texture(img);
				entity.create(VisibleComponent.class);
				entity.create(MouseTouchBoundComponent.class).bounds(img.getWidth(), img.getHeight());
				entity.create(UserEventHandlingComponent.class)
						.addHandling(InputEvents.MOUSE_TOUCH_DOWN, "touched" + counter++);
			}
		}
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
