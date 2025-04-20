package org.example.Staff;

import org.example.CarParts.Car;
import org.example.Storage.Storage;

public class Dealer extends Thread{
    private final Storage<Car> storage;
    private int delay;
    private final int id;
    private boolean isWorking;
    public Dealer(Storage<Car> storage, int id){
        this.storage = storage;
        this.id = id;
    }

    @Override
    public void run(){
        isWorking = true;
        double startTime = (double) System.currentTimeMillis() / 1000;
        try {
            while(!Thread.currentThread().isInterrupted() && isWorking){
                Car auto = storage.take();
                double curTime = (double) System.currentTimeMillis() / 1000 - startTime;
                System.out.println(curTime + " Dealer: " + id + " " + auto.getId());
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
