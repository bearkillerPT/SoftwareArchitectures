package Process1.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The GUI to control the CPP and it's settings
 */
public class ControlGUI extends JFrame {
    /* State Variables */
    private int total_adult_patients;
    private int total_children_patients;
    private int total_seats;
    private int evt;
    private int mdt;
    private int pyt;
    private int time_to_move;

    /* UI */
    JPanel text_inputs_panel;

    JButton ccp_mode_button = new JButton("Auto");
    JButton allow_costumer_button = new JButton("Allow Costumer");

    JPanel ui_total_adult_patients_panel;
    JTextField ui_total_adult_patients_input;
    JLabel ui_total_adult_patients_label;

    JPanel ui_total_children_patients_panel;
    JTextField ui_total_children_patients_input;
    JLabel ui_total_children_patients_label;

    JPanel ui_total_seats_panel;
    JTextField ui_total_seats_input;
    JLabel ui_total_seats_label;

    JPanel ui_evt_panel;
    JTextField ui_evt_input;
    JLabel ui_evt_label;

    JPanel ui_mdt_panel;
    JTextField ui_mdt_input;
    JLabel ui_mdt_label;

    JPanel ui_pyt_panel;
    JTextField ui_pyt_input;
    JLabel ui_pyt_label;

    JPanel ui_time_to_move_panel;
    JTextField ui_time_to_move_input;
    JLabel ui_time_to_move_label;

    /**
     * The GUI constructor intializes state to default values and creates the ui
     * components
     */
    public ControlGUI() {
        super("CPP GUI");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Mode Control
        ccp_mode_button.setBackground(Color.green);
        ccp_mode_button.setForeground(Color.white);
        allow_costumer_button.setBackground(Color.blue);
        allow_costumer_button.setForeground(Color.white);
        ccp_mode_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ccp_mode_button.getText().equals("Auto")) {
                    ccp_mode_button.setBackground(Color.red);
                    ccp_mode_button.setText("Manual");
                    text_inputs_panel.add(allow_costumer_button);

                    pack();
                } else {
                    ccp_mode_button.setBackground(Color.green);
                    ccp_mode_button.setText("Auto");
                    text_inputs_panel.remove(allow_costumer_button);
                    pack();
                }
            }
        });
        /* State Variables Initialization */
        this.total_adult_patients = 10;
        this.total_adult_patients = 10;
        this.total_seats = 4;
        this.evt = 0;
        this.mdt = 0;
        this.pyt = 0;
        this.time_to_move = 100;

        /* UI Variables Initialization */
        this.text_inputs_panel = new JPanel();
        this.text_inputs_panel.setLayout(new FlowLayout());


        this.ui_total_adult_patients_label = new JLabel("Number of Adult Patients");
        this.ui_total_children_patients_label = new JLabel("Number of Children Patients");
        this.ui_total_seats_label = new JLabel("Number of Seats");
        this.ui_evt_label = new JLabel("EVT");
        this.ui_mdt_label = new JLabel("MDT");
        this.ui_pyt_label = new JLabel("PYT");
        this.ui_time_to_move_label = new JLabel("Time to Move");
        this.ui_total_adult_patients_panel = new JPanel();
        ui_total_adult_patients_panel.setBackground(Color.decode("#494949"));
        ui_total_adult_patients_label.setForeground(Color.white);
        this.ui_total_children_patients_panel = new JPanel();
        ui_total_children_patients_panel.setBackground(Color.decode("#494949"));
        ui_total_children_patients_label.setForeground(Color.white);
        this.ui_total_seats_panel = new JPanel();
        ui_total_seats_panel.setBackground(Color.decode("#494949"));
        ui_total_seats_label.setForeground(Color.white);
        this.ui_evt_panel = new JPanel();
        ui_evt_panel.setBackground(Color.decode("#494949"));
        ui_evt_label.setForeground(Color.white);
        this.ui_mdt_panel = new JPanel();
        ui_mdt_panel.setBackground(Color.decode("#494949"));
        ui_mdt_label.setForeground(Color.white);
        this.ui_pyt_panel = new JPanel();
        ui_pyt_panel.setBackground(Color.decode("#494949"));
        ui_pyt_label.setForeground(Color.white);
        this.ui_time_to_move_panel = new JPanel();
        ui_time_to_move_panel.setBackground(Color.decode("#494949"));
        ui_time_to_move_label.setForeground(Color.white);

        this.ui_total_adult_patients_panel.setLayout(new BorderLayout());
        this.ui_total_children_patients_panel.setLayout(new BorderLayout());
        this.ui_total_seats_panel.setLayout(new BorderLayout());
        this.ui_evt_panel.setLayout(new BorderLayout());
        this.ui_mdt_panel.setLayout(new BorderLayout());
        this.ui_pyt_panel.setLayout(new BorderLayout());
        this.ui_time_to_move_panel.setLayout(new BorderLayout());



        this.ui_total_adult_patients_input = new JTextField("" + total_adult_patients, 10);
        this.ui_total_children_patients_input = new JTextField("" + total_children_patients, 10);
        this.ui_total_seats_input = new JTextField("" + total_seats, 10);
        this.ui_evt_input = new JTextField("" + evt, 10);
        this.ui_mdt_input = new JTextField("" + mdt, 10);
        this.ui_pyt_input = new JTextField("" + pyt, 10);
        this.ui_time_to_move_input = new JTextField("" + time_to_move, 10);

        this.ui_total_adult_patients_panel.add(ui_total_adult_patients_label, BorderLayout.NORTH);
        this.ui_total_adult_patients_panel.add(ui_total_adult_patients_input, BorderLayout.SOUTH);
        this.ui_total_children_patients_panel.add(ui_total_children_patients_label, BorderLayout.NORTH);
        this.ui_total_children_patients_panel.add(ui_total_children_patients_input, BorderLayout.SOUTH);
        this.ui_total_seats_panel.add(ui_total_seats_label, BorderLayout.NORTH);
        this.ui_total_seats_panel.add(ui_total_seats_input, BorderLayout.SOUTH);
        this.ui_evt_panel.add(ui_evt_label, BorderLayout.NORTH);
        this.ui_evt_panel.add(ui_evt_input, BorderLayout.SOUTH);
        this.ui_mdt_panel.add(ui_mdt_label, BorderLayout.NORTH);
        this.ui_mdt_panel.add(ui_mdt_input, BorderLayout.SOUTH);
        this.ui_pyt_panel.add(ui_pyt_label, BorderLayout.NORTH);
        this.ui_pyt_panel.add(ui_pyt_input, BorderLayout.SOUTH);
        this.ui_time_to_move_panel.add(ui_time_to_move_label, BorderLayout.NORTH);
        this.ui_time_to_move_panel.add(ui_time_to_move_input, BorderLayout.SOUTH);

        this.text_inputs_panel.add(ui_total_adult_patients_panel);
        this.text_inputs_panel.add(ui_total_children_patients_panel);
        this.text_inputs_panel.add(ui_total_seats_panel);
        this.text_inputs_panel.add(ui_evt_panel);
        this.text_inputs_panel.add(ui_mdt_panel);
        this.text_inputs_panel.add(ui_pyt_panel);
        this.text_inputs_panel.add(ui_time_to_move_panel);
        this.text_inputs_panel.add(ccp_mode_button);

        this.getContentPane().add(this.text_inputs_panel);
        text_inputs_panel.setBackground(Color.decode("#282c34"));
    }
}
