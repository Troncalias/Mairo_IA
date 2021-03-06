/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlo;

import javax.swing.JLabel;

/**
 *
 * @author tronc
 */
public class Butao_finalizar extends javax.swing.JFrame {
    
    private int world;
    private int stage;
    private int tries = 0;
    private int reward;
    private int minimo_proxima = 0;
    private int melhor_atingido = 0;
    private boolean finalizar = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Butao_finalizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Butao_finalizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Butao_finalizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Butao_finalizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(() -> {
            new Butao_finalizar().setVisible(true);
        });
        
    }
    
    /**
     * Creates new form Butao_finalizar
     */
    public Butao_finalizar() {
        initComponents();
    }

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
        this.World.setText(world + "");
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        this.Stage.setText(stage + "");
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
        this.Tryes.setText(tries + "");
    }

    public boolean isFinalizar() {
        return finalizar;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
        this.Reward.setText(reward + "");
    }

    public int getMinimo_proxima() {
        return minimo_proxima;
    }

    public void setMinimo_proxima(int minimo_proxima) {
        this.minimo_proxima = minimo_proxima;
        this.Minimo.setText(minimo_proxima + "");
    }

    public int getMelhor_atingido() {
        return melhor_atingido;
    }

    public void setMelhor_atingido(int melhor_atingido, String reason) {
        this.melhor_atingido = melhor_atingido;
        this.Melhor_Ultima.setText(reason + ": " + melhor_atingido);
    }

    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Butao_Acabar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        World = new javax.swing.JLabel();
        Stage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Tryes = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Reward = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Minimo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Melhor_Ultima = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Butao_Acabar.setText("Acabar");
        Butao_Acabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Butao_AcabarActionPerformed(evt);
            }
        });

        jLabel1.setText("Mundo Atual: ");

        jLabel2.setText("Stage Atual: ");

        World.setText("1");

        Stage.setText("1");

        jLabel3.setText("Tentativas:");

        Tryes.setText("0");

        jLabel4.setText("Melhor Reward Atual");

        Reward.setText("0");

        jLabel5.setText("Minimo para proxima");

        Minimo.setText("0");

        jLabel6.setText("Melhor das ultimas");

        Melhor_Ultima.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(World)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Stage))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(Reward)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4))
                                .addGap(68, 68, 68))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Butao_Acabar)
                                .addGap(6, 6, 6)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(Tryes))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel5)))
                                .addGap(52, 52, 52))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Minimo)
                                .addGap(105, 105, 105))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Melhor_Ultima)
                                .addGap(52, 52, 52)))
                        .addGap(175, 175, 175))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(World)
                    .addComponent(jLabel2)
                    .addComponent(Stage)
                    .addComponent(jLabel3)
                    .addComponent(Tryes))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Reward)
                    .addComponent(Minimo))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Melhor_Ultima)
                .addGap(18, 18, 18)
                .addComponent(Butao_Acabar)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Butao_AcabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Butao_AcabarActionPerformed
        this.finalizar = true;
    }//GEN-LAST:event_Butao_AcabarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Butao_Acabar;
    private javax.swing.JLabel Melhor_Ultima;
    private javax.swing.JLabel Minimo;
    private javax.swing.JLabel Reward;
    private javax.swing.JLabel Stage;
    private javax.swing.JLabel Tryes;
    private javax.swing.JLabel World;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
