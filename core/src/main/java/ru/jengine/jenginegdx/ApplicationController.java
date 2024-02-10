package ru.jengine.jenginegdx;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.Constants.Contexts;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;
import ru.jengine.jenginegdx.view.texture.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
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
		eventBus.registerHandler(new SinglePostHandler<UserEvent>() {
			@Override
			public void handle(UserEvent event) {
				System.out.println("Handled event [" + event.getEvent() + "]");
			}
		});

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
			}
		}
	}

	@Override
	public void dispose () {
		super.dispose();
		img.dispose();
	}
}
