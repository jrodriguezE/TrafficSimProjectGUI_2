/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author paleo
 */
public class RoadFrame extends JFrame{
//    Nd node= new Nd();
    Road road;
    
    JTextField refField = new JTextField(10);
    JTextField speedField = new JTextField(5);
    JLabel isOneWay = new JLabel("OneWay? ");
    JLabel lanesLabel = new JLabel("Lanes: ");
    

        
    RoadFrame(){

        this.setSize(255,280); // regular stuff for jframe 
//        this.setDefaultCloseOperation(0);
//        this.setUndecorated(true);

        this.setTitle("Road Edit");
//        this.setLocation(0,55);
//        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout());

        JLabel refLabel = new JLabel("Rd ID:");
        refField = new JTextField(10);
        refField.setEnabled(false);
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        displayPanel.add(refLabel);
        displayPanel.add(refField);
        
        JLabel speedLabel = new JLabel("speed:");
        speedField = new JTextField(10);
        speedField.setEnabled(false);
        
        JLabel speedEditLabel = new JLabel("edit speed");
        JTextField speedEditField = new JTextField(10);
        speedEditField.setText("3.6E-4");
        speedEditField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                node.speedLimit=Double.parseDouble(speedEditField.getText());
                //TODO grab EACH node and change speed.
                for(int i=0;i<road.nodeList.size();i++){
                    road.nodeList.get(i).speedLimit=Double.parseDouble(speedEditField.getText());
                }


            }
        });
        JButton commitButton = new JButton("Update Speed");
        commitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                node.speedLimit=Double.parseDouble(speedEditField.getText());
                //TODO grab EACH node and change speed.
                for(int i=0;i<road.nodeList.size();i++){
                    road.nodeList.get(i).speedLimit=Double.parseDouble(speedEditField.getText());
                }


            }
        });
        JButton twoWayButton = new JButton("Make 2-Way");
        commitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                node.speedLimit=Double.parseDouble(speedEditField.getText());
                //TODO grab EACH node and change speed.
                road.oneWay=false;
                road.buildLocalIntercets();


            }
        });
        
        
        
        JButton oneLaneButton = new JButton("1 Lane");
        oneLaneButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                node.speedLimit=Double.parseDouble(speedEditField.getText());
                //TODO grab EACH node and change speed.
                road.numLanes=1;
                for(int i=0;i<road.nodeList.size();i++){
                    road.nodeList.get(i).numLanes=1;
                }


            }
        });
        JButton twoLaneButton = new JButton("2 Lane");
        twoLaneButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                node.speedLimit=Double.parseDouble(speedEditField.getText());
                //TODO grab EACH node and change speed.
                road.numLanes=2;
                for(int i=0;i<road.nodeList.size();i++){
                    road.nodeList.get(i).numLanes=2;
                }


            }
        });
        displayPanel.add(speedLabel);
        displayPanel.add(speedField);
        displayPanel.add(speedEditLabel);
        displayPanel.add(speedEditField);
        displayPanel.add(commitButton);
        displayPanel.add(lanesLabel);
        displayPanel.add(oneLaneButton);
        displayPanel.add(twoLaneButton);
//        displayPanel.add(twoWayButton);
        this.add(displayPanel, BorderLayout.WEST);
        
        
        
        
        JPanel wayPanel=new JPanel();
        wayPanel.add(isOneWay);
        wayPanel.add(twoWayButton);
        this.add(wayPanel);

        
        
//        this.add(refLabel);
//        JButton nodeToolButton = new JButton("delete");
//        nodeToolButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
////                new NodeInfoFrame();
//            }
//        });
//        this.add(nodeToolButton, BorderLayout.NORTH);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }
    
    // use this to give window the Node 
    // once you got the node fill all data about it
    // in this window.
//    public void setNode(Nd node){
//        this.node=node;
//    }
    
    public void setRoad(Nd node){
        road=node.parentRoad;
    }
    
    public void setFields(){
        if(road.nodeList.size()>0){
            refField.setText( Long.toString(road.nodeList.get(0).getRef()));
            isOneWay.setText("OneWay? "+road.oneWay);
            speedField.setText(Double.toString(road.nodeList.get(0).speedLimit));
        }
        lanesLabel.setText(Integer.toString(road.numLanes));
    }
    
    
    
    
    
}
