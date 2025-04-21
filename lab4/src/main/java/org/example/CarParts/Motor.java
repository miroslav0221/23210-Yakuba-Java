package org.example.CarParts;


public class Motor implements CarPart {
    private final int id;
    private static int idGenerator = 0;

    public Motor() {
        id = idGenerator;
        idGenerator++;
    }

    @Override
    public int getId() {
        return id;
    }
}
