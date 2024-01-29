package ru.jengine.jenginegdx.view.renderes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.view.Renderer;
import ru.jengine.jenginegdx.viewmodel.ecs.label.LabelComponent;

@Bean
public class LabelRenderer extends Renderer {
    private ComponentMapper<LabelComponent> labelComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        BitmapFontCache fontCache = labelComponentMapper.get(entityId).fontCache;
        fontCache.draw(batch);
    }
}
