package Project3;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 *
 * @author gil-t
 */
public class Message implements Comparable<Message>{

    public int client_id;
    public int request_id;
    public int server_id;
    public int number_iteractions;
    public int message_code;
    public double result;
    public int deadline;

    public Message(int client_id, int request_id, int server_id, int number_iteractions, int message_code, double result, int deadline) {
        this.client_id = client_id;
        this.request_id = request_id;
        this.server_id = server_id;
        this.number_iteractions = number_iteractions;
        this.message_code = message_code;
        this.result = result;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "| " + this.client_id + " | " + this.request_id + " | " + this.server_id + " | " + this.number_iteractions + " | "
                + this.message_code + " | " + this.result + " | " + this.deadline + " | ";
    }

    public static Message parseMessage(String message) {
        ArrayList<String> message_fields = new ArrayList<>();
        for (String field : message.split(" ")) {
            if (!(field.equals("")) && !(field.equals("|")))
                message_fields.add(field);
        }
        for (String field : message_fields) {
            System.out.println(field);
        }
        if (message_fields.size() == 7) {
            Message res = new Message(
                    Integer.parseInt(message_fields.get(0)),
                    Integer.parseInt(message_fields.get(1)),
                    Integer.parseInt(message_fields.get(2)),
                    Integer.parseInt(message_fields.get(3)),
                    Integer.parseInt(message_fields.get(4)),
                    Double.parseDouble(message_fields.get(5)),
                    Integer.parseInt(message_fields.get(6)));
            return res;
        }
        return null;
    }
    
    public static void main(String[] args) {
        /* Usage example 
        *  | client_id | request_id | 00 | 01 | NI | 00 | deadline |
        */
        Message res = Message.parseMessage(" | 1 | 1 | 00 | 01 | 10 | 00 | 20 |");
        if (res != null)
            System.out.println(res.toString());
    }

    @Override
    public int compareTo(Message other_msg) {
        return this.deadline - other_msg.deadline;
    }
}
