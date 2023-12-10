package ru.jengine.jenginegdx;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.eventqueue.event.PostHandler;
import ru.jengine.jenginegdx.container.wrappers.EventBusWrapper;
import ru.jengine.jenginegdx.container.wrappers.WorldWrapper;
import ru.jengine.jenginegdx.view.renderes.TextureRenderer;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RendererComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.transform.TransformComponent;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransportEvent;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransportHolder;

public class ApplicationController extends JEngineAdapter {
	private Texture img;
	private ModelTransportHolder modelTransportHolder;

	@Override
	protected void configureContainer(DefaultContainerConfigurationBuilder configurationBuilder) { }

	@Override
	protected void createGameWithContainer(JEngineContainer container) {
		img = new Texture("badlogic.jpg");

		World world = container.getBean(WorldWrapper.class).getWorld();
		TextureRenderer renderer = container.getBean(TextureRenderer.class);

		EntityEdit badlogicEntity = world.createEntity().edit();
		badlogicEntity.create(TransformComponent.class).coordinates(Vector3.Zero);
		badlogicEntity.create(TextureComponent.class).texture(img);
		badlogicEntity.create(RendererComponent.class).renderer(renderer);

		EntityEdit badlogicEntity2 = world.createEntity().edit();
		badlogicEntity2.create(TransformComponent.class).coordinates(new Vector3(100, 100, 10));
		badlogicEntity2.create(TextureComponent.class).texture(img);
		badlogicEntity2.create(RendererComponent.class).renderer(renderer);

		modelTransportHolder = container.getBean(ModelTransportHolder.class);
		EventBusWrapper eventBus = container.getBean(EventBusWrapper.class);
		eventBus.registerHandler(new ModelEventHandler(world, badlogicEntity.getEntityId()));

		Gdx.app.log("dependency", TestDependency.VALUE);
	}

	@Override
	public void render()
	{
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			modelTransportHolder.send(new ModelEvent());
		}

		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		img.dispose();
	}

	private static class ModelEvent implements ModelTransportEvent { }

	private static class ModelEventHandler implements PostHandler<ModelEvent>
	{
		private final World world;
		private final int entityId;

		public ModelEventHandler(World world, int entityId)
		{
			this.world = world;
			this.entityId = entityId;
		}

		@Override
		public void handle(ModelEvent event) {
			TransformComponent component = world
					.getEntity(entityId)
					.getComponent(TransformComponent.class);
			component.coordinates = component.coordinates.add(10, 10, 5);
		}

		@Override
		public int getPriority() {
			return 0;
		}
	}
}
