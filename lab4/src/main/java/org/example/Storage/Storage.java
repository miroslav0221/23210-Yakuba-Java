package org.example.Storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Storage<T> {
    private int sizeStorage;
    private int capacityStorage;
    private Queue<T> storage;
    private int countItemAllTime;

    public Storage(int sizeStorage_) {
        countItemAllTime = 0;
        storage = new LinkedList<>();
        sizeStorage = sizeStorage_;
        capacityStorage = 0;
    }

    public synchronized T take() {
        while(storage.isEmpty()) {
            try {
                wait();
            }
            catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
                Thread.currentThread().interrupt();
            }
        }
        T element = storage.poll();
        capacityStorage--;
        notifyAll();
        return element;
    }

    public synchronized void put(T objectStorage)  {
        while (capacityStorage >= sizeStorage) {
            try {
                wait();
            }
            catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
                Thread.currentThread().interrupt();
            }
        }
        storage.add(objectStorage);
        countItemAllTime++;
        capacityStorage++;
        notifyAll();
    }


    public int getCapacity() {
        return capacityStorage;
    }
    public int getSize() {
        return sizeStorage;
    }

    public int getCountItemAllTime() {
        return countItemAllTime;
    }
}

















































