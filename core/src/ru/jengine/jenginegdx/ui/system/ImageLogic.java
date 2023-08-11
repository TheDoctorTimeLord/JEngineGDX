package ru.jengine.jenginegdx.ui.system;

import com.badlogic.gdx.utils.ByteArray;
import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.Map;

public class ImageLogic {
    private final Coordinates coordinates;
    private final Map <String, Object> content;
    private byte[] img;

    public ImageLogic(Coordinates coordinates, Map <String, Object> content) {
        this.coordinates = coordinates;
        this.content = content;
    }

    public void resize(int newX, int newY) {
        this.coordinates.resize(newX, newY);
    }

    public void doSomethingWithImg() {

    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Map <String, Object> getContent() {
        return content;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
