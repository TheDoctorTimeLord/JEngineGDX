package ru.jengine.jenginegdx.logic;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.List;

public class ImageLogic extends LogicObject{
    private byte[] img;

    public ImageLogic(String name, Coordinates coordinates, List <LogicObject> content) {
        super(name, coordinates,content);
    }

    public void resize(int newX, int newY) {
        this.coordinates.resize(newX, newY);
    }

    public void doSomethingWithImg() {

    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
