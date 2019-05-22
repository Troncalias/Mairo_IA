/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Primary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import luigi.MarioUtils;
import luigi.Request;
import luigi.RunResult;

/**
 * Realiza os teste para o modo de Forever
 *
 * @author tronc
 */
public class Jenetic_C {

    //Variaveis recebidas na contrução desta
    private final String level_sucess;
    private final String forever;
    private final MarioUtils mario;

    //Listas das tentativas realizadas por Level e por Forevel
    private List<Chromosoma> list_levels;
    private List<Chromosoma> list_forever;
    private boolean pass;

    //Para controlo das tentativas
    //Controlo do Mundo
    private int curent_world;

    //Controlo do Stage
    private int curent_stage;

    //Controlo das posições que se trocao
    private int size_comands_acepted;

    public Jenetic_C(String level_sucess, String forever, MarioUtils mario) throws FileNotFoundException, IOException {
        this.level_sucess = level_sucess;
        this.forever = forever;
        this.mario = mario;

        this.curent_world = 1;
        this.curent_stage = 1;
        this.size_comands_acepted = 0;

        this.loadListLevel();
        this.loadListForever();
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void loadListLevel() throws FileNotFoundException, IOException {
        this.list_levels = new ArrayList();
        BufferedReader b;
        File f;
        for (int i = 1; i < 9; i++) {
            for (int y = 1; y < 5; y++) {
                f = new File(this.level_sucess + "Level_" + i + "_" + y + ".csv");
                if (f.exists()) {
                    b = new BufferedReader(new FileReader(f));
                    String line = b.readLine();
                    while (line != null) {
                        this.list_levels.add(new Chromosoma(line));
                        line = b.readLine();
                    }
                }
            }
        }
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void loadListForever() throws FileNotFoundException, IOException {
        this.list_forever = new ArrayList();
        File fs = new File(this.forever + "Tries.csv");

        if (fs.exists()) {
            BufferedReader b = new BufferedReader(new FileReader(fs));
            String line = b.readLine();
            while (line != null) {
                this.list_forever = this.addChromosome(new Chromosoma(line), list_forever);
                line = b.readLine();
            }
        }

    }

    /**
     *
     * @param chrom
     * @param lista
     * @return
     */
    public List<Chromosoma> addChromosome(Chromosoma chrom, List<Chromosoma> lista) {
        boolean added = false;
        List<Chromosoma> media = new ArrayList();

        for (int i = 0; i < lista.size(); i++) {
            //Vereficar se o tempo do que se adiciona e inferior ao proximo da lista atual
            if (lista.get(i).getGenes().size() < chrom.getGenes().size()) {
                if (!added) {
                    media.add(chrom);
                    added = true;
                }
                //Vereficar se a lista tem valores a frente
            } else if (lista.get(i).getGenes().size() == chrom.getGenes().size()) {
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
     *
     * @throws java.lang.CloneNotSupportedException
     */
    public void run() throws CloneNotSupportedException, IOException {
        Chromosoma Curent = new Chromosoma(0);
        Chromosoma Best;
        Chromosoma Level;
        Request req;
        RunResult r;
        if (this.list_forever.isEmpty()) {

            for (Chromosoma list_level : this.list_levels) {
                if (!list_level.passC(1, 1)) {
                    Curent = this.list_levels.get(0).replicar();
                }
            }

            if (!Curent.getGenes().isEmpty()) {
                Curent.addGene(new Gene(0));
                req = new Request(Curent.comandsNormalSize(), "SuperMarioBros-v0", "false", "forever");
                r = mario.goMarioGo(req);
                System.out.println(r.toString());
                Curent.setResults(r);
                Curent.removeLastGene();
                if (Curent.passC(1, 1)) {
                    this.list_forever = this.addChromosome(Curent, list_forever);
                    this.save();
                    this.run();
                } else {
                    System.out.println("Não passou o 1 nivel\nTente crialo de novo");
                }

            }
        } else {
            Curent = this.list_forever.get(0);
            this.curent_stage = Curent.getStage();
            this.curent_world = Curent.getWorld();
            this.size_comands_acepted = Curent.getGenes().size();
            boolean Encontrado = false;

            for (int i = 0; i < this.list_levels.size(); i++) {
                if (this.list_levels.get(i).getWorld() == Curent.getWorld() && this.list_levels.get(i).getStage() == Curent.getStage()) {
                    for (int y = 0; y < this.list_levels.get(i).getGenes().size(); y++) {
                        Curent.addGene(new Gene(this.list_levels.get(i).getGene(y).getX()));
                        Level = this.list_levels.get(i).replicar();
                    }
                    i = this.list_levels.size();
                    Encontrado = true;
                }
            }

            if (Encontrado) {
                Curent.addGene(0);
                req = new Request(Curent.comandsNormalSize(), "SuperMarioBros-v0", "false", "forever");
                r = mario.goMarioGo(req);
                Curent.setResults(r);
                Curent.removeLastGene();
                //Se passou o nivel
                if (Curent.getWorld() != this.curent_world || Curent.getStage() != this.curent_stage) {

                    this.list_forever = this.addChromosome(Curent, list_forever);
                    this.save();
                    this.run();
                    //Se não passou
                } else {
                    Best = Curent.replicar();
                    while (true) {
                        
                    }
                }
            }
        }
    }

    public void save() throws IOException {
        File filef = new File(this.forever + "Tries.csv");
        try (FileWriter Fstream = new FileWriter(filef, false)) {
            for (int i = 0; i < this.list_forever.size(); i++) {
                String s = this.list_forever.get(i).toSave() + "\n";
                Fstream.write(s);
            }
            Fstream.flush();
        }
    }
}
