package com.crible;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * Class representing a thread that will compute the eratosthenes sieve algorithm
 * It will be used to compute the sieve in parallel
 * 
 */
public class Crible implements Runnable {
    /*
     * The sieve that will be computed it is given by the previous thread
     */
    private int sieve;

    /*
     * Buffer that stores the value transmitted to the next thread
     */
    // private BlockingQueue<Integer> bufferNext;

    /*
     * Buffer that stores the value transmitted by the previous thread
     */
    // private BlockingQueue<Integer> bufferPrevious;

    private MoniteurProdCons moniteurPrevious;
    private MoniteurProdCons moniteurNext;

    /*
     * The constructor of the class
     * 
     * @param sieve the sieve that will be computed
     * 
     * @param next the next thread that will be used to compute the next sieve
     */
    public Crible(int sieve, MoniteurProdCons moniteurPrevious) {
        this.sieve = sieve;
        // this.bufferPrevious = bufferPrevious;
        // this.bufferNext = new LinkedBlockingQueue<Integer>();
        this.moniteurPrevious = moniteurPrevious;
        System.out.println(sieve);

    }

    /*
     * Compute the sieve using the buffer from the previous thread
     * When a value is received, it is either a prime number or a multiple of the
     * sieve
     * If it is a prime number, it is sent to the next thread
     */
    private void compute() {
        while (true) {
            // int value = this.bufferPrevious.take();
            int value = this.moniteurPrevious.cons();
            if (value % this.sieve != 0) {
                if (moniteurNext == null) {
                    moniteurNext = new MoniteurProdCons();

                    Thread t = new Thread(new Crible(value, this.moniteurNext));
                    t.start();
                }
                this.moniteurNext.prod(value);

            }
            // this.bufferNext.put(value);
        }
    }

    @Override
    public void run() {
        this.compute();
    }
}
