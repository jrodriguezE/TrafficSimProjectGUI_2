/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author paleo
 */
public class CarInfoFrame extends JFrame
{
    Auto auto= new Auto();
    JTextField idField = new JTextField(8);
    JTextField longField = new JTextField(8);
    JTextField latField = new JTextField(8);
    JTextField velocityField = new JTextField(8);
    JTextArea carArea = new JTextArea(7,12);
    JTextArea conArea = new JTextArea(7,12);
    JRadioButton stopYes = new JRadioButton("true");
    JRadioButton stopNo = new JRadioButton("false");        
    ButtonGroup stopButtons = new ButtonGroup();
     ButtonGroup CloseButton = new ButtonGroup();
    JTextField waypointField = new JTextField(16);
    JTextField targetField = new JTextField(16);
        
    CarInfoFrame()
    {
        setIconImage(new ImageIcon("./src/car4.png").getImage());

        //this.setUndecorated(true);
        this.setSize(450,350); // regular stuff for jframe 
       // Color black = Color.decode("#")
        this.setBackground(new Color(102,102,102));//Color.getColor("#3333"));
//        this.setDefaultCloseOperation(0);
//        this.setUndecorated(true);
        
        this.setTitle("Car Info");
//        this.setLocation(0,55);
//        this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());

        JLabel idLabel = new JLabel("ID:");
        idLabel.setForeground(new Color(242,242,242));
        idField = new JTextField(8);
        idField.setEnabled(false);
        
        JLabel longLabel = new JLabel("Long:");
        longLabel.setForeground(new Color(242,242,242));
        longField = new JTextField(8);
        longField.setEnabled(false);
        
        JLabel latLabel = new JLabel("Lat:");
        latLabel.setForeground(new Color(242,242,242));
        latField = new JTextField(8);
        latField.setEnabled(false);
        
        JLabel conLabel = new JLabel("Connects:");
        JLabel carsLabel = new JLabel("Cars:");
        
        JPanel displayPanel = new JPanel();
        
        //displayPanel.setBackground(new Color(0,0,0));
        //displayPanel.setOpaque(false);
        displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        displayPanel.add(idLabel);
        displayPanel.add(idField);
        
        JLabel velocityLabel = new JLabel("Velocity:");
        displayPanel.add(longLabel);
        displayPanel.add(longField);
        displayPanel.add(latLabel);
        displayPanel.add(latField);
        displayPanel.add(velocityLabel);
        velocityField.setEditable(false);
        displayPanel.add(velocityField);
        displayPanel.add(waypointField);
        displayPanel.add(targetField);
        this.add(displayPanel, BorderLayout.WEST);


//
        JLabel stopLabel = new JLabel("Accident? ");
        stopYes = new JRadioButton("true");
        stopNo = new JRadioButton("false");
        stopYes.addActionListener(new RadioButtonListener());
        stopNo.addActionListener(new RadioButtonListener());
        
        stopButtons = new ButtonGroup();
        stopButtons.add(stopYes);
        stopButtons.add(stopNo); 
       
        JPanel stopPanel = new JPanel();
        stopPanel.add(stopLabel,BorderLayout.NORTH);
        stopPanel.add(stopYes,BorderLayout.CENTER);
        stopPanel.add(stopNo,BorderLayout.CENTER);
//        this.add(conPanel,BorderLayout.CENTER);
//        this.add(carPanel,BorderLayout.CENTER);
        this.add(stopPanel,BorderLayout.SOUTH);        
        
//        this.add(refLabel);
//        JButton nodeToolButton = new JButton("delete");
//        nodeToolButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
////                new NodeInfoFrame();
//            }
//        });
//        this.add(nodeToolButton, BorderLayout.NORTH);
        this.setVisible(true);
        //this.setOpacity(0.85f);
        this.setAlwaysOnTop(true);
    }
    
    // use this to give window the Node 
    // once you got the node fill all data about it
    // in this window.
    public void setAuto(Auto auto)
    {
        this.auto=auto;
    }
    
    
//            JTextField refField = new JTextField(6);
//        JTextField longField = new JTextField(6);
//        JTextField latField = new JTextField(6);
    public void setFields()
    {

        idField.setText( Long.toString(auto.posNode.getRef()));
        longField.setText(Double.toString(auto.posNode.getLong()));
        latField.setText(Double.toString(auto.posNode.getLat()));
        String output = "";
        
        for(int i=0;i<auto.posNode.connections.size();i++)
        {
            output=output+Long.toString(auto.posNode.connections.get(i).getRef())+"\n";
        }
        conArea.setText(output);
        
        output="";
        
        for(int i=0;i<auto.posNode.cars.size();i++)
        {
            output=output+Long.toString(auto.posNode.cars.get(i).getRef())+"\n";
      
        }
        carArea.setText(output);
        
        if(auto.stop)
        {
            stopButtons.setSelected(stopYes.getModel(), true);

            
        }
        else
        {
            stopButtons.setSelected(stopNo.getModel(), true);
            
        }
        velocityField.setText(Double.toString(auto.velocity));
        waypointField.setText(Double.toString(auto.waypointNode.getLong())+" "+Double.toString(auto.waypointNode.getLat()));
        targetField.setText(Double.toString(auto.targetNode.getLong())+" "+Double.toString(auto.targetNode.getLat()));
        
    }
    //functions to edit the node
    //make functions to cycle through its connections.
    //maybe open another window that has info on cars connected to a node.
    //display graphs and charts
    
    private class RadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==stopYes){
                auto.stop=true;
            }
            if(e.getSource()==stopNo ){
                auto.stop=false;
            }
        }
        
    }
    
}
