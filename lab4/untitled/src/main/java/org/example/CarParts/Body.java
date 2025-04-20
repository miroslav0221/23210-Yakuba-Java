package org.example.CarParts;

public class Body implements CarPart {
    private final int id;
    private static Integer idGenerator = 0;

    public Body() {
        id = idGenerator;
        idGenerator++;
    }

    @Override
    public int getId() {
        return id;
    }
}