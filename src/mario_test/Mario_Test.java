/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario_test;

import Controlo.Butao_finalizar;
import Primary.Jenetic_C;
import Primary.Start;
import java.io.IOException;
import luigi.MarioUtils;

/**
 *
 * @author tronc
 */
public class Mario_Test {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        // TODO code application logic here
        Butao_finalizar b = new Butao_finalizar();
        MarioUtils m = new MarioUtils("192.168.1.6");
        //String level_sucess, String forever_sucess, MarioUtils mario
        //Jenetic_C j = new Jenetic_C("files/Level/Success/","files/Forever/",m);
        //j.run();
        Start s = new Start(m, b);
    }

}
