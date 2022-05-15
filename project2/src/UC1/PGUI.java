/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UC1;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author gil-t
 */
public class PGUI extends JFrame {
    private int records_count;
    HashMap<String, Integer> records_by_sensor_id;
    JPanel recordsPanel = new JPanel();
    JPanel totalRecordsPanel = new JPanel();
    JPanel totalRecordBySensorIdPanel = new JPanel();
    JLabel recordsLabel = new JLabel("Records:");
    JLabel totalRecordsLabel = new JLabel("Total number of Records:");
    JLabel totalRecordBySensorIdLabel = new JLabel("Total number of Records by sensor id:");
    JTextArea logs = new JTextArea(5, 50);
    JLabel totalRecordsValueLabel = new JLabel("0");
    JTextArea totalRecordBySensorId = new JTextArea(5, 50);
    JScrollPane scrollLogs = new JScrollPane(logs,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            
    JScrollPane scrollTotalRecordBySensorId = new JScrollPane(totalRecordBySensorId,
    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    public PGUI(String title) {
        this.records_count = 0;
        this.records_by_sensor_id = new HashMap();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Producer" + title + " GUI");
        this.setLayout(new GridLayout(2, 1));
        this.recordsPanel.add(this.recordsLabel);
        this.recordsPanel.add(this.scrollLogs);
        this.logs.setEditable(false);
        this.totalRecordBySensorId.setEditable(false);
        this.totalRecordsPanel.add(this.totalRecordsLabel);
        this.totalRecordsPanel.add(this.totalRecordsValueLabel);


        this.totalRecordBySensorIdPanel.add(this.totalRecordBySensorIdLabel);
        this.totalRecordBySensorIdPanel.add(this.scrollTotalRecordBySensorId);
        
        this.getContentPane().add(this.recordsPanel);
        this.getContentPane().add(this.totalRecordsPanel);
        this.getContentPane().add(this.totalRecordBySensorIdPanel);

        this.setVisible(true);
        pack();
    }

    public void addRecordsBySensorId(String key, String value) {
        this.logs.setText(this.logs.getText() + key + ":" + value + "\n");
        this.records_count++;
        this.totalRecordsValueLabel.setText("" + this.records_count);
        this.logs.setCaretPosition(this.logs.getDocument().getLength());
        if (this.records_by_sensor_id.containsKey(key)) {
            this.records_by_sensor_id.put(key, this.records_by_sensor_id.get(key)+1);
        } else {
            this.records_by_sensor_id.put(key, 1);
        }
        this.totalRecordBySensorId.setText(this.records_by_sensor_id.toString()+ "\n");
        this.totalRecordBySensorId.setCaretPosition(this.totalRecordBySensorId.getDocument().getLength());
        
    }
}
