package ru.jengine.jenginegdx;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.JEngineContainer;
import ru.jengine.beancontainer.configuration.ContainerConfiguration;
import ru.jengine.beancontainer.configuration.DefaultContainerConfigurationBuilder;
import ru.jengine.jenginegdx.container.modules.MainModule;
import ru.jengine.jenginegdx.container.wrappers.WorldWrapper;
import ru.jengine.jenginegdx.view.renderes.TextureRenderer;
import ru.jengine.jenginegdx.viewmodel.JEngineAdapter;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RendererComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.transform.TransformComponent;

public class ApplicationController extends JEngineAdapter {
	Texture img;

	@Override
	protected DefaultContainerConfigurationBuilder prepareContainerConfiguration() {
		return ContainerConfiguration.builder(MainModule.class);
	}

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
		badlogicEntity2.create(TransformComponent.class).coordinates(new Vector3(100, 100, 0));
		badlogicEntity2.create(TextureComponent.class).texture(img);
		badlogicEntity2.create(RendererComponent.class).renderer(renderer);

		Gdx.app.log("dependency", TestDependency.VALUE);
	}

	@Override
	public void dispose () {
		super.dispose();
		img.dispose();
	}
}
