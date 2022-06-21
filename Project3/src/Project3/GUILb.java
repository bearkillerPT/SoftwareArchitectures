package Project3;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUILb extends JFrame {
    
    JPanel sentRequestsPanel = new JPanel();
    JLabel sentRequestsLabel = new JLabel("Sent Requests");
    JTextArea sentRequestsTextArea = new JTextArea(5, 25);

    public String data = null;

    public GUILb(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(1, 0));
        this.sentRequestsPanel.add(this.sentRequestsLabel);
        this.sentRequestsPanel.add(this.sentRequestsTextArea);
        this.getContentPane().add(this.sentRequestsPanel);
        this.setVisible(true);
        pack();
    }
    public void setPendingRequests(String request){
        sentRequestsTextArea.append(request);
    }
}