package Project3;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class GUIServer extends JFrame {

    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel(" Requests recieved ");
    JTextArea pendingRequestsTextArea = new JTextArea(5, 25);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Requests processed");
    JTextArea executedRequestsTextArea = new JTextArea(5, 25);
    public String data = null;

    public GUIServer(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(2, 0));
        this.pendingRequestsTextArea.setBorder(border);
        this.executedRequestsTextArea.setBorder(border);
        this.pendingRequestsPanel.add(this.pendingRequestsLabel);
        this.pendingRequestsPanel.add(this.pendingRequestsTextArea);
        this.getContentPane().add(this.pendingRequestsPanel);
        this.executedRequestsPanel.add(this.executedRequestsLabel);
        this.executedRequestsPanel.add(this.executedRequestsTextArea);
        this.getContentPane().add(this.executedRequestsPanel);
        this.setVisible(true);
        pack();
    }

    public void setPendingRequests(String request) {
        pendingRequestsTextArea.setText(request);
    }
    public void setExecutedRequests(String request){
        executedRequestsTextArea.append(request);
    }
}
