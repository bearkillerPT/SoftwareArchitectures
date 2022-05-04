/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Thread representing one Entity
 */
package HCP.Main;

import HCP.Monitor.METH;
import HCP.Monitor.MEVH;
import HCP.Monitor.MMDW;
import HCP.Monitor.MPYH;
import HCP.Monitor.MWTH;

/**
 *
 * @author Gil Teixeira
 */
/**
 * The HCP represents a Health Center containing the multiple Halls, as monitors, and containing state relevant to the simulation
* @param total_adult_patients Number of adult patients
* @param total_children_patients Number of children patients
* @param total_seats Number of seats un each Hall
* @param evt Evaluation time 
* @param mdt Medical Apointment time
* @param pyt Payment time
* @param time_to_move Time between halls or rooms for any Patient
  
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
    private METH mEth; 
    private MEVH mEvh; 
    private MWTH mWth; 
    private MMDW mMdw; 
    private MPYH mPyh; 

    public HCP( int total_adult_patients,int total_children_patients,int total_seats,int evt,int mdt,int pyt,int time_to_move){
        this.total_adult_patients = total_adult_patients;
        this.total_children_patients = total_children_patients;
        this.total_seats = total_seats;
        this.evt = evt;
        this.mdt = mdt;
        this.pyt = pyt;
        this.time_to_move = time_to_move;
        this.mEth = new METH(total_seats/2);
        this.mEvh = new MEVH();
        this.mWth = new MWTH(total_seats/2);
        this.mMdw = new MMDW();
        this.mPyh = new MPYH();
    }

    public METH getMEth(){
        return this.mEth;
    }
    public MEVH getMEvh(){
        return this.mEvh;
    }
    public MWTH getMWth(){
        return this.mWth;
    }
    public MMDW getMMdw(){
        return this.mMdw;
    }
    public MPYH getMPyh(){
        return this.mPyh;
    }

    public void setSimStatus(String status) {
        this.sim_status = status;
      
    }
    public int getTotalAdults() {
        return this.total_adult_patients;
    }
    public int getTotalChildren() {
        return this.total_children_patients;
    }

    public int getEVT(){
        return this.evt;
    }
    public int getMDT(){
        return this.mdt;
    }
    public int getPYT(){
        return this.pyt;
    }
}
