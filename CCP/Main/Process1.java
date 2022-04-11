/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Process1.Main;
import Process1.Main.ControlGUI;
import Process1.Entities.Patient;
import Process1.Entities.TEntity2;
import Process1.Monitor.Manchester_Fifo;
/**
 *
 * @author user
 */
public class Process1 extends ControlGUI {

    /**
     * Creates new form M
     */
    public Process1() {
        super();
        pack();
    }

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
            java.util.logging.Logger.getLogger(Process1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Process1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Process1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Process1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>       
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Process1().setVisible(true);
            }
        });
        
        // Eventually this code should be localed elsewhere but not here
        Manchester_Fifo mSh1 = new MSharedRegion1();
        
        // Example of interfaces usage
        TEntity1 tE1 = new TEntity1( 1, (ISharedRegion1_Entity1)mSh1,
                                        (ISharedRegion2_Entity1)mSh2 );
        tE1.start();
        TEntity2 tE2 = new TEntity2( 1, (ISharedRegion1_Entity2)mSh1,
                                        (ISharedRegion2_Entity2)mSh2);
        tE2.start();
                
        // ....
        
        try {
            tE1.join();
            tE2.join();
        } catch ( InterruptedException ex ) {}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
