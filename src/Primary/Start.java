/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    private Comands[] previust_comands = new Comands[10];
    private Comands[] current_comands = new Comands[10];

    private BufferedWriter out;

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
        this.access_file();
    }

    public void start() throws IOException {
        this.load();
        //Integer[] solution, String level, String render, String mode

        for (int i = 0; i < this.previust_comands.length; i++) {
            Request req = new Request(this.previust_comands[i].getCommands(), this.level, "false", this.type);
            MarioUtils m = new MarioUtils("192.168.1.6");
            RunResult r = m.goMarioGo(req);
            System.out.println(r.getScore() + "\n" + r.getX_pos() + "\n" + r.getReason_finish());
            System.out.println(r.toString());
            this.previust_comands[i].add_data(r);
            out.write(this.previust_comands[i].valeos_saved() + "\n");
        }

        try {
            out.flush();
            out.close();
        } catch (IOException e) {
        }

        //m.submitToLeaderboard(r, "Let's a go!", "forever");
    }

    public void access_file() {
        try {
            //se ficheiro existe, append
            if (new File(this.file_saved).isFile()) {
                out = new BufferedWriter(new BufferedWriter(new FileWriter(this.file_saved, true)));
            } else {
                //senão cria e escreve header
                out = new BufferedWriter(new BufferedWriter(new FileWriter(this.file_saved)));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void load() throws FileNotFoundException, IOException {
        File file = new File(this.file_saved);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int i = 0;
        while ((st = br.readLine()) != null) {
            Integer[] sequencia = new Integer[]{1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1};
            this.previust_comands[i] = new Comands(sequencia);
            System.out.println(st);
            i++;
        }

        while (i < 10) {
            Integer[] sequencia = new Integer[]{1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1};
            this.previust_comands[i] = new Comands(sequencia);
            i++;
        }
    }
}
