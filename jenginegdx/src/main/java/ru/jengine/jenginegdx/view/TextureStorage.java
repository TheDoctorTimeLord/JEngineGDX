package ru.jengine.jenginegdx.view;

import com.badlogic.gdx.graphics.Texture;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.PreDestroy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Bean
public class TextureStorage {
    private Map<String, Texture> storage = new ConcurrentHashMap<>();

    public Texture get(String internalPath) { //TODO доработать поиск текстуры по локальным файлам
        return storage.computeIfAbsent(internalPath, Texture::new);
    }

    @PreDestroy
    public void disposeTextures() {
        for (Texture texture : storage.values()) {
            texture.dispose();
        }
    }
}
