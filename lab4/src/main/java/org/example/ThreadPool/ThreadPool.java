package org.example.ThreadPool;


import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadPool {
    private final Queue<Task> tasks;
    private final ArrayList<WorkerThread> workers;
    private final int countWorkers;
    private int delay;

    public ThreadPool(int countWorkers, int delay) {
        this.tasks = new ConcurrentLinkedDeque<>();
        this.workers = new ArrayList<>();
        this.countWorkers = countWorkers;
        this.delay = delay;

        for(int i = 0; i < countWorkers; ++i){
            WorkerThread workerThread = new WorkerThread();
            workers.add(workerThread);
            workerThread.start();
        }
    }

    public void addTask(Task task){
        synchronized (tasks) {
            tasks.add(task);
            tasks.notifyAll();
        }

    }

    public void stopWorking() {
        for(WorkerThread workerThread : workers) {
            workerThread.stopWorking();
        }

    }

    public int getTasksSize(){
        return tasks.size();
    }

    public void setDelay(int delay){
        this.delay = delay;
    }

    private class WorkerThread extends Thread {
        private boolean isWorking;

        @Override
        public void run(){
            isWorking = true;
            Task task;
            try {
                while(!Thread.currentThread().isInterrupted() && isWorking){

                    synchronized (tasks) {
                        while(tasks.isEmpty()){
                            tasks.wait();
                        }
                        task = tasks.poll();
                        // ??? notifyAll();
                    }

                    if (task != null) {
                        task.doTask();
                    }


                    Thread.sleep(delay);
                }
            }
            catch (InterruptedException e){
                System.err.println(e.getLocalizedMessage());
                Thread.currentThread().interrupt();

            }

        }

        public void stopWorking(){
            isWorking = false;
        }
    }
}