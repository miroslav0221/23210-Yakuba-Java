package org.example.ThreadPool;

import org.example.CarParts.*;
import org.example.Storage.Storage;

public class Task {
    private final Storage<Body> bodyStorage;
    private final Storage<Motor> motorStorage;
    private final Storage<Accesory> accessoryStorage;
    private final Storage<Car> carsStorage;

    public Task(Storage<Body> bodyStorage, Storage<Motor> motorStorage,
                Storage<Accesory> accessoryStorage, Storage<Car> carsStorage) {
        this.bodyStorage = bodyStorage;
        this.motorStorage = motorStorage;
        this.accessoryStorage = accessoryStorage;
        this.carsStorage = carsStorage;
    }

    public void doTask(){
        Body body = bodyStorage.take();
        Motor engine = motorStorage.take();
        Accesory accessory = accessoryStorage.take();
        Car car = new Car(body, engine, accessory);
        carsStorage.put(car);
    }
}