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

public class GUILb extends JFrame {
    
    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel sentRequestsPanel = new JPanel();
    JLabel sentRequestsLabel = new JLabel("Sent Requests");
    JTextArea sentRequestsTextArea = new JTextArea(10, 25);
    JScrollPane scroll = new JScrollPane(sentRequestsTextArea);

    public String data = null;

    public GUILb(String title) {
        this.sentRequestsTextArea.setBorder(border);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(1, 0));
        this.sentRequestsPanel.add(this.sentRequestsLabel);
        this.sentRequestsPanel.add(this.scroll);
        this.getContentPane().add(this.sentRequestsPanel);
        this.setVisible(true);
        pack();
    }
    public void setPendingRequests(String request){
        sentRequestsTextArea.append(request);
    }
}