package Project3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class GUIMonitor extends JFrame {

    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel mainPanel = new JPanel();

    JPanel requestsLBPanel = new JPanel();
    JLabel requestsLBLabel = new JLabel("Requests managed by the LB");
    JPanel requestsLBPanel2 = new JPanel();
    JTextArea requestsLBTextArea = new JTextArea(6, 25);
    JScrollPane scroll = new JScrollPane(requestsLBTextArea);

    JPanel pendingRequestsPanel = new JPanel();
    JPanel pendingRequestsPanel2 = new JPanel();
    JLabel requestsServerLabel = new JLabel("  Requests managed by servers");
    JTextArea requestsServerTextArea = new JTextArea(6, 25);
    
    JScrollPane scroll2 = new JScrollPane(requestsServerTextArea);

    JPanel stateserversPanel = new JPanel();
    JPanel stateserversPanel2 = new JPanel();
    JLabel stateserversLabel = new JLabel("State of servers");
    JTextArea stateserversTextArea = new JTextArea(11, 25);

    JPanel stateLbsPanel = new JPanel();
    JPanel stateLbsPanel2 = new JPanel();
    JLabel stateLbsLabel = new JLabel("State of lbs");
    JTextArea stateLbsTextArea = new JTextArea(3, 25);
    Map<Integer, String> map = new HashMap<Integer, String>();
    Map<Integer, String> map2 = new HashMap<Integer, String>();

    public String data = null;

    public GUIMonitor(String title) {
        timer.start();
        timer.setRepeats(true);
        for(int i = 0; i < 2; i++){
            map.put(i, "OFF");
        }
        for(int i = 0; i < 10; i++){
            map2.put(i, "OFF");
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title + " GUI");
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.requestsLBTextArea.setBorder(border);
        this.requestsServerTextArea.setBorder(border);
        this.stateserversTextArea.setBorder(border);
        this.stateLbsTextArea.setBorder(border);
        this.requestsLBPanel.add(this.requestsLBLabel);
        this.requestsLBPanel2.add(this.scroll);

        this.pendingRequestsPanel.add(this.requestsServerLabel);
        this.pendingRequestsPanel2.add(this.scroll2);

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

    public void setLBRequests(String request) {
        requestsLBTextArea.append(" " + request + "\n");
    }

    public void setServerStatus(String request) {
        map2.put(Integer.valueOf(request), "ON");
    }

    public void setLbStatus(String request) {
        map.put(Integer.valueOf(request), "ON");
    }

    public void setServerRequests(String request) {
        requestsServerTextArea.append(" "+request+"\n");
    }
    Timer timer = new Timer(30000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateLbsTextArea.setText("");
            stateserversTextArea.setText("");
            for(int i = 0; i < 2; i++){
                stateLbsTextArea.append("LB:"+i+":"+map.get(i)+"\n");
                map.put(i, "OFF");
            }
            for(int i = 0; i < 10; i++){
                stateserversTextArea.append("SERVER "+i+":"+map2.get(i)+"\n");
                map2.put(i, "OFF");
            }
        }

    });
}
