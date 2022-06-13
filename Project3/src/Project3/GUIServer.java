package Project3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIServer extends JFrame implements ActionListener {
    
    String[] servers = {"1" , "2"};
    
    JPanel pickServerPanel = new JPanel();
    JLabel pickServerLabel = new JLabel("Pick Server");
    JComboBox pickServerCombo = new JComboBox(servers);

    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel("  Pending requests ");
    JTextArea pendingRequestsTextArea = new JTextArea(5, 50);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Executed requests");
    JTextArea executedRequestsTextArea = new JTextArea(5, 50);
    public String data = null;

    public GUIServer(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(3, 0));
        pickServerCombo.addActionListener(this);
        this.pickServerPanel.add(this.pickServerLabel);
        this.pickServerPanel.add(this.pickServerCombo);
        this.getContentPane().add(this.pickServerPanel);
        this.pendingRequestsPanel.add(this.pendingRequestsLabel);
        this.pendingRequestsPanel.add(this.pendingRequestsTextArea);
        this.getContentPane().add(this.pendingRequestsPanel);
        this.executedRequestsPanel.add(this.executedRequestsLabel);
        this.executedRequestsPanel.add(this.executedRequestsTextArea);
        this.getContentPane().add(this.executedRequestsPanel);
        this.setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(pickServerCombo.getSelectedItem());
    }

}
