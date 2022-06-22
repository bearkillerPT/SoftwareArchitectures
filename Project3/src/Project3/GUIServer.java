package Project3;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class GUIServer extends JFrame {

    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel(" Requests recieved ");
    JTextArea pendingRequestsTextArea = new JTextArea(10, 25);
    
    JScrollPane scroll = new JScrollPane(pendingRequestsTextArea);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Requests processed");
    JTextArea executedRequestsTextArea = new JTextArea(10, 25);
    JScrollPane scroll2 = new JScrollPane(executedRequestsTextArea);
    public String data = null;

    public GUIServer(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(2, 0));
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.pendingRequestsTextArea.setBorder(border);
        this.executedRequestsTextArea.setBorder(border);
        this.pendingRequestsPanel.add(this.pendingRequestsLabel);
        this.pendingRequestsPanel.add(this.scroll);
        this.getContentPane().add(this.pendingRequestsPanel);
        this.executedRequestsPanel.add(this.executedRequestsLabel);
        this.executedRequestsPanel.add(this.scroll2);
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
