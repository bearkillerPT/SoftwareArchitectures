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

    JLabel insertNILabel = new JLabel("Insert nº iterations");
    JTextField insertNITextField = new JTextField();
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
        this.insertNITextField.setColumns(5);
        this.insertDeadLineTextField.setColumns(5);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(3, 0));
        this.insertRequestsbutton.setText("Send");
        this.getContentPane().add(this.pickClientPanel);
        this.pickClientPanel.add(this.insertNILabel);
        this.pickClientPanel.add(this.insertNITextField);
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
        pendingRequestsTextArea.setText(request);
    }
    
    public void setExecutedRequests(String request){
        executedRequestsTextArea.append(request);
    }
}
