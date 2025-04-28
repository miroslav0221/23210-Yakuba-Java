package org.example.CarParts;

public class Accesory implements CarPart {
    private final int id;
    public static Integer idGenerator = 0;
    public Accesory() {
        id = idGenerator;
        idGenerator++;
    }

    @Override
    public int getId() {
        return id;
    }
}
