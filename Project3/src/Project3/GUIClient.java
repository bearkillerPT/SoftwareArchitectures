package Project3;

import java.awt.Button;
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

    JPanel insertRequestsPanel = new JPanel();
    JLabel insertRequestsLabel = new JLabel("Insert requests");
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
    public String data = null;

    public GUIClient(String title) {
        this.insertRequestsTextField.setColumns(5);
        this.insertDeadLineTextField.setColumns(5);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.setLayout(new GridLayout(3, 0));
        this.insertRequestsbutton.setText("Send");
        this.insertRequestsPanel.add(this.insertRequestsLabel);
        this.insertRequestsPanel.add(this.insertRequestsTextField);
        this.insertRequestsPanel.add(this.insertDeadLineLabel);
        this.insertRequestsPanel.add(this.insertDeadLineTextField);
        this.insertRequestsPanel.add(this.insertRequestsbutton);
        this.getContentPane().add(this.insertRequestsPanel);
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
        if ((!insertDeadLineTextField.getText().isEmpty()) && (!insertRequestsTextField.getText().isEmpty())) {
            data = insertDeadLineTextField.getText() +":"+ insertRequestsTextField.getText();
        }
    }
}
