/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Thread representing one Entity
 */
package HCP.Entities;

import HCP.Monitor.Fifo;

/**
 *
 * @author user
 */
public class TPatient extends Thread {
    private final int tE1Id;                    // Thread Id
    private String priority;    
    private Fifo<TPatient> entrance_hall;                // Priority
    
    
    public TPatient(int tE1Id, Fifo<TPatient> entrance_hall) {
        this.tE1Id = tE1Id;
    }
    @Override
    public void run() {
        this.entrance_hall.put(this);
        
    }
    public void setPatientPriority(String priority){
        this.priority = priority; 
    }
}
