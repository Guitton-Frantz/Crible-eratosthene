package com.crible;

public class Main {
    public static void main(String[] args) {
        // BlockingQueue<Integer> buffer = new LinkedBlockingQueue<Integer>();
        MoniteurProdCons buffer = new MoniteurProdCons();
        Thread bufferFiller = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 2; i < 10000; i++) {
                    buffer.prod(i);
                }
            }
        });
        bufferFiller.start();

        Thread t1 = new Thread(new Crible(2, buffer));
        t1.start();
    }
}