/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import Enums.Levels;
import Enums.Tipos;

/**
 *
 * @author tronc
 */
public class Comands_Executed {
    private Comands[] executed;
    private Tipos tipo;
    private Levels Level;

    public Comands_Executed(Comands[] executed, Levels Level) {
        this.tipo = Tipos.forever;
        this.Level = Level;
        this.executed = new Comands[0];
        for (Comands executed1 : executed) {
            if (executed1 != null) {
                this.addComand(executed1);
            }
        }
    }

    public Comands_Executed(Comands[] executed) {
        this.tipo = Tipos.level;
        this.executed = new Comands[0];
        for (Comands executed1 : executed) {
            if (executed1 != null) {
                this.addComand(executed1);
            }
        }
    }

    public Comands_Executed(Levels Level) {
        this.tipo = Tipos.level;
        this.Level = Level;
        this.executed = new Comands[0];
    }

    public Comands_Executed() {
        this.tipo = Tipos.level;
        this.executed = new Comands[0];
    }

    
    

    public Comands[] getExecuted() {
        return executed;
    }

    public void setExecuted(Comands[] executed) {
        this.executed = executed;
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public Levels getLevel() {
        return Level;
    }

    public void setLevel(Levels Level) {
        this.Level = Level;
    }

    public int getQuantity() {
        return this.executed.length;
    }
    
    public void addComand(Comands c){
        this.increassComandsMax();
        this.executed[this.executed.length-1] = c;
    }
    
    public void increassComandsMax(){
        Comands[] curents = new Comands[this.getQuantity() + 1];
        System.arraycopy(this.executed, 0, curents, 0, this.getQuantity());
        this.executed = curents;
    }
}
