/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import luigi.RunResult;

/**
 *
 * @author tronc
 */
public class Chromosome {

    //Informações para iniciar um array do inicio
    private List<Gene> genes;

    //Informações adicionadas após ter este sido currido
    private int distance;
    private int reward;
    private int score;
    private int time;
    private int coins;
    private int world;
    private int stage;
    private String reason;

    public Chromosome(int size) {
        this.genes = new ArrayList();
        this.addGene(size);
    }

    public Chromosome(int reward, int score, int time, int distance, int coins, int world, int stage, String reason, Integer[] g) {
        this.distance = distance;
        this.reward = reward;
        this.score = score;
        this.time = time;
        this.coins = coins;
        this.world = world;
        this.stage = stage;
        this.reason = reason;

        this.genes = new ArrayList();
        for (int i = 0; i < g.length; i++) {
            this.genes.add(new Gene(g[i]));
        }
    }

    public Chromosome(Chromosome c) {
        this.coins = c.getCoins();
        this.score = c.getScore();
        this.reward = c.getScore();
        this.time = c.getTime();
        this.world = c.getWorld();
        this.stage = c.getStage();
        this.distance = c.getDistance();
        this.reason = c.getReason();

        this.genes = new ArrayList();
        this.genes.addAll(c.getGenes());
    }

    public Chromosome(Chromosome c, int to_add) {
        this.coins = c.getCoins();
        this.score = c.getScore();
        this.reward = c.getScore();
        this.time = c.getTime();
        this.world = c.getWorld();
        this.stage = c.getStage();
        this.distance = c.getDistance();
        this.reason = c.getReason();

        this.genes = new ArrayList();
        this.genes.addAll(c.getGenes());
        this.addGene(to_add);
    }

    private void addGene(int add) {
        for (int i = 0; i < add; i++) {
            genes.add(new Gene());
        }
    }
    
    public void addOtherGenes(Chromosome c){
        this.genes.addAll(c.getGenes());
    }

    public void incresseGenes(int add) {
        for (int i = 0; i < add; i++) {
            genes.add(new Gene());
        }
    }

    public void removeGenes(int removed) {
        if (this.genes.size() < removed) {
            removed = this.genes.size();
        }
        for (int i = 0; i < removed; i++) {
            this.genes.remove(i);
        }
    }

    /**
     * Possibilidade de mudar os comandos que são executados
     *
     * @param chance
     * @param start
     * @param finish
     * @return
     */
    public Chromosome mutate(int chance, int start, int finish) {
        Chromosome ret = new Chromosome(0);

        if (start < finish) {
            if (start < 0) {
                start = 0;
            }
            if (finish > this.getGenes().size()) {
                finish = this.getGenes().size();
            }
        } else {
            start = 0;
            finish = this.getGenes().size();
        }

        for (int i = start; i < finish; i++) {
            ret.getGenes().add(this.getGene(i));
            
            int y = ThreadLocalRandom.current().nextInt(1, 100);
            if (y <= chance) {
                ret.getGene(i).newRandom();
            } 
        }

        return ret;
    }

    /**
     *
     * @param c
     * @param chance
     * @return
     */
    public Chromosome cross(Chromosome c, int chance) {
        Chromosome ret = new Chromosome(0);
        int i = 0;
        int y;

        if (chance >= 100 && chance <= 0) {
            chance = 10;
        }

        for (; i < this.getGenes().size() && i < c.getGenes().size(); i++) {
            y = ThreadLocalRandom.current().nextInt(1, 100);
            if (y <= chance) {
                ret.getGenes().get(i).setX(c.getGenes().get(i).getX());
            } else {
                ret.getGenes().get(i).setX(this.getGenes().get(i).getX());
            }
        }

        for (; i < this.getGenes().size(); i++) {
            ret.getGenes().get(i).setX(this.getGenes().get(i).getX());
        }

        for (; i < c.getGenes().size(); i++) {
            ret.getGenes().get(i).setX(c.getGenes().get(i).getX());
        }

        return ret;
    }

    public Integer[] comandsRightSize(int repeat) {
        Integer[] list = new Integer[this.genes.size() * repeat];
        for (int i = 0, l = 0; i < this.genes.size(); i++) {
            for (int y = 0; y < repeat; y++) {
                list[l] = this.getGene(i).getX();
                l++;
            }
        }

        return list;
    }

    public Integer[] comandsNormalSize() {
        Integer[] list = new Integer[this.genes.size()];
        for (int i = 0; i < this.genes.size(); i++) {
            list[i] = this.getGene(i).getX();
        }

        return list;
    }

    public String toSaveF() {
        StringBuilder s = new StringBuilder();
        //int reward, int score, int time, int distance, int coins, int world, int stage, String reason, Integer[] g
        s.append(reward).append(";").append(score).append(";").append(time).append(";").append(distance).append(";").append(coins).append(";").
                append(world).append(";").append(stage).append(";").append(reason).append(";");

        for (int i = 0; i < genes.size(); i++) {
            s.append(genes.get(i).getX());
            if (i < genes.size() - 1) {
                s.append(",");
            }
        }

        String v = s.toString();
        return v;
    }

    public String toSaveS(int repeat) {
        StringBuilder s = new StringBuilder();
        //int reward, int score, int time, int distance, int coins, int world, int stage, String reason, Integer[] g
        s.append(reward).append(";").append(score).append(";").append(time).append(";").append(distance).append(";").append(coins).append(";").
                append(world).append(";").append(stage).append(";").append(reason).append(";");

        for (int i = 0; i < genes.size(); i++) {
            for (int y = 0; y < repeat; y++) {
                s.append(genes.get(i).getX());
                if (i < genes.size() - 1 && y < repeat - 1) {
                    s.append(",");
                }
            }
        }

        String v = s.toString();
        return v;
    }
    
    public void setResults(RunResult r){
        this.distance = r.getX_pos();
        this.reward = r.getReward();
        this.score = r.getScore();
        this.time = r.getTime_left();
        this.coins = r.getCoins();
        this.world = r.getWorld();
        this.stage = r.getStage();
        this.reason = r.getReason_finish();
    }
    
    public Boolean pass(int world, int stage){
        return (this.world == world && this.stage == stage);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Chromosome(this);
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public int getSize() {
        return this.genes.size();
    }

    public Gene getGene(int i) {
        return this.genes.get(i);
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
