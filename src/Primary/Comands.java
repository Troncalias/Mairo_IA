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
public class Comands {

    private Integer[] values;

    public Comands(Integer[] values) {
        this.values = values;
    }

    public Comands() {
        this.values = new Integer[0];
    }

    public Integer[] getValues() {
        return values;
    }

    public void setValues(Integer[] values) {
        this.values = values;
    }

    public void incressSize(int quant) {
        Integer[] val = new Integer[this.values.length + quant];

        System.arraycopy(values, 0, val, 0, this.values.length);

        int t = 0;
        int curent = 0;
        for (int i = this.values.length; i < val.length; i++) {
            if (t == 0) {
                int y = ThreadLocalRandom.current().nextInt(1, 120);
                if (y <= 10) {
                    val[i] = 1;
                } else if (y <= 35) {
                    val[i] = 2;
                } else if (y <= 45) {
                    val[i] = 3;
                } else if (y <= 55) {
                    val[i] = 4;
                } else if (y <= 70) {
                    val[i] = 5;
                } else if (y <= 80) {
                    val[i] = 6;
                } else if (y <= 90) {
                    val[i] = 7;
                } else if (y <= 95) {
                    val[i] = 8;
                } else if (y <= 105) {
                    val[i] = 9;
                } else if (y <= 110) {
                    val[i] = 10;
                } else if (y <= 115) {
                    val[i] = 11;
                } else {
                    val[i] = 0;
                }
                curent = val[i];
                t = 5;
            } else {
                t--;
                val[i] = curent;
            }
        }

        this.values = val;
    }

    public void cahngeMoves(int inic, int last) {
        if (inic < last) {
            int t = 0;
            int curent = 0;
            for (int i = inic; i < last; i++) {
                int a = this.values[i];
                if (t == 0) {
                    while (a == this.values[i]) {
                        int y = ThreadLocalRandom.current().nextInt(1, 120);
                        if (y <= 20) {
                            a = 1;
                        } else if (y <= 35) {
                            a = 2;
                        } else if (y <= 45) {
                            a = 3;
                        } else if (y <= 55) {
                            a = 4;
                        } else if (y <= 70) {
                            a = 5;
                        } else if (y <= 80) {
                            a = 6;
                        } else if (y <= 90) {
                            a = 7;
                        } else if (y <= 95) {
                            a = 8;
                        } else if (y <= 105) {
                            a = 9;
                        } else if (y <= 110) {
                            a = 10;
                        } else if (y <= 115) {
                            a = 11;
                        } else {
                            a = 0;
                        }
                    }
                    this.values[i] = a;
                } else {
                    t--;
                    this.values[i] = curent;
                }

            }
        }
    }
}
