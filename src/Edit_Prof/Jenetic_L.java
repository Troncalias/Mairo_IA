/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import Controlo.Butao_finalizar;
import java.io.BufferedReader;
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

    private List<Chromosoma> lista_falhada;

    private List<Chromosoma> lista_sucesso;

    private final int world;
    private final int stage;
    private final int change_possibility;

    private final String fileF;

    private final String fileS;

    private int currentBestReward = 0;
    private MarioUtils mario;

    /**
     * Construtor do gestor das tentativas deste stage
     *
     * @param world world designado para este gestor
     * @param stage stage designado para este gestor
     * @param change_possibility probabilidade de alterar o codigo durante o
     * mutate
     * @param fileF localização do ficheiro que guarda as tentativas que
     * falharam
     * @param fileS localização do ficheiro que guarda as tentativas que tiveram
     * sucesso
     * @param m necessário para executar as tentativas
     * @throws IOException
     */
    public Jenetic_L(int world, int stage, int change_possibility, String fileF, String fileS, MarioUtils m) throws IOException {
        this.change_possibility = change_possibility;
        this.world = world;
        this.stage = stage;
        this.fileF = fileF;
        this.fileS = fileS;
        this.mario = m;

        //Acceder ao ficheiro que guarda os dados FALHADOS deste World/Stage
        File f = new File(fileF);
        if (f.exists()) {
            this.lista_falhada = this.loadList(new BufferedReader(new FileReader(fileF)));
        } else {
            this.lista_falhada = new ArrayList();
        }

        //Acceder ao ficheiro que guarda os dados SUSSEDIDOS deste World/Stage
        f = new File(fileS);
        if (f.exists()) {
            this.lista_sucesso = this.loadList(new BufferedReader(new FileReader(fileS)));
        } else {
            this.lista_sucesso = new ArrayList();
        }

    }

    /**
     * Carrega as tentativas passadas dentro do ficheiro recebido
     *
     * @param b o ficheiro que está a ser lido
     * @return todos os Chromosomas que foram recebidos pela leitura do ficheiro
     * @throws IOException
     */
    private List<Chromosoma> loadList(BufferedReader b) throws IOException {
        List<Chromosoma> c = new ArrayList();
        Chromosoma d;

        String line = b.readLine();
        while (line != null) {
            d = new Chromosoma(line);
            c = this.addChromosome(d, c);
            line = b.readLine();
        }

        return c;
    }

    /**
     * Procura pelo Record passado que estava a ser usado
     */
    private void bestPassRecord() {
        if (!this.lista_falhada.isEmpty()) {
            //O tempo actual maior possivel
            int time = this.lista_falhada.get(0).getTime();

            //Procurar exatamente o tempo que teria o anterior
            int i = 0;
            while (time >= i && i < 400) {
                i += 10;
            }

            //Procurar pelo tempo anterior passado com sucesso aos if de runToFindSucessRun
            for (int y = 0; y < this.lista_falhada.size(); y++) {

                //Vereficar se este Chromosoma tem o mesmo tempo que a tentativa passada anterior
                if (this.lista_falhada.get(y).getTime() == i) {

                    //Vereficar se este Chromosoma não tem como razão de ter parado como morte
                    if (!this.lista_falhada.get(y).getReason().equals("death")) {

                        //Defenir este como o melhor Reward atualmente para comparação e acabar com as buscas
                        this.currentBestReward = this.lista_falhada.get(y).getReward();
                        y = this.lista_falhada.size();
                    }
                }
            }
        }
    }

    /**
     * Adiciona o Chromosoma a lista na posição desejada e ordenada
     *
     * @param chrom Chromosoma que vai ser adicionado a lista
     * @param lista lista a adicionar o Chromosoma
     * @return 
     */
    public List<Chromosoma> addChromosome(Chromosoma chrom, List<Chromosoma> lista) {
        boolean added = false;
        List<Chromosoma> media = new ArrayList();

        for (int i = 0; i < lista.size(); i++) {
            //Vereficar se o tempo do que se adiciona e inferior ao proximo da lista atual
            if (lista.get(i).getTime() > chrom.getTime()) {
                if (!added) {
                    media.add(chrom);
                    added = true;
                }
                //Vereficar se a lista tem valores a frente
            } else if (lista.get(i).getTime() == chrom.getTime()) {
                if (lista.get(i).getReward() < chrom.getReward()) {
                    if (!added) {
                        media.add(chrom);
                        added = true;
                    }
                }
            }
            media.add(lista.get(i));
        }
        if (!added) {
            media.add(chrom);
        }

        return media;
    }

    /**
     * Execução do código principal
     *
     * @param b Butão de controlo de execução
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    public void run(Butao_finalizar b) throws CloneNotSupportedException, IOException {

        boolean existeng;

        Chromosoma c;
        Chromosoma d;
        boolean pass = false;
        int i = 0, l = 0;

        if (this.lista_falhada.isEmpty()) {
            c = new Chromosoma(40);
            this.currentBestReward = 0;
            existeng = false;
        } else {
            this.bestPassRecord();
            c = this.lista_falhada.get(0).replicar();
            existeng = true;
        }
        b.setVisible(true);

        while (!pass) {
            //Executar uma tentativa
            Request req = new Request(c.comandsGameSize(5), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
            RunResult r = this.mario.goMarioGo(req);
            c.setResults(r);
            System.out.println(r.toString());

            //Aumentar a quantidade de tentativas no final
            b.setTries(b.getTries() + 1);

            //Vereficar se a tentativa atual passou o nivel
            if (c.passC(world, stage)) {
                d = c.replicar();
                this.correctSuccessRun(d);
                pass = true;
            } else {
                if (i == 50) {
                    l++;
                }

                if (!existeng) {
                    //Adicionar tentativa a list de tentativas falhadas
                    d = c.replicar();
                    lista_falhada = this.addChromosome(d, lista_falhada);
                    this.save();

                }
                existeng = false;

                //Mudificar este dependendo da situação atual
                if (this.currentBestReward + 200 > c.getReward()) {
                    c = c.mutate(c.getGenes().size() - 40 - 4 * l, c.getGenes().size(), this.change_possibility);
                    i++;
                } else if (c.getReason().equals("death")) {
                    c = c.mutate(c.getGenes().size() - 40 - 4 * l, c.getGenes().size(), this.change_possibility);
                    i++;
                } else {
                    this.currentBestReward = c.getReward();
                    c.incresseSizeGenes(40);
                    i = 0;
                    l = 0;
                }
            }
            if (b.isFinalizar()) {
                pass = true;
            }

        }
    }

    public void correctSuccessRun(Chromosoma c) {
        Chromosoma s = new Chromosoma(c);
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
        this.lista_sucesso = this.addChromosome(s, this.lista_sucesso);
    }

    /**
     * Para implementar como teste
     *
     * @return
     */
    private List<Integer> verefyPasRuns() {
        List<Integer> l = new ArrayList();
        for (int i = 0; i < this.lista_falhada.size(); i++) {
            if (this.lista_falhada.get(i).passC(world, stage)) {
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
    private void fixingRun(MarioUtils m, RunResult r, Chromosoma c) {

    }

    public void save() throws IOException {

        File filef = new File(this.fileF);
        FileWriter Fstream = new FileWriter(filef, false);
        for (int i = 0; i < this.lista_falhada.size(); i++) {
            String s = this.lista_falhada.get(i).toSave() + "\n";
            Fstream.write(s);
        }
        Fstream.flush();
        Fstream.close();

        File files = new File(this.fileS);
        FileWriter Sstream = new FileWriter(files, false);
        for (int i = 0; i < this.lista_sucesso.size(); i++) {
            Sstream.write(this.lista_sucesso.get(i).toSave() + "\n");
        }
        Sstream.flush();
        Sstream.close();

    }

    public void addChromoF(Chromosoma c) {
        this.lista_falhada.add(c);
    }

    public void addChromoS(Chromosoma c) {
        this.lista_sucesso.add(c);
    }

    public List<Chromosoma> getLista_f() {
        return lista_falhada;
    }

    public Chromosoma getChromoF(int i) {
        return lista_falhada.get(i);
    }

    public List<Chromosoma> getLista_s() {
        return lista_sucesso;
    }

    public Chromosoma getChromoS(int i) {
        return lista_sucesso.get(i);
    }

    public int getWorld() {
        return world;
    }

    public int getStage() {
        return stage;
    }

}
