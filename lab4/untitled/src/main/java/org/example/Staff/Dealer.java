package org.example.Staff;

import org.example.CarParts.Car;
import org.example.Storage.Storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Dealer extends Thread{
    private final Storage<Car> storage;
    private int delay;
    private final int id;
    private boolean isWorking;
    private boolean log;
    private BufferedWriter writer;


    public Dealer(Storage<Car> storage, int id, boolean log) {
        this.storage = storage;
        this.id = id;
        this.log = log;
        if (log) {
            writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("log.log", true));
            }
            catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void run(){
        isWorking = true;
        double startTime = (double) System.currentTimeMillis() / 1000;
        try {
            while(!Thread.currentThread().isInterrupted() && isWorking){
                Car car = storage.take();
                double curTime = (double)System.currentTimeMillis() / 1000 - startTime;
                double roundNumberTime = Math.round(curTime*100)/100.0;
//                System.out.println(roundNumberTime + "sec | Dealer: " + id + " " + car.getId());
//                System.out.println("Count cars all time: " + storage.getCountItemAllTime());
//                System.out.println("-------------------------");
                String logs = roundNumberTime + ":" + " Dealer " + id + ": Auto " + car.getId() +
                        "(Body: " + car.getBody().getId() + ", Motor: " + car.getMotor().getId() +
                        ", Accessory: " + car.getAccesory().getId() + ")";

                if(log) {
                    try {
                        System.out.println(logs);
                        writer.write(logs);
                        writer.newLine();
                        writer.flush();
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }

                }
                Thread.sleep(delay);
            }
        }
        catch (InterruptedException e) {
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
