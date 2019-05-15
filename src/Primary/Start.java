/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import Controlo.Butao_finalizar;
import Edit_Prof.Chromosoma;
import Edit_Prof.Jenetic_L;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import luigi.MarioUtils;
import luigi.Request;
import luigi.RunResult;

/**
 *
 * @author tronc
 */
public class Start {


    public Start() throws CloneNotSupportedException, CloneNotSupportedException, IOException {
        //Integer[] solution, String level, String render, String mode

        Butao_finalizar b = new Butao_finalizar();
        MarioUtils m = new MarioUtils("192.168.1.2");
        Integer[] chances = new Integer[]{100,20,10,10};

        Jenetic_L[] lista = new Jenetic_L[8];
        int l = 0;
        for (int i = 1; i < 3; i++) {
            for (int y = 1; y < 5; y++) {
                if(!b.isFinalizar()){
                    //int world, int stage, String fileF, String fileS, MarioUtils m
                    lista[l] = new Jenetic_L(i, y, chances[y], "files/Level/Failures/Level_" + i + "_" + y + ".csv", "files/Level/Success/Level_" + i + "_" + y + ".csv", m);
                    b.setWorld(lista[l].getWorld());
                    b.setStage(lista[l].getStage());
                    lista[l].run(b);
                    l++;
                }
            }
        }
        b.setVisible(false);
        b.setDefaultCloseOperation(0);

        /**
        for (int i = 0; i < 8; i++) {
            b.setWorld(lista[i].getWorld());
            b.setStage(lista[i].getStage());
            try {
                lista[i].run(b);
            } catch (IOException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
            lista[i].lastSave();
            if (!lista[i].getLista_s().isEmpty()) {
                Chromosoma c = lista[i].getLista_s().get(0);
                Request req = new Request(c.comandsGameSize(1), "SuperMarioBros-" + lista[i].getWorld() + "-" + lista[i].getStage() + "-v0", "false", "level");
                RunResult r = m.goMarioGo(req);

                //m.submitToLeaderboard(r, "Let's a go!", "forever");
            } else {
                if (!lista[i].getLista_f().isEmpty()) {
                    Chromosoma c = lista[i].getLista_f().get(0);
                    Request req = new Request(c.comandsGameSize(5), "SuperMarioBros-" + lista[i].getWorld() + "-" + lista[i].getStage() + "-v0", "false", "level");
                    RunResult r = m.goMarioGo(req);

                    //m.submitToLeaderboard(r, "Let's a go!", "level");
                }
            }
        }

        b.setVisible(false);
        b.setDefaultCloseOperation(0);**/
    }

    

    public void exece() {

        //Request req = new Request(c.comandsGameSize(1), this.level, "true", this.type);
        //RunResult r = m.goMarioGo(req);
        //int comand_per_secon = 20;
        //System.out.println("Comands used" + r.getCommands_used() + "\nDistance: " + r.getX_pos() + "\nScore: " + r.getScore() + "\nReward: " + r.getReward() + "\nComands per secon: " + comand_per_secon + "\nTime: " + r.getTime_left() + "\nStop why?: " + r.getReason_finish() +"\n\n");
        //Integer[] intg = new Integer[]{0};
        //c.setValues(intg);
        //req = new Request(c.getValues(), this.level, "false", this.type);
        //r = m.goMarioGo(req);
        //System.out.println("Distance: " + r.getX_pos() + "\nScore: " + r.getScore() + "\nReward: " + r.getReward() + "\nComands per secon: " + comand_per_secon + "\nTime: " + r.getTime_left() + "\n\n");
        /**
         * Comands cs = new Comands(); Integer[] intgs = new Integer[]{0};
         * cs.setValues(intgs); req = new Request(cs.getValues(), this.level,
         * "false", this.type); r = m.goMarioGo(req);
         *
         * for(; r.getTime_left() > 399 ; ){ cs.incressSize(1); req = new
         * Request(cs.getValues(), this.level, "false", this.type); r =
         * m.goMarioGo(req); } comand_per_secon = cs.getValues().length;
         * System.out.println("Distance: " + r.getX_pos() + "\nScore: " +
         * r.getScore() + "\nReward: " + r.getReward() + "\nComands per secon: "
         * + comand_per_secon + "\nTime: " + r.getTime_left() + "\n\n");
         *
         * for(; r.getTime_left() > 398 ; ){ cs.incressSize(1); req = new
         * Request(cs.getValues(), this.level, "false", this.type); r =
         * m.goMarioGo(req); } comand_per_secon = cs.getValues().length/2;
         * System.out.println("Distance: " + r.getX_pos() + "\nScore: " +
         * r.getScore() + "\nReward: " + r.getReward() + "\nComands per secon: "
         * + comand_per_secon + "\nTime: " + r.getTime_left() + "\n\n");
         *
         * for(; r.getTime_left() > 397 ; ){ cs.incressSize(1); req = new
         * Request(cs.getValues(), this.level, "false", this.type); r =
         * m.goMarioGo(req); } comand_per_secon = cs.getValues().length / 3;
         * System.out.println("Distance: " + r.getX_pos() + "\nScore: " +
         * r.getScore() + "\nReward: " + r.getReward() + "\nComands per secon: "
         * + comand_per_secon + "\nTime: " + r.getTime_left() + "\n\n");
         *
         */
        /**
         * Integer[] intg2 = new
         * Integer[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1};
         * c.setValues(intg2); int distance = 0; Request req = new
         * Request(c.comandsGameSize(5), this.level, "true", this.type);
         * RunResult r = m.goMarioGo(req); System.out.println(r.toString());
         *
         * /**
         * for (int i = 0; i <= 99; i++) {
         * req = new Request(c.comandsGameSize(5), this.level, "true", this.type);
         * r = m.goMarioGo(req);
         * System.out.println(r.toString());
         *
         * if (distance >= r.getX_pos() && i!=0) {
         * c.mutate(50,c.getGenes().size() - 40, c.getGenes().size());
         *
         * } else if(r.getX_pos() - distance < distance_max/4) {
         * c.mutate(50,c.getGenes().size() - 40, c.getGenes().size());
         *
         * } else if(!r.getReason_finish().equals("no_more_commands")){
         * c.mutate(50,c.getGenes().size()- 40, c.getGenes().size()); } else{
         * c.incresseSizeGenes(40); distance = r.getX_pos(); }
         *
         *
         *
         *
         * }*
         */
        //System.out.println(r.getScore() + "\n" + r.getX_pos() + "\n" + r.getReason_finish());
        //System.out.println(r.toString());
        //current_comands.add_data(r);
    }

}
