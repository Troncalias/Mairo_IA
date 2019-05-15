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
public class Chromosoma {

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

    /**
     * Criar uma Chromosoma novo com um determinado tamanho
     * @param size 
     */
    public Chromosoma(int size) {
        this.genes = new ArrayList();
        this.startGenes(size);
    }

    /**
     * Criar um novo Chromosoma com os dados retirados de um ficheiro
     * @param string
     */
    public Chromosoma(String string) {
        String[] list = string.split(";");
        
        this.reward = Integer.parseInt(list[0]);
        this.score = Integer.parseInt(list[1]);
        this.time = Integer.parseInt(list[2]);
        this.distance = Integer.parseInt(list[3]);
        this.coins = Integer.parseInt(list[4]);
        this.world = Integer.parseInt(list[5]);
        this.stage = Integer.parseInt(list[6]);
        this.reason = list[7];

        String[] values = list[8].split(",");
        this.genes = new ArrayList();
        
        for (String value : values) {
            this.genes.add(new Gene(Integer.parseInt(value)));
        }
        
    }

    /**
     * Criar um novo Chromosoma a partir das informações de outro
     * @param c 
     */
    public Chromosoma(Chromosoma c) {
        this.coins = c.getCoins();
        this.score = c.getScore();
        this.reward = c.getReward();
        this.time = c.getTime();
        this.world = c.getWorld();
        this.stage = c.getStage();
        this.distance = c.getDistance();
        this.reason = c.getReason();

        this.genes = new ArrayList();
        this.genes.addAll(c.getGenes());
    }

    /**
     * Serve para introduzir os primeiros Genes random quando a class é iniciada
     * @param add 
     */
    private void startGenes(int add) {
        Gene g;
        for (int i = 0; i < add; i++) {
            g = new Gene();
            genes.add(new Gene());
            
            if(g.getX() == 2 || g.getX() == 4 || g.getX() == 5 || g.getX() == 7 || g.getX() == 9){
                int y = ThreadLocalRandom.current().nextInt(1, 100);
                if(y <= 30 && i+1<add){
                    genes.add(g);
                    i++;
                }
            }
        }
    }
    
    /**
     * Função repetida anterior mas para quando a classe ja foi iniciada
     * @param add 
     */
    public void incresseSizeGenes(int add) {
        this.startGenes(add);
    }
    
    /**
     * Adicionar a lista os Genes de outra tentativa (Para o tipo de jogo FOREVER)
     * @param c 
     */
    public void addOtherGenes(Chromosoma c){
        this.genes.addAll(c.getGenes());
    }

    /**
     * Serve para remover uma quantidade de Genes especifica
     * @param removed a quantidade de genes a serem removidos
     * @return Lista de Genes removidos noum novo Chromosome
     */
    public List<Gene> removeGenes(int removed) {
        if (this.genes.size() < removed) {
            removed = this.genes.size();
        }
        
        Integer [] l = new Integer[removed];
        
        for (int i = 0; i < removed; i++) {
            l[removed - i] = this.genes.remove(i).getX();
        }
        
        List<Gene> c = new ArrayList();
        
        for (Integer l1 : l) {
            c.add(new Gene(l1));
        }
        
        return c;
    }
    
    /**
     * Serve para remover o ultimo Gene da tentativa
     * @return 
     */
    public Gene removeLastGene(){
        return this.genes.remove(this.genes.size());
    }
    
    /**
     * Adiciona um lista de Genes
     * @param g 
     */
    public void addGenes(List<Gene> g){
        this.genes.addAll(g);
    }
    
    /**
     * Adiciona um Gene especifico
     * @param g 
     */
    public void addGene(Gene g){
        this.genes.add(g);
    }
    
    public void addGene(int i){
        this.genes.add(new Gene(i));
    }

    public void setValues(Integer[] values){
        this.genes = new ArrayList();
        
        for (Integer value : values) {
            this.genes.add(new Gene(value));
        }
    }
    
    /**
     * Possibilidade de mudar os comandos que são executados
     * @param start a posição inicial dos genes em que os comandos podem ser modificados
     * @param finish a posição final  dos genes em que os comandos podem ser modificados
     * @param indice_de_mutação a probabilidade de estes mudarem
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    public Chromosoma mutate(int start, int finish, int indice_de_mutação) throws CloneNotSupportedException {
        Chromosoma ret = this.replicar();

        if (start < finish) {
            if (start <= 0) {
                start = 0;
            }
            if (finish >= this.getGenes().size()) {
                finish = this.getGenes().size();
            }
        } else {
            start = 0;
            finish = this.getGenes().size();
        }

        for (int i = start; i < finish; i++) {
            int y = ThreadLocalRandom.current().nextInt(1, 100);
            if (y <= indice_de_mutação) {
                ret.getGene(i).newRandom();
            } 
        }

        return ret;
    }

    /**
     * Crusamento entre Chromosomas
     * @param c chromosoma que este vai se cruzar
     * @param chance a probabilidade de mudificação com o chromosoma que este se crusa
     * @return retorna o crusamento
     */
    public Chromosoma cross(Chromosoma c, int chance) {
        Chromosoma ret = new Chromosoma(0);
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

    /**
     * Retorna os comandos no tamanho desejado
     * @param repeat a quantidade de vezes que cada comando repete
     * @return retorno da lista de comandos no tamanho desejado no formato Integer
     */
    public Integer[] comandsGameSize(int repeat) {
        int j = this.genes.size() * repeat;
        Integer[] list = new Integer[j];
        for (int i = 0, l = 0; i < this.genes.size(); i++) {
            for (int y = 0; y < repeat; y++) {
                list[l] = this.getGene(i).getX();
                l++;
            }
        }

        return list;
    }

    /**
     * Retorno da lista de comandos no formato que estes estão atulamente
     * @return lista de comandos no formato Integer
     */
    public Integer[] comandsNormalSize() {
        Integer[] list = new Integer[this.genes.size()];
        for (int i = 0; i < this.genes.size(); i++) {
            list[i] = this.getGene(i).getX();
        }

        return list;
    }

    /**
     * Formato em que os dados deste Chromosoma são guardados
     * @return String dos dados deste Chromosoma
     */
    public String toSave() {
        StringBuilder s = new StringBuilder();
        //int reward, int score, int time, int distance, int coins, int world, int stage, String reason, Integer[] g
        s.append(Integer.toString(reward)).append(";").append(score).append(";").append(time).append(";").append(distance).append(";").append(coins).append(";").
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

    /**
     * Introduzir os resultados obtidos após uma tentativa
     * @param r retorno da execução do RunResult
     */
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
    
    /**
     * Verefica se passou o nivel no modo LEVEL
     * @return true se passpou, false se não
     */
    public Boolean passL(){
        return this.reason.equals("win");
    }
    
    /**
     * Verefica se passou o nivel no modo CONTINUO
     * @param world world na qual esta tentativa se enquadra
     * @param stage stage na qual esta tentativa se enquadra
     * @return true se passpou, false se não
     */
    public Boolean passC(int world, int stage){
        return !(this.world == world && this.stage == stage);
    }
    
    /**
     * Replica este Chromosoma
     * @return Este Chromosoma como um novo
     * @throws CloneNotSupportedException 
     */
    public Chromosoma replicar() throws CloneNotSupportedException  {
        return new Chromosoma(this);
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
