package ru.jengine.jenginegdx.viewmodel.ecs.text.components;

import com.artemis.PooledComponent;

public class TextComponent extends PooledComponent {
    private String text;

    public TextComponent text(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    protected void reset() {
        this.text = null;
    }
}
