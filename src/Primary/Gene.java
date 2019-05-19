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

    Integer[] array = new Integer[]{0, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 5, 6, 7, 8, 9, 10, 10, 11};
    private int x;

    public Gene(int x) {
        this.x = x;
    }

    public Gene() {
        int y = ThreadLocalRandom.current().nextInt(0, array.length - 1);
        this.x = array[y];

    }

    public int newRandom() {
        int j = x + 0;
        while (x == j) {
            int y = ThreadLocalRandom.current().nextInt(0, array.length - 1);
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
