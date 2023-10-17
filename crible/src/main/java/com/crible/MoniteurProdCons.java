package com.crible;

public class MoniteurProdCons {
    int tampon;
    boolean estVide = true;

    synchronized void prod(int m) {
        if (!estVide) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        tampon = m;
        estVide = false;
        notify();
    }

    synchronized int cons() {
        if (estVide) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        int result = tampon;
        estVide = true;
        notify();
        return result;
    }

}
