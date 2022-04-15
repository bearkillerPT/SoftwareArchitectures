/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Thread representing one Entity
 */
package HCP.Main;


/**
 *
 * @author user
 */
public class HCP {
    private String sim_status = "Stopped";
    private int total_adult_patients;
    private int total_children_patients;
    private int total_seats;
    private int evt;
    private int mdt;
    private int pyt;
    private int time_to_move;
    public HCP( int total_adult_patients,int total_children_patients,int total_seats,int evt,int mdt,int pyt,int time_to_move){
        this.total_adult_patients = total_adult_patients;
        this.total_children_patients = total_children_patients;
        this.total_seats = total_seats;
        this.evt = evt;
        this.mdt = mdt;
        this.pyt = pyt;
        this.time_to_move = time_to_move;
    }
}
