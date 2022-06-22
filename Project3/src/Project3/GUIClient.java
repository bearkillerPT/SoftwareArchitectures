package Project3;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class GUIClient extends JFrame implements ActionListener {

    JPanel mainPanel = new JPanel();
    JPanel pickClientPanel = new JPanel();
    

    JLabel insertNILabel = new JLabel("Insert nº iterations");
    JTextField insertNITextField = new JTextField();
    JLabel insertDeadLineLabel = new JLabel("Insert deadline");
    JTextField insertDeadLineTextField = new JTextField();
    
    JButton insertRequestsbutton = new JButton();

    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel("  Pending requests ");
    JTextArea pendingRequestsTextArea = new JTextArea(10, 25);
    
    JScrollPane scroll = new JScrollPane(pendingRequestsTextArea);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Executed requests");
    JTextArea executedRequestsTextArea = new JTextArea(10, 25);
    
    JScrollPane scroll2 = new JScrollPane(executedRequestsTextArea);
    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    
    private String data;
    Boolean data_sent = false;
    
    public GUIClient(String title) {
        this.insertNITextField.setColumns(5);
        this.insertDeadLineTextField.setColumns(5);
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.insertRequestsbutton.setText("Send");
        this.pendingRequestsTextArea.setBorder(border);
        this.executedRequestsTextArea.setBorder(border);
        this.pickClientPanel.add(this.insertNILabel);
        this.pickClientPanel.add(this.insertNITextField);
        this.pickClientPanel.add(this.insertDeadLineLabel);
        this.pickClientPanel.add(this.insertDeadLineTextField);
        this.pickClientPanel.add(this.insertRequestsbutton);
        this.pendingRequestsPanel.add(this.pendingRequestsLabel);
        this.pendingRequestsPanel.add(this.scroll);
        this.executedRequestsPanel.add(this.executedRequestsLabel);
        this.executedRequestsPanel.add(this.scroll2);
        this.mainPanel.add(pickClientPanel);
        this.mainPanel.add(pendingRequestsPanel);
        this.mainPanel.add(executedRequestsPanel);
        
        this.setContentPane(mainPanel);
        this.insertRequestsbutton.addActionListener(this);
        this.setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String N_requests = insertNITextField.getText();
        String deadline = insertDeadLineTextField.getText();
        data = null;
        if ((!insertDeadLineTextField.getText().isEmpty()) && (!insertNITextField.getText().isEmpty())) {
                if (Integer.parseInt(N_requests) > 0) {
                    if (Integer.parseInt(deadline) > 0) {
                        data = N_requests + ":" + deadline;
                        data_sent = true;
                    } else {
                        System.out.println("Value of Deadline must be higher than 0");
                    }
                } else {
                    System.out.println("Number of requests must be higher than 0");
                }
        } else {
            System.out.println("No field can be empty!");
        }
    }
    public String getData(){
        if(data!= null && data_sent==true){
            data_sent=false;
            return data;
        }
        return null;
    }
    
    public void setPendingRequests(String request){
        pendingRequestsTextArea.setText(" "+request);
    }
    
    public void setExecutedRequests(String request){
        executedRequestsTextArea.append(" "+request);
    }
    public void cancelPendingRequest(String request){
        
    }
}
