package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import models.DisplayInfo;

public class DisplayGUI {

    private JFrame frame;
    private DynamicPanelList events;
    
    public DisplayGUI(String channelName){
        frame = new JFrame("Figbot - " + channelName);
        events = new DynamicPanelList();
        frame.setSize(new Dimension(900, 900));
        frame.getContentPane().setPreferredSize(new Dimension(900, 900));            
        frame.setLocationRelativeTo(null);         
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //events.setBackground(Color.BLACK);
        frame.add(events);
    }

    
    public void newEvent(DisplayInfo info){
        events.addEvent(info);
    }

    //TODO borrar?
    public static void main (String [] args){
        new DisplayGUI("rayo106");
    }
}
