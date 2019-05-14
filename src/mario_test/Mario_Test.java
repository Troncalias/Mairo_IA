/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario_test;
import Primary.Start;
import java.io.IOException;
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
        Start s = new Start("f");
        s.start();
    }
    
}
