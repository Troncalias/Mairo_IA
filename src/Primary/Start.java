/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import java.io.IOException;
import luigi.MarioUtils;
import luigi.Request;
import luigi.RunResult;

/**
 *
 * @author tronc
 */
public class Start {

    private final String type;
    private final String level;
    private final String file_saved;
    private Game_Info[] previust_comands = new Game_Info[1];
    private Game_Info current_comands;

    public Start(String s) {
        if (s.equals("f")) {
            level = "SuperMarioBros-v0";
            type = "forever";
            file_saved = "commands_forever.csv";
        } else {
            level = "SuperMarioBros-1-1-v0";
            type = "level";
            file_saved = "commands_level.csv";
        }
    }

    public void start() throws IOException {
        //Integer[] solution, String level, String render, String mode
        MarioUtils m = new MarioUtils("192.168.1.5");

        Comands c = new Comands();
        Integer[] intg = new Integer[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        System.out.println(intg.length);
        c.setValues(intg);
        Request req = new Request(c.getValues(), this.level, "false", this.type);
        RunResult r = m.goMarioGo(req);
        int distance_max = r.getX_pos();
        System.out.println(distance_max);
        
        Integer[] intg2 = new Integer[0];
        c.setValues(intg2);
        c.incressSize(200);
        int distance = 0;

        for (int i = 0; 99 >= i; i++) {
            req = new Request(c.getValues(), this.level, "true", this.type);
            r = m.goMarioGo(req);
            System.out.println(r.toString());
            
            if (distance >= r.getX_pos() && i!=0) {
                c.cahngeMoves(c.getValues().length - 200, c.getValues().length);
                
            } else if(r.getX_pos() - distance < distance_max/4) {
                c.cahngeMoves(c.getValues().length - 200, c.getValues().length);
                
            } else if(!r.getReason_finish().equals("no_more_commands")){
                c.cahngeMoves(c.getValues().length - 200, c.getValues().length);
            } else{
                c.incressSize(200);
                distance = r.getX_pos();
            }
            
            
            

        }
        //System.out.println(r.getScore() + "\n" + r.getX_pos() + "\n" + r.getReason_finish());
        System.out.println(r.toString());
        //current_comands.add_data(r);

        //m.submitToLeaderboard(r, "Let's a go!", "forever");
    }

}
