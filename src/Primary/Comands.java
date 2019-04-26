/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import java.util.Arrays;
import luigi.RunResult;

/**
 *
 * @author tronc
 */
public class Comands {
    private Integer[] commands;
    private int score;
    private int time;
    private int x_position;
    private int world = 0;
    private String reason_stop;
        
    public Comands(Integer[] commands) {this.commands = commands;}
    
    public Comands(Integer[] commands, int world) {this.commands = commands; this.world = world;}

    public Integer[] getCommands() {return commands;}
    
    public Integer getCommand(int i) {return commands[i];}

    public void setCommands(Integer[] commands) {this.commands = commands;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public int getTime() {return time;}
    
    public void setTime(int time) {this.time = time;}

    public int getX_position() {return x_position;}

    public void setX_position(int x_position) {this.x_position = x_position;}

    public int getWorld() {return world;}

    public void setWorld(int world) {this.world = world;}

    public String getReason_stop() {return reason_stop;}

    public void setReason_stop(String reason_stop) {this.reason_stop = reason_stop;}
    
    public void add_data(RunResult r){
        this.score = r.getScore();
        this.time = r.getTime_left();
        this.x_position = r.getX_pos();
        this.reason_stop = r.getReason_finish();
    }
    
    public String valeos_saved(){
        StringBuilder s = new StringBuilder();
        s.append(this.getScore()).append(";").append(this.getTime()).append(";").append(this.getX_position()).append(";").append(this.getReason_stop()).append(";").append(Arrays.toString(this.getCommands()));
        
        String v = s.toString();
        return v;
    }
    
}
