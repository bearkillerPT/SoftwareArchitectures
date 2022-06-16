package Project3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIClient extends JFrame implements ActionListener {

    JPanel pickClientPanel = new JPanel();
    JLabel pickClientLabel = new JLabel("Insert Client Id");
    JTextField insertIdClientTextField = new JTextField();

    JLabel insertRequestsLabel = new JLabel("Insert nº requests");
    JTextField insertRequestsTextField = new JTextField();
    JLabel insertDeadLineLabel = new JLabel("Insert deadline");
    JTextField insertDeadLineTextField = new JTextField();
    JButton insertRequestsbutton = new JButton();

    JPanel pendingRequestsPanel = new JPanel();
    JLabel pendingRequestsLabel = new JLabel("  Pending requests ");
    JTextArea pendingRequestsTextArea = new JTextArea(5, 50);

    JPanel executedRequestsPanel = new JPanel();
    JLabel executedRequestsLabel = new JLabel("Executed requests");
    JTextArea executedRequestsTextArea = new JTextArea(5, 50);
    private String data;
    Boolean data_sent = false;
    
    public GUIClient(String title) {
        this.insertIdClientTextField.setColumns(5);
        this.insertRequestsTextField.setColumns(5);
        this.insertDeadLineTextField.setColumns(5);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(3, 0));
        this.insertRequestsbutton.setText("Send");
        this.pickClientPanel.add(this.pickClientLabel);
        this.pickClientPanel.add(this.insertIdClientTextField);
        this.getContentPane().add(this.pickClientPanel);
        this.pickClientPanel.add(this.insertRequestsLabel);
        this.pickClientPanel.add(this.insertRequestsTextField);
        this.pickClientPanel.add(this.insertDeadLineLabel);
        this.pickClientPanel.add(this.insertDeadLineTextField);
        this.pickClientPanel.add(this.insertRequestsbutton);
        this.getContentPane().add(this.pickClientPanel);
        this.pendingRequestsPanel.add(this.pendingRequestsLabel);
        this.pendingRequestsPanel.add(this.pendingRequestsTextArea);
        this.getContentPane().add(this.pendingRequestsPanel);
        this.executedRequestsPanel.add(this.executedRequestsLabel);
        this.executedRequestsPanel.add(this.executedRequestsTextArea);
        this.getContentPane().add(this.executedRequestsPanel);
        this.insertRequestsbutton.addActionListener(this);
        this.setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ClientId = insertIdClientTextField.getText();
        String N_requests = insertRequestsTextField.getText();
        String deadline = insertDeadLineTextField.getText();
        data = null;
        if ((!insertDeadLineTextField.getText().isEmpty()) && (!insertRequestsTextField.getText().isEmpty())) {
            if (Integer.parseInt(ClientId) > 0) {
                if (Integer.parseInt(N_requests) > 0) {
                    if (Integer.parseInt(deadline) > 0) {
                        data = ClientId + ":" + N_requests + ":" + deadline;
                        insertIdClientTextField.setText("");
                        insertRequestsTextField.setText("");
                        insertDeadLineTextField.setText("");
                        data_sent = true;
                    } else {
                        System.out.println("Value of Deadline must be higher than 0");
                    }
                } else {
                    System.out.println("Number of requests must be higher than 0");
                }
            } else {
                System.out.println("Value of Client Id must be higher than 0");
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
        pendingRequestsTextArea.setText(request);
    }
    
    public void setExecutedRequests(String request){
        executedRequestsTextArea.append(request);
    }
}
