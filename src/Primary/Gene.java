/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author tronc
 */
public class Gene {

    Integer[] array = new Integer[]{11, 10, 9, 8, 7, 6, 5, 5, 5, 5, 5, 4, 4, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0};
    private int x;

    public Gene(int x) {
        this.x = x;
    }

    public Gene() {
        int y = ThreadLocalRandom.current().nextInt(0, array.length);
        this.x = array[y];

    }

    public int newRandom() {
        int j = x + 0;
        while (x == j) {
            int y = ThreadLocalRandom.current().nextInt(0, array.length);
            this.x = array[y];
        }

        return this.x;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Gene(this.x);
    }

    @Override
    public String toString() {
        return x + "";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x >= 0 && x <= 11) {
            this.x = x;
        } else {
            this.x = x;
            this.newRandom();
        }
    }

    public boolean isValid() {
        return this.getX() <= 11 && this.getX() >= 0;
    }
}
