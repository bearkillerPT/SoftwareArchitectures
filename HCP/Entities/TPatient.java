/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Thread representing one Entity
 */
package Process1.Entities;

import Process1.Monitor.Manchester_Fifo;

/**
 *
 * @author user
 */
public class Patient extends Thread {
    private final int tE1Id;                    // Thread Id
    private String priority;                    // Priority
    
    
    public Patient(int tE1Id) {
        this.tE1Id = tE1Id;
    }
    @Override
    public void run() {
        // state machine for the Entity1 (Thread)
        // for example
        
    }
    public void setPatientPriority(String priority){
        this.priority = priority; 
    }
}
