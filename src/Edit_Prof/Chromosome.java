/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edit_Prof;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tronc
 */
public class Chromosome {
    //Informações para utilização
    private int currentCopy = 0;
    private int copy;
    
    //Informações do jogo
    private List<Gene> genes;
    private int reward;
    private int score;
    private int time;
    private int coins;
    private int world;
    private int stage;
    private int distance;
    private int pass;
    private String reason;

    public Chromosome(int size, int copy) {
        this.copy = copy;
        this.coins = 0;
        this.score = 0;
        this.reward = 0;
        this.time = 0;
        this.world = 1;
        this.stage = 1;
        this.distance = 0;
        this.reason = "";
        this.genes = new ArrayList();
        this.addGene(size);
    }

    public Chromosome(Chromosome c, int add) {
        this.copy = c.getCopy();
        this.coins = c.getCoins();
        this.score = c.getScore();
        this.reward = c.getScore();
        this.time = c.getTime();
        this.world = c.getWorld();
        this.stage = c.getStage();
        this.distance = c.getDistance();
        this.reason = c.getReason();
        
        this.genes = new ArrayList();
        for(int i=0; i<c.genes.size(); i++){
            this.genes.add(c.getGene(i));
        }
        this.addGene(add);
    }

    public Chromosome(Chromosome c) {
        this.copy = c.getCopy();
        this.coins = c.getCoins();
        this.score = c.getScore();
        this.reward = c.getScore();
        this.time = c.getTime();
        this.world = c.getWorld();
        this.stage = c.getStage();
        this.distance = c.getDistance();
        this.reason = c.getReason();
        
        this.genes = new ArrayList();
        for(int i=0; i<c.genes.size(); i++){
            this.genes.add(c.getGene(i));
        }
    }
    
    
    
    private void addGene(int add){
        Gene g = new Gene();
        
        for(int i=0; i<add; i++){
            if(this.currentCopy == 0){
                g = new Gene();
                genes.add(g);
                this.currentCopy = this.copy + 0;
            }else{
                genes.add(g);
                this.currentCopy--;
            }
        }
    }
    
    public void addGenes(int add){
        Gene g = new Gene();
        
        for(int i=0; i<add; i++){
            if(this.currentCopy == 0){
                g = new Gene();
                genes.add(g);
                this.currentCopy = this.copy + 0;
            }else{
                genes.add(g);
                this.currentCopy--;
            }
        }
    }
    
    public void removeGenes(int removed){
        for(int i=0; i<removed; i++){
            this.genes.remove(this.genes.size());
        }
    }
    
    public void mutate(int chance){
        
    }
    
    public void cross(){
        
    }

    @Override
    public Object clone(){
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

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }
    
    public Gene getGene(int i){
        return this.genes.get(i);
    }

    public int getCurrentCopy() {
        return currentCopy;
    }

    public void setCurrentCopy(int currentCopy) {
        this.currentCopy = currentCopy;
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
