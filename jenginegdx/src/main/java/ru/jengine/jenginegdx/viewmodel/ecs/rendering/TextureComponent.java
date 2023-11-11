package ru.jengine.jenginegdx.viewmodel.ecs.rendering;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class TextureComponent extends Component {
    public Texture texture;

    public TextureComponent texture(Texture texture) {
        this.texture = texture;
        return this;
    }
}
