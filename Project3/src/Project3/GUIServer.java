package Project3;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIServer extends JFrame {

    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel(" Requests recieved ");
    JTextArea pendingRequestsTextArea = new JTextArea(5, 50);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Requests processed");
    JTextArea executedRequestsTextArea = new JTextArea(5, 50);
    public String data = null;

    public GUIServer(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(2, 0));
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
