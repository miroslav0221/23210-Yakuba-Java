package org.example.CarParts;


public class Car {
    private final int id;
    private final Body body;
    private final Accesory accesory;
    private final Motor motor;
    private static Integer idGenerator = 0;

    public Car(Body body, Motor motor, Accesory accesory) {
        this.body = body;
        this.accesory = accesory;
        this.motor = motor;
        id = idGenerator;
        idGenerator++;
    }

    public int getId() {
        System.out.println("Car " + String.valueOf(id) + ": body " + String.valueOf(body.getId()
                + ", motor " + String.valueOf(motor.getId()) + ", accesory " + String.valueOf(accesory.getId())));
        return id;
    }



}
