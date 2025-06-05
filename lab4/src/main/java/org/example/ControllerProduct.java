package org.example;
import org.example.ThreadPool.*;
import org.example.CarParts.*;
import org.example.Storage.Storage;

public class ControllerProduct extends Thread {
    private ThreadPool workers;
    private final Storage<Body> storageBody;
    private final Storage<Accesory> storageAcces;
    private final Storage<Motor> storageMotor;
    private final Storage<Car> storageCar;
    private boolean isWorking;

    public ControllerProduct(ThreadPool workers, Storage<Body> storageBody,
                             Storage<Accesory> storageAcces, Storage<Motor> storageMotor,
                             Storage<Car> storageCar)
    {
        this.storageCar = storageCar;
        this.storageAcces = storageAcces;
        this.storageMotor = storageMotor;
        this.storageBody = storageBody;
        this.workers = workers;
    }

    @Override
    public void run() {
        isWorking = true;
        while (isWorking && !Thread.currentThread().isInterrupted()) {
            synchronized (storageCar) {
                while (storageCar.getSize() - storageCar.getCapacity() < workers.getTasksSize()) {
                    try {
                        storageCar.wait();
                    } catch (Exception e) {
                        System.err.println(e.getLocalizedMessage());
                    }

                }

                int countFreePlace = storageCar.getSize() - storageCar.getCapacity() - workers.getTasksSize();

                for (int i = 0; i < countFreePlace; i++) {
                    Task task = new Task(storageBody, storageMotor, storageAcces,
                            storageCar);
                    workers.addTask(task);
                }
                storageCar.notifyAll();
            }
        }
    }

    public void stopWorking() {
        isWorking = false;
    }
}

