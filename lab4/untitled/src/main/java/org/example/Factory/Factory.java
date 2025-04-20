package org.example.Factory;

import org.example.CarParts.Accesory;
import org.example.CarParts.Body;
import org.example.CarParts.Car;
import org.example.CarParts.Motor;
import org.example.ControllerProduct;
import org.example.Staff.Dealer;
import org.example.Staff.Supplier;
import org.example.Storage.Storage;
import org.example.ThreadPool.ThreadPool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class Factory {

    private Properties properties;
    private ArrayList<Dealer> dealers;
    private ControllerProduct controllerProduct;
    private ArrayList<Supplier<Accesory>> suppliersAcces;
    private ArrayList<Supplier<Body>> suppliersBody;
    private ArrayList<Supplier<Motor>> suppliersMotor;
    private ThreadPool workers;
    private Storage<Body> storageBody;
    private Storage<Motor> storageMotor;
    private Storage<Accesory> storageAccesory;
    private Storage<Car> storageCar;
    private final int timeWait;

    private void createDealersArray() {
        dealers = new ArrayList<>();
    }

    private void createWorkersArray() {
        workers = new ThreadPool(Integer.parseInt(properties.getProperty("Workers")), timeWait);
    }

    private void createStorages() {
        storageBody = new Storage<>(Integer.parseInt(properties.getProperty("BodyStorageSize")));
        storageAccesory = new Storage<>(Integer.parseInt(properties.getProperty("AccessoryStorageSize")));
        storageMotor = new Storage<>(Integer.parseInt(properties.getProperty("MotorStorageSize")));
        storageCar = new Storage<>(Integer.parseInt(properties.getProperty("CarStorageSize")));
    }

    private void createSuppliersArray() {
        suppliersAcces = new ArrayList<>();
        suppliersBody = new ArrayList<>();
        suppliersMotor = new ArrayList<>();
    }



    public Factory(String config) {
        properties = new Properties();
        System.out.println(config);
        timeWait = 1000;
        try {
            File file = new File(config);
            properties.load(new FileReader(file));
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        createDealersArray();
        createSuppliersArray();
        createWorkersArray();
        createStorages();
        initDealers();
        initSuppliersBody();
        initSuppliersAccesory();
        initSuppliersMotor();
        controllerProduct = new ControllerProduct(workers, storageBody,
                storageAccesory, storageMotor, storageCar);
        startWorking();
    }

    private void initDealers() {
        int countDealers = Integer.parseInt(properties.getProperty("Dealers"));
        for (int i = 0; i < countDealers; i++) {
            Dealer dealer = new Dealer(storageCar, i);
            dealer.setDelay(timeWait);
            dealers.add(dealer);
        }
    }

    private void initSuppliersBody() {
        int countSuppliersBody = Integer.parseInt(properties.getProperty("BodySuppliers"));
        for (int i = 0; i < countSuppliersBody; i++) {
            Supplier<Body> supplier = new Supplier<>(storageBody, Body::new);
            supplier.setDelay(timeWait);
            suppliersBody.add(supplier);
        }
    }

    private void initSuppliersAccesory() {
        int countSuppliersAcces = Integer.parseInt(properties.getProperty("AccessorySuppliers"));
        for (int i = 0; i < countSuppliersAcces; i++) {
            Supplier<Accesory> supplier = new Supplier<>(storageAccesory, Accesory::new);
            supplier.setDelay(timeWait);
            suppliersAcces.add(supplier);
        }
    }

    private void initSuppliersMotor() {
        int countSuppliersMotor = Integer.parseInt(properties.getProperty("MotorSuppliers"));
        for (int i = 0; i < countSuppliersMotor; i++) {
            Supplier<Motor> supplier = new Supplier<>(storageMotor, Motor::new);
            supplier.setDelay(timeWait);
            suppliersMotor.add(supplier);
        }
    }


    public void startWorking() {
        for (Supplier<Body> supplier : suppliersBody) {
            supplier.start();
        }
        for (Supplier<Accesory> supplier : suppliersAcces) {
            supplier.start();
        }
        for (Supplier<Motor> supplier : suppliersMotor) {
            supplier.start();
        }
        controllerProduct.start();
    }

    public void stopWorking() {
        for (Supplier<Body> supplier : suppliersBody) {
            supplier.stopWorking();
        }
        for (Supplier<Accesory> supplier : suppliersAcces) {
            supplier.stopWorking();
        }
        for (Supplier<Motor> supplier : suppliersMotor) {
            supplier.stopWorking();
        }
        controllerProduct.stopWorking();
        workers.stopWorking();
    }



    public int getBodyStorageSize() {
        return storageBody.getCapacity();
    }
    public int getMotorStorageSize() {
        return storageMotor.getCapacity();
    }
    public int getAccesoryStorageSize() {
        return storageAccesory.getCapacity();
    }
    public int getCarStorageCapacity() {
        return storageCar.getCapacity();
    }

    public void setTimeWaitDealers(int time) {
        for (Dealer dealer : dealers) {
            dealer.setDelay(time);
        }
    }

    public void setTimeWaitSuppliersBody(int time) {
        for (Supplier<Body> supplier : suppliersBody) {
            supplier.setDelay(time);
        }
    }

    public void setTimeWaitSuppliersMotor(int time) {
        for (Supplier<Motor> supplier : suppliersMotor) {
            supplier.setDelay(time);
        }
    }

    public void setTimeWaitSuppliersAcces(int time) {
        for (Supplier<Accesory> supplier : suppliersAcces) {
            supplier.setDelay(time);
        }
    }

    public void setTimeWaitWorkers(int time) {
        workers.setDelay(time);
    }

}
