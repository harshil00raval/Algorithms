//import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class HarshilThreadPoolExecutor {
    private BlockingQueue<Runnable> taskQ = null;
    private List<HarshilThread> threads = new ArrayList<>();
    private boolean stop = false;
    private CountDownLatch l = null;

    public HarshilThreadPoolExecutor(int nthreads, int ntasks) throws InterruptedException {
        taskQ = new ArrayBlockingQueue<Runnable>(ntasks);
        l = new CountDownLatch(ntasks);
        for(int i=0;i<nthreads;i++){
            threads.add(new HarshilThread(taskQ,l));
        }
        for(HarshilThread t:threads){
            t.start();
        }

    }

    public void closeHarshilThreadPool(){
        try {
            l.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.stop = true;
        for(HarshilThread t:this.threads){
            t.stopThread();
        }
    }

    public synchronized void execute(Runnable r){
        if(this.stop)
            throw new IllegalStateException("Harshil's Threadpool Has Stopped");
        this.taskQ.offer(r);
    }
}

class HarshilThread extends Thread{
    private BlockingQueue<Runnable> taskQ = null;
    private boolean stop = false;
    private CountDownLatch l = null;
    public HarshilThread(BlockingQueue<Runnable> taskQ,CountDownLatch l){
        this.taskQ = taskQ;
        this.l = l;
    }
    public void run(){
        while(!stop){
            Runnable r = (Runnable) this.taskQ.poll();
            if(r!= null) {
                r.run();
                l.countDown();
            }
        }
    }
    public void stopThread(){
        this.stop = true;
    }
}

class TestHarshilThread{
    public static void main(String args[]){
        HarshilThreadPoolExecutor htpe = null;
        try {
            htpe = new HarshilThreadPoolExecutor(4,10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HarshilRunnable hr1 = new HarshilRunnable(1,2);
        HarshilRunnable hr2 = new HarshilRunnable(2,2);
        HarshilRunnable hr3 = new HarshilRunnable(3,2);
        HarshilRunnable hr4 = new HarshilRunnable(4,2);
        HarshilRunnable hr5 = new HarshilRunnable(5,2);
        HarshilRunnable hr6 = new HarshilRunnable(6,2);
        HarshilRunnable hr7 = new HarshilRunnable(7,2);
        HarshilRunnable hr8 = new HarshilRunnable(8,2);
        HarshilRunnable hr9 = new HarshilRunnable(9,2);
        HarshilRunnable hr0 = new HarshilRunnable(10,2);

        htpe.execute(hr0);
        htpe.execute(hr1);
        htpe.execute(hr2);
        htpe.execute(hr3);
        htpe.execute(hr4);
        htpe.execute(hr5);
        htpe.execute(hr6);
        htpe.execute(hr7);
        htpe.execute(hr8);
        htpe.execute(hr9);

        htpe.closeHarshilThreadPool();

    }
}

class HarshilRunnable implements Runnable{
    int a;
    int b;
    public HarshilRunnable(int a,int b){
        this.a=a;
        this.b = b;
    }
    public void run(){
        System.out.println("Thread"+ Thread.currentThread().getName() +" : "+(this.a+this.b));
    }
}
