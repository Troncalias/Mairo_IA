/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import Controlo.Butao_finalizar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private final MarioUtils mario;
    
    private final int min_size;

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
     * @param size
     * @throws IOException
     */
    public Jenetic_L(int world, int stage, int change_possibility, String fileF, String fileS, MarioUtils m, int size) throws IOException {
        this.change_possibility = change_possibility;
        this.world = world;
        this.stage = stage;
        this.fileF = fileF;
        this.fileS = fileS;
        this.mario = m;
        this.min_size = size;

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
            int size = this.lista_falhada.get(0).getGenes().size() - 40;

            //Procurar pelo tempo anterior passado com sucesso aos if de runToFindSucessRun
            for (int y = 0; y < this.lista_falhada.size(); y++) {
                //
                if(this.lista_falhada.get(y).getGenes().size() == size){
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
     * @param a define se vai vereficar a lista de sucesso
     * @throws CloneNotSupportedException
     * @throws IOException
     */
    public void run(Butao_finalizar b, boolean a) throws CloneNotSupportedException, IOException {

        if (!this.lista_sucesso.isEmpty()) {
            if (a) {
                this.verefyPastSuccessfullRuns(b);
            }
        }

        if (this.lista_sucesso.isEmpty()) {
            boolean existeng;
            Chromosoma Curent;
            Chromosoma Best = new Chromosoma(0);
            Best.setReward(0);
            Best.setReason("death");

            if (this.lista_falhada.isEmpty()) {
                Curent = new Chromosoma(40);
                this.currentBestReward = 0;
                existeng = false;
            } else {
                this.bestPassRecord();
                Curent = this.lista_falhada.get(0).replicar();
                existeng = true;
            }

            int i = 0, l = 0, k = 0;

            //Painel de controlo
            b.setReward(this.currentBestReward);
            b.setMinimo_proxima(this.currentBestReward + this.min_size);
            b.setMelhor_atingido(this.currentBestReward, "no_more_comands");
            b.setVisible(true);

            while (true) {
                //Executar uma tentativa
                Request req;
                RunResult r;
                
                if (k == 20) {
                    req = new Request(Curent.comandsGameSize(5), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "true", "level");
                    k = 1;
                } else {
                    req = new Request(Curent.comandsGameSize(5), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
                    k++;
                }
                r = this.mario.goMarioGo(req);
                Curent.setResults(r);
                System.out.println(r.toString());

                //Aumentar a quantidade de tentativas no painel de controlo
                b.setTries(b.getTries() + 1);

                //Vereficar se a tentativa atual passou o nivel
                if (Curent.getReason().equals("win")) {
                    Chromosoma Save = Curent.replicar();
                    this.correctSuccessRun(Save, r);
                    break;
                } else {
                    if (i == 20) {
                        if(l < 10){
                            l++;
                        }
                        i = 0;
                    }

                    //Iniciar anterior
                    if (Best.getSize() == 0) {
                        if (!Curent.getReason().equals("death")) {
                            Best = Curent.replicar();
                            b.setMelhor_atingido(Best.getReward(), Best.getReason());
                        } else {
                            Best = new Chromosoma(0);
                            Best.setReward(0);
                            Best.setReason("death");
                        }
                    }

                    if (!existeng) {
                        //Guardar ultima tentativa executada
                        lista_falhada = this.addChromosome(Curent.replicar(), lista_falhada);
                        this.save_fails();
                    } else {
                        existeng = false;
                    }

                    //Vereficar se o anterior tem melhor resultado que o atual
                    if (Curent.getReward() <= Best.getReward() && !Best.getReason().equals("death")) {
                        Curent = Best.replicar();
                    }

                    if (!Curent.getReason().equals("death") && Best.getReward() <= Curent.getReward()) {
                        Best = Curent.replicar();
                        //Caso o executado atual não esteja na lista de falhados
                        //Vereficar se o resultado melhor passado é melhor que o atual
                        b.setMelhor_atingido(Best.getReward(), Best.getReason());
                    }

                    //Mudificar este dependendo da situação atual
                    if (this.currentBestReward + this.min_size > Curent.getReward()) {
                        Curent = Curent.mutate(Curent.getGenes().size() - 40 - 8 * l, Curent.getGenes().size(), this.change_possibility);
                        i++;
                    } else if (Curent.getReason().equals("death")) {
                        Curent = Curent.mutate(Curent.getGenes().size() - 40 - 8 * l, Curent.getGenes().size(), this.change_possibility);
                        i++;
                    } else {
                        this.currentBestReward = Curent.getReward();
                        b.setReward(this.currentBestReward);
                        b.setMinimo_proxima(this.currentBestReward + this.min_size);
                        b.setMelhor_atingido(this.currentBestReward, Curent.getReason());
                        Best.setReward(0);
                        Best.setReason("death");
                        Curent.incresseSizeGenes(40);
                        i = 0;
                        l = 0;
                    }

                }
                if (b.isFinalizar()) {
                    break;
                }

            }
        }
    }

    public void correctSuccessRun(Chromosoma c, RunResult r) throws IOException {
        Chromosoma s = new Chromosoma(c);
        s.setValues(c.comandsGameSize(5));
        List<Gene> i = new ArrayList();
        boolean not_pass = true;

        while (s.getGenes().size() != r.getCommands_used()) {
            i.add(s.removeLastGene());
        }
        i.add(s.removeLastGene());

        while (not_pass) {
            Request req = new Request(s.comandsNormalSize(), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "false", "level");
            r = this.mario.goMarioGo(req);
            s.setResults(r);

            if (s.getReason().equals("win")) {
                not_pass = false;
            } else {
                if (!i.isEmpty()) {
                    s.addGene(i.remove(i.size() - 1));
                } else {
                    s.addGene(s.getGene(s.getGenes().size() - 1));
                }
            }
        }

        Request req = new Request(s.comandsNormalSize(), "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "true", "level");
        this.mario.goMarioGo(req);

        this.lista_sucesso = this.addChromosome(s, this.lista_sucesso);
        this.save_success();
    }

    /**
     * Para implementar como teste das tentativas realizadas
     *
     * @return
     */
    private void verefyPastSuccessfullRuns(Butao_finalizar b) throws CloneNotSupportedException, IOException {
        RunResult r;
        for (int i = 0; i < this.lista_sucesso.size(); i++) {
            Integer[] in = this.lista_sucesso.get(i).comandsNormalSize();
            Request req = new Request(in, "SuperMarioBros-" + this.world + "-" + this.stage + "-v0", "true", "level");
            r = this.mario.goMarioGo(req);
            System.out.println(r.toString());
            if (!r.getReason_finish().equals("win")) {
                this.lista_sucesso.remove(i);
                i--;
            }
        }
        this.save_success();
        if (this.lista_sucesso.isEmpty()) {
            this.run(b, false);
        }
        this.run(b, false);
    }

    public void save() throws IOException {
        this.save_fails();
        this.save_success();
    }

    public void save_fails() throws IOException {
        File filef = new File(this.fileF);
        try (FileWriter Fstream = new FileWriter(filef, false)) {
            for (int i = 0; i < this.lista_falhada.size(); i++) {
                String s = this.lista_falhada.get(i).toSave() + "\n";
                Fstream.write(s);
            }
            Fstream.flush();
        }
    }

    public void save_success() throws IOException {
        File files = new File(this.fileS);
        try (FileWriter Sstream = new FileWriter(files, false)) {
            for (int i = 0; i < this.lista_sucesso.size(); i++) {
                Sstream.write(this.lista_sucesso.get(i).toSave() + "\n");
            }
            Sstream.flush();
        }
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
