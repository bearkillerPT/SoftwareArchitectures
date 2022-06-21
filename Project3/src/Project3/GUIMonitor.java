package Project3;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUIMonitor extends JFrame {
    
    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel mainPanel = new JPanel();
    
    JPanel requestsLBPanel = new JPanel();
    JLabel requestsLBLabel = new JLabel("Requests managed by the LB");
    JPanel requestsLBPanel2 = new JPanel();
    JTextArea requestsLBTextArea = new JTextArea(5, 25);

    JPanel pendingRequestsPanel = new JPanel();
    JPanel pendingRequestsPanel2 = new JPanel();
    JLabel requestsServerLabel = new JLabel("  Requests managed by servers");
    JTextArea requestsServerTextArea = new JTextArea(5, 25);
    
    JPanel stateserversPanel = new JPanel();
    JPanel stateserversPanel2 = new JPanel();
    JLabel stateserversLabel = new JLabel("State of servers");
    JTextArea stateserversTextArea = new JTextArea(5, 25);
    
    JPanel stateLbsPanel = new JPanel();
    JPanel stateLbsPanel2 = new JPanel();
    JLabel stateLbsLabel = new JLabel("State of lbs");
    JTextArea stateLbsTextArea = new JTextArea(5, 25);
    
    public String data = null;

    public GUIMonitor(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        
        this.requestsLBTextArea.setBorder(border);
        this.requestsServerTextArea.setBorder(border);
        this.stateserversTextArea.setBorder(border);
        this.stateLbsTextArea.setBorder(border);
//        
//        this.requestsLBPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.requestsLBPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.pendingRequestsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.pendingRequestsPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.executedRequestsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.executedRequestsPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.stateserversPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.stateserversPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.stateLbsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        this.stateLbsPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.requestsLBPanel.add(this.requestsLBLabel);
        this.requestsLBPanel2.add(this.requestsLBTextArea);
        
        this.pendingRequestsPanel.add(this.requestsServerLabel);
        this.pendingRequestsPanel2.add(this.requestsServerTextArea);
        
        this.stateserversPanel.add(this.stateserversLabel);
        this.stateserversPanel2.add(this.stateserversTextArea);
        
        this.stateLbsPanel.add(this.stateLbsLabel);
        this.stateLbsPanel2.add(this.stateLbsTextArea);
        
        this.mainPanel.add(this.requestsLBPanel);
        this.mainPanel.add(this.requestsLBPanel2);
        this.mainPanel.add(this.pendingRequestsPanel);
        this.mainPanel.add(this.pendingRequestsPanel2);
        this.mainPanel.add(this.stateserversPanel);
        this.mainPanel.add(this.stateserversPanel2);
        this.mainPanel.add(this.stateLbsPanel);
        this.mainPanel.add(this.stateLbsPanel2);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        pack();
    }
    public void setLBRequests(String request){
        requestsLBTextArea.append(" "+request+"\n");
    }
    
    public void setServerStatus(String request){
        stateserversTextArea.setText(request);
    }
    public void setLbStatus(String request){
        stateLbsTextArea.setText(request);
    }
    public void setServerRequests(String request){
        requestsServerTextArea.setText(request);
    }
}
