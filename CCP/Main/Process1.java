/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CCP.Main;
import java.util.concurrent.TimeUnit;
import CCP.Communication.Client;
import CCP.Main.ControlGUI;
/**
 *
 * @author user
 */
public class Process1 {

    private ControlGUI CCPGui;
    private Client CCPClient;

    public Process1() {
        this.CCPGui = new ControlGUI();
        this.CCPClient = new Client();
        while(CCPGui.getSimState() != "Running")
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        catch (Exception e) {
            System.out.println("Something went wrong before sending the Configuration!");
        }
        CCPClient.sendCommand(CCPGui.getConfiguration());
        System.out.println("Settings Config Sent!");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Process1 p = new Process1();
    }
        
}
