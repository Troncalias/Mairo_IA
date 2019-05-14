/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import Controlo.Butao_finalizar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    private List<Chromosome> lista_s;

    private final int world;
    private final int stage;

    private BufferedWriter out_f;
    private final String fileF;

    private BufferedWriter out_s;
    private final String fileS;

    private int currentBestReward = 0;
    private MarioUtils mario;

    public Jenetic_L(int world, int stage, String fileF, String fileS, MarioUtils m) throws IOException {
        this.world = world;
        this.stage = stage;
        this.fileF = fileF;
        this.fileS = fileS;
        this.mario = m;

        //Acceder ao ficheiro que guarda os dados FALHADOS deste World/Stage
        if (new File(fileF).isFile()) {
            this.lista_f = this.loadList(new BufferedReader(new FileReader(fileS)));
            this.bestPassRecord();
            File file = new File(this.fileF);
            FileWriter fstream = new FileWriter(file, true);
            this.out_f = new BufferedWriter(fstream);
        } else {
            out_f = new BufferedWriter(new BufferedWriter(new FileWriter(fileF)));
            out_f.write("");
            out_f.flush();
            out_f.close();
            File file = new File(this.fileF);
            FileWriter fstream = new FileWriter(file, true);
            this.out_f = new BufferedWriter(fstream);
        }

        //Acceder ao ficheiro que guarda os dados SUSSEDIDOS deste World/Stage
        if (new File(fileS).isFile()) {
            this.lista_s = this.loadList(new BufferedReader(new FileReader(fileS)));
            File file = new File(this.fileS);
            FileWriter fstream = new FileWriter(file, true);
            this.out_s = new BufferedWriter(fstream);
        } else {
            out_s = new BufferedWriter(new BufferedWriter(new FileWriter(fileS)));
            out_s.write("");
            out_s.flush();
            out_s.close();
            File file = new File(this.fileF);
            FileWriter fstream = new FileWriter(file, true);
            this.out_f = new BufferedWriter(fstream);
        }

    }

    private List<Chromosome> loadList(BufferedReader b) throws IOException {
        List<Chromosome> c = new ArrayList();
        Chromosome g;
        String line = b.readLine();
        while (line != null) {
            g = this.getChromosome(line);
            c.add(g);
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

    public final void bestPassRecord() {
        int time = 0;

        //O tempo actual maior possivel
        if (!this.lista_f.isEmpty()) {
            for (int l = 0; l < this.lista_f.size(); l++) {
                if (time < this.lista_f.get(l).getTime()) {
                    time = this.lista_f.get(l).getTime();
                }
            }

            //Procurar exatamente o tempo que teria o anterior
            int i = 0;
            while (time > i || i == 400) {
                i += 10;
            }

            //Procurar pelo tempo anterior passado com sucesso aos if de runToFindSucessRun
            for (int y = 0; y < this.lista_f.size(); y++) {
                if (this.lista_f.get(y).getTime() == i && !this.lista_f.get(y).getReason().equals("death")) {
                    this.currentBestReward = this.lista_f.get(y).getReward();
                    y = this.lista_f.size();
                }
            }
        }
    }

    public void run(Butao_finalizar b) throws CloneNotSupportedException, IOException {

        boolean status = true;
        if (!this.lista_s.isEmpty()) {
            status = false;
        } else if (b.isFinalizar()) {
            status = false;
        }

        while (status) {
            Chromosome c;
            Chromosome d;
            boolean pass = true;
            boolean first;
            int i = 0, l = 0;

            if (this.lista_s.isEmpty()) {
                c = new Chromosome(40);
                first = true;
            } else {
                c = this.lista_f.get(0).replicar();
                first = false;
            }
            b.setVisible(true);

            while (pass) {

                Request req = new Request(c.comandsGameSize(5), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
                RunResult r = this.mario.goMarioGo(req);
                c.setResults(r);

                //Aumentar a quantidade de tentativas no final
                b.setTries(b.getTries() + 1);

                if (first) {
                    first = false;
                    this.currentBestReward = c.getReward();
                }

                if (c.passC(world, stage)) {
                    d = c.replicar();
                    this.correctSuccessRun(d);
                    pass = false;
                    status = false;
                } else {
                    if (i == 25) {
                        l++;
                    }

                    //Adicionar tentativa a list de tentativas falhadas
                    d = c.replicar();
                    this.lista_f.add(d);
                    this.order(lista_f);
                    //Mudificar este dependendo da situação atual

                    if (this.currentBestReward + 200 >= c.getReward()) {
                        c.mutate(50, c.getGenes().size() - 40 - 5 * l, c.getGenes().size());
                        i++;
                    } else if (c.getReward() - this.currentBestReward < 200) {
                        c.mutate(50, c.getGenes().size() - 40 - 5 * l, c.getGenes().size());
                        i++;
                    } else if (!r.getReason_finish().equals("no_more_commands")) {
                        c.mutate(50, c.getGenes().size() - 40 - 5 * l, c.getGenes().size());
                        i++;
                    } else {
                        this.order(lista_f);
                        this.save();
                        c.incresseSizeGenes(40);
                        this.currentBestReward = c.getReward();
                        i = 0;
                        l = 0;
                    }
                }
                if (b.isFinalizar()) {
                    pass = false;
                    status = false;
                }

            }
            
        }
    }

    public void correctSuccessRun(Chromosome c) {
        Chromosome s = new Chromosome(c);
        s.setValues(c.comandsGameSize(5));
        List<Gene> i = new ArrayList();
        boolean b = false;

        while (!b) {
            i.add(s.removeLastGene());
            Request req = new Request(c.comandsNormalSize(), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
            RunResult r = this.mario.goMarioGo(req);
            s.setResults(r);

            if (!s.getReason().equals("win")) {
                while (!s.getReason().equals("win")) {
                    s.addGene(i.remove(i.size() - 1));
                    req = new Request(c.comandsNormalSize(), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
                    r = this.mario.goMarioGo(req);
                    s.setResults(r);
                    b = true;
                }
            }
        }
        this.lista_s.add(s);
    }

    /**
     * Para implementar como teste
     *
     * @return
     */
    private List<Integer> verefyPasRuns() {
        List<Integer> l = new ArrayList();
        for (int i = 0; i < this.lista_f.size(); i++) {
            if (this.lista_f.get(i).passC(world, stage)) {
                l.add(i);
            }
        }
        return l;
    }

    /**
     * Corrigir run para Modo continuo
     *
     * @param m
     * @param r
     * @param c
     */
    private void fixingRun(MarioUtils m, RunResult r, Chromosome c) {

    }

    public void orderScoreLists() {
        this.lista_f = this.order(lista_f);
        this.lista_s = this.order(lista_s);
    }

    private List<Chromosome> order(List<Chromosome> lista) {
        List<Chromosome> order = new ArrayList();

        boolean[] added = new boolean[lista.size()];
        Arrays.fill(added, false);

        for (int i = 0; i < lista.size(); i++) {
            int l = 0;

            //Procurar primeiro pelo proximo posição que ainda não foi adicionada
            for (int y = 0; y < lista.size(); y++) {
                if (added[y] == false) {
                    l = y + 0;
                    y = lista.size();
                }
            }

            //Procurar pela lista qual é o atualmente maior, sen contar com os que foram já adicionados
            for (int y = 0; y < lista.size(); y++) {
                if (added[y] == false) {
                    if (lista.get(l).getReward() < lista.get(y).getReward()) {
                        l = i;
                    }
                }
            }

            //Adicionar e marcar como adicionado estre Chromosoma
            order.add(lista.get(l));
            added[l] = true;

        }

        return order;
    }

    public void save() throws FileNotFoundException, IOException {
        this.orderScoreLists();

        for (int i = 0; i < this.lista_f.size(); i++) {
            this.out_f.write(this.lista_f.get(i).toSaveF()+"\n");
        }
        out_f.flush();
        out_f.close();

        for (int i = 0; i < this.lista_s.size(); i++) {
            this.out_s.write(this.lista_s.get(i).toSaveF()+"\n");
        }

        out_s.flush();
        out_s.close();

    }

    public void addChromoF(Chromosome c) {
        this.lista_f.add(c);
    }

    public void addChromoS(Chromosome c) {
        this.lista_s.add(c);
    }

    public List<Chromosome> getLista_f() {
        return lista_f;
    }

    public Chromosome getChromoF(int i) {
        return lista_f.get(i);
    }

    public List<Chromosome> getLista_s() {
        return lista_s;
    }

    public Chromosome getChromoS(int i) {
        return lista_s.get(i);
    }

    public int getWorld() {
        return world;
    }

    public int getStage() {
        return stage;
    }
    
    

}
