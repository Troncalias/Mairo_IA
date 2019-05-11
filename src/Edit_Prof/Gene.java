/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author tronc
 */
public class Gene {

    private int x;

    public Gene(int x) {
        this.x = x;
    }

    public Gene() {
        int y = ThreadLocalRandom.current().nextInt(1, 120);
        if (y <= 20) {
            x = 1;
        } else if (y <= 35) {
            x = 2;
        } else if (y <= 45) {
            x = 3;
        } else if (y <= 55) {
            x = 4;
        } else if (y <= 70) {
            x = 5;
        } else if (y <= 80) {
            x = 6;
        } else if (y <= 90) {
            x = 7;
        } else if (y <= 95) {
            x = 8;
        } else if (y <= 105) {
            x = 9;
        } else if (y <= 110) {
            x = 10;
        } else if (y <= 115) {
            x = 11;
        } else {
            x = 0;
        }
    }

    public int newRandom() {
        int j = x + 0;
        while (x == j) {
            int y = ThreadLocalRandom.current().nextInt(1, 120);
            if (y <= 20) {
                this.setX(1);
            } else if (y <= 35) {
                this.setX(2);
            } else if (y <= 45) {
                this.setX(3);
            } else if (y <= 55) {
                this.setX(4);
            } else if (y <= 70) {
                this.setX(5);
            } else if (y <= 80) {
                this.setX(6);
            } else if (y <= 90) {
                this.setX(7);
            } else if (y <= 95) {
                this.setX(8);
            } else if (y <= 105) {
                this.setX(9);
            } else if (y <= 110) {
                this.setX(10);
            } else if (y <= 115) {
                this.setX(11);
            } else {
                this.setX(0);
            }
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
        }
    }

    public boolean isValid() {
        return this.getX() <= 11 && this.getX() >= 0;
    }
}
