/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import luigi.MarioUtils;
import luigi.Request;
import luigi.RunResult;

/**
 *
 * @author tronc
 */
public class Jenetic_L {

    private List<Chromosome> lista_f;
    private List<Chromosome> list_f_start;
    private int best_f;
    
    private List<Chromosome> lista_s;
    private List<Chromosome> list_s_start;
    private int best_s;
    
    private final int world;
    private final int stage;

    private BufferedWriter out_f;
    private final String fileF;

    private BufferedWriter out_s;
    private final String fileS;

    public Jenetic_L(int world, int stage, String fileF, String fileS) throws IOException {
        this.world = world;
        this.stage = stage;
        this.fileF = fileF;
        this.fileS = fileS;
        this.list_f_start = new ArrayList();
        this.list_s_start = new ArrayList();

        //Acceder ao ficheiro que guarda os dados FALHADOS deste World/Stage
        if (new File(fileF).isFile()) {
            out_f = new BufferedWriter(new BufferedWriter(new FileWriter(fileF, true)));
            this.lista_f = this.loadList(new BufferedReader(new FileReader(fileF)), this.list_f_start);
        } else {
            out_f = new BufferedWriter(new BufferedWriter(new FileWriter(fileF)));
            out_f.write("");
        }

        //Acceder ao ficheiro que guarda os dados SUSSEDIDOS deste World/Stage
        if (new File(fileS).isFile()) {
            out_s = new BufferedWriter(new BufferedWriter(new FileWriter(fileS, true)));
            this.lista_s = this.loadList(new BufferedReader(new FileReader(fileS)), this.list_s_start);
        } else {
            out_s = new BufferedWriter(new BufferedWriter(new FileWriter(fileS)));
            out_s.write("");
        }
    }

    private List<Chromosome> loadList(BufferedReader b, List<Chromosome> l) throws IOException {
        List<Chromosome> c = new ArrayList();
        Chromosome g;
        String line = b.readLine();
        while (line != null) {
            g = this.getChromosome(line);
            c.add(g);
            l.add(g);
        }
        return c;
    }

    private Chromosome getChromosome(String s) {
        String[] list = s.split(";");
        //int reward, int score, int time, int distance, int coins, int world, 
        //int stage, String reason, Integer[] g

        int reward = Integer.getInteger(list[0]);
        int score = Integer.getInteger(list[1]);
        int time = Integer.getInteger(list[2]);
        int distance = Integer.getInteger(list[3]);
        int coins = Integer.getInteger(list[4]);
        int worlds = Integer.getInteger(list[5]);
        int stages = Integer.getInteger(list[6]);
        String reason = list[7];

        String[] values = list[8].split(",");
        Integer[] val = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            val[i] = Integer.getInteger(values[i]);
        }

        Chromosome c = new Chromosome(reward, score, time, distance, coins, worlds, stages, reason, val);
        return c;
    }

    public RunResult run(MarioUtils m){
         Request req;
         RunResult r;
         
        if(!this.lista_s.isEmpty()){
            this.order(this.lista_s);
            req = new Request(this.lista_s.get(0).comandsNormalSize(), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
            r = m.goMarioGo(req);
            if(r.getReason_finish().equals("win")){
                return r;
            }else{
                
            }
        }else{
            
        }
        
        
        return r;
    }
    
    private void fixingRun(MarioUtils m, RunResult r, Chromosome c){
        
    }
    
    public void orderScoreLists(){
        this.lista_f = this.order(lista_f);
        this.lista_s = this.order(lista_s);
    }
    
    private List<Chromosome>  order(List<Chromosome> lista){
        List<Chromosome> order = new ArrayList();
        
        boolean[] added = new boolean[lista.size()];
        Arrays.fill(added, false);
        
        for(int i=0; i<lista.size(); i++){
            int l=0;
            
            //Procurar primeiro pelo proximo posição que ainda não foi adicionada
            for(int y=0; y<lista.size(); y++){
                if(added[y] == false){
                    l=y+0;
                    y=lista.size();
                }
            }
            
            //Procurar pela lista qual é o atualmente maior, sen contar com os que foram já adicionados
            for(int y=0; y<lista.size(); y++){
                if(added[y] == false){
                    if(lista.get(l).getReward() < lista.get(y).getReward()){
                        l=i;
                    }
                }
            }
            
            //Adicionar e marcar como adicionado estre Chromosoma
            order.add(lista.get(l));
            added[l] = true;
            
        }
        
        return order;
    }
    
    public void save(){
        
    }
    
    public void addChromoF(Chromosome c){
        this.lista_f.add(c);
    }
    
    public void addChromoS(Chromosome c){
        this.lista_s.add(c);
    }
    
    
    public List<Chromosome> getLista_f() {
        return lista_f;
    }
    
    public Chromosome getChromoF(int i){
        return lista_f.get(i);
    }

    public List<Chromosome> getLista_s() {
        return lista_s;
    }
    
    public Chromosome getChromoS(int i){
        return lista_s.get(i);
    }

}
