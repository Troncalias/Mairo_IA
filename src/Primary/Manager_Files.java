/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;
import Enums.Levels;
import Enums.Tipos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author tronc
 */
public class Manager_Files {

    //Gestão dos ficheiros do modo FOREVER
    //Ficheiros que guardão os fails
    private BufferedWriter[] out_f = new BufferedWriter[32];
    private final String[] files_failures = new String[]{"F_1_1.csv", "F_1_2.csv", "F_1_3.csv", "F_1_4.csv", "F_2_1.csv", "F_2_2.csv", "F_2_3.csv", "F_2_4.csv",
        "F_3_1.csv", "F_3_2.csv", "F_3_3.csv", "F_3_4.csv", "F_4_1.csv", "F_4_2.csv", "F_4_3.csv", "F_4_4.csv",
        "F_5_1.csv", "F_5_2.csv", "F_5_3.csv", "F_5_4.csv", "F_6_1.csv", "F_6_2.csv", "F_6_3.csv", "F_6_4.csv",
        "F_7_1.csv", "F_7_2.csv", "F_7_3.csv", "F_7_4.csv", "F_8_1.csv", "F_8_2.csv", "F_8_3.csv", "F_8_4.csv"};

    //Ficheiros que guardão os successes
    private BufferedWriter[] out_s = new BufferedWriter[32];
    private final String[] files_success = new String[]{"S_1_1.csv", "S_1_2.csv", "S_1_3.csv", "S_1_4.csv", "S_2_1.csv", "S_2_2.csv", "S_2_3.csv", "S_2_4.csv",
        "S_3_1.csv", "S_3_2.csv", "S_3_3.csv", "S_3_4.csv", "S_4_1.csv", "S_4_2.csv", "S_4_3.csv", "S_4_4.csv",
        "S_5_1.csv", "S_5_2.csv", "S_5_3.csv", "S_5_4.csv", "S_6_1.csv", "S_6_2.csv", "S_6_3.csv", "S_6_4.csv",
        "S_7_1.csv", "S_7_2.csv", "S_7_3.csv", "S_7_4.csv", "S_8_1.csv", "S_8_2.csv", "S_8_3.csv", "S_8_4.csv"};
    ;
    
    //Gestão dos ficheiros do modo NIVEL
    //Ficheiro que guarda o resultado do "level"
    private BufferedWriter out_lf;
    private final String file_level_success = "Level_s";

    private BufferedWriter out_ls;
    private final String file_level_failures = "Level_f";

    //Pastas dos ficheiros
    private final String pfs = "/files/Forever/Failures/";
    private final String pff = "/files/Forever/Success/";
    private final String pls = "/files/Level";
    
    //
    private final Comands_Executed[] c = new Comands_Executed[64];

    public Manager_Files() {
        for(int i=0; i<this.c.length; i++){
            this.c[i] = new Comands_Executed();
        }
    }

    public Comands_Executed[] getAllComandsF() throws IOException {
        Comands_Executed[] c = new Comands_Executed[64];
        for (int i = 0; i < 32; i++) {
            if (new File("output_F1Ex6-2.csv").isFile()) {
                out_f[i] = new BufferedWriter(new BufferedWriter(new FileWriter(pff + this.files_failures[i], true)));
                out_s[i] = new BufferedWriter(new BufferedWriter(new FileWriter(pfs + this.files_success[i], true)));
                c[i] = this.loadComandos(pff + this.files_failures[i]);
                c[32 + i] = this.loadComandos(pfs + this.files_success[i]);

            } else {
                //senão cria e escreve header
                out_f[i] = new BufferedWriter(new BufferedWriter(new FileWriter(pff + this.files_failures[i])));
                out_f[i].write("");
                out_s[i] = new BufferedWriter(new BufferedWriter(new FileWriter(pfs + this.files_failures[i])));
                out_s[i].write("");
            }
        }
        return c;
    }

    public Comands_Executed loadComandos(String f) throws IOException {
        Comands_Executed ce = new Comands_Executed();

        File file = new File(f);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String st;
        while ((st = br.readLine()) != null) {

            //Format: score, time, position, reason, commands
            String[] str = st.split(";");

            //Load comands
            String[] intS = str[4].split(",");
            Integer[] intrs = new Integer[intS.length];
            for (int y = 0; y < intS.length; y++) {
                intrs[y] = Integer.parseInt(intS[y]);
            }

            //Save all inforo of try
            Comands comands = new Comands(intrs);
            Game_Info g = new Game_Info(comands);
            g.setReason_stop(str[3]);
            g.setX_position(Integer.parseInt(str[2]));
            g.setTime(Integer.parseInt(str[1]));
            g.setScore(Integer.parseInt(str[0]));
            
            ce.addComand(comands);
        }
        
        return ce;
    }

    /**
     * public Manager_Files(String s) { if ("l".equals(s)) { this.load_f(); }
     * else {
     *
     * }
     * if ("f".equals(s)) { this.load_l(); } else { this.load(); } }
     *
     *
     * public void load() throws FileNotFoundException, IOException { File file
     * = new File(this.file_saved); BufferedReader br = new BufferedReader(new
     * FileReader(file)); String st; int i = 0; while ((st = br.readLine()) != *
     * null) { Comands c = new Comands(new Integer[]{1, 1, 1, 1, 1, 2, 2, 2, 1,
     * 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1,
     * 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1,
     * 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1,
     * 5, 5, 5, 5, 1, 1}); this.previust_comands[i] = new Game_Info(c);
     * System.out.println(st); i++; }
     *
     * while (i < 10) { Comands c = new Comands(new Integer[]{1, 1, 1, 1, 1, 2,
     * 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1,
     * 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 5, 5,
     * 5, 5, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 5, 5, 5, 5, 1, 1, 1,
     * 1, 1, 1, 5, 5, 5, 5, 1, 1}); this.previust_comands[i] = new Game_Info(c);
     * i++; } }
     *
     *
     * public void access_file() { try { //se ficheiro existe, append if (new
     * File(this.file_saved).isFile()
     *
     * ) { out = new BufferedWriter(new BufferedWriter(new
     * FileWriter(this.file_saved, true))); }else { //senão cria e escreve
     * header out = new BufferedWriter(new BufferedWriter(new
     * FileWriter(this.file_saved))); } } catch (IOException e) {
     * System.out.println(e.toString()); } }
     *
     *
     */
}
