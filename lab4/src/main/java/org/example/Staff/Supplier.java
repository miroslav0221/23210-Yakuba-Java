package org.example.Staff;

import org.example.CarParts.CarPart;
import org.example.Storage.Storage;

public class Supplier<T extends CarPart> extends Thread{
    private final Storage<T> storage;
    private int delay;
    private java.util.function.Supplier<T> supplier;
    private boolean isWorking;

    public Supplier(Storage<T> storage, java.util.function.Supplier<T> supplier){
        this.storage = storage;
        this.supplier = supplier;
    }

    @Override
    public void run(){
        this.isWorking = true;
        try {
            while (!Thread.currentThread().isInterrupted() && isWorking) {
                T item = supplier.get();
                storage.put(item);
                Thread.sleep(delay);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stopWorking(){
        isWorking = false;
    }
    public void setDelay(int delay){
        this.delay = delay;
    }

}
