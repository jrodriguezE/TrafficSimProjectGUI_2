package trafficsimproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
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
public class NodeInfoFrame extends JFrame
{
    Nd node= new Nd();
        JTextField refField = new JTextField(5);
        JTextField longField = new JTextField(5);
        JTextField latField = new JTextField(5);
        JTextField speedField = new JTextField(5);
        
        JTextArea carArea = new JTextArea(7,7);
        JTextArea conArea = new JTextArea(7,7);
        
        JRadioButton stopYes = new JRadioButton("true");
        JRadioButton stopNo = new JRadioButton("false");        
        ButtonGroup stopButtons = new ButtonGroup();
        
        JRadioButton lightYes = new JRadioButton("true");
        JRadioButton lightNo = new JRadioButton("false");        
        ButtonGroup lightButtons = new ButtonGroup();
        
        JRadioButton blockYes = new JRadioButton("true");
        JRadioButton blockNo = new JRadioButton("false");        
        ButtonGroup blockButtons = new ButtonGroup();
        
        
    NodeInfoFrame(){

        this.setSize(440,300); // regular stuff for jframe 
//        this.setDefaultCloseOperation(0);
//        this.setUndecorated(true);

        this.setTitle("Node Info");
//        this.setLocation(0,55);
//        this.setLayout(new BorderLayout());
        this.setLayout(new GridLayout());

        JLabel refLabel = new JLabel("ref:");
        refField = new JTextField(7);
//        refField.setEnabled(false);
        JLabel longLabel = new JLabel("long:");
        longField = new JTextField(7);
//        longField.setEnabled(false);
        JLabel latLabel = new JLabel("lat:");
        latField = new JTextField(7);
//        latField.setEnabled(false);
        
        JLabel conLabel = new JLabel("connects:");
        JLabel carsLabel = new JLabel("cars:");
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        displayPanel.add(refLabel);
        displayPanel.add(refField);
        
        JLabel speedLabel = new JLabel("speed:");
        speedField = new JTextField(7);
        speedField.setEnabled(false);
        JLabel speedEditLabel = new JLabel("edit speed");
        JTextField speedEditField = new JTextField(5);
        speedEditField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                node.speedLimit=Double.parseDouble(speedEditField.getText());
            }
        });
        
        displayPanel.add(longLabel);
        displayPanel.add(longField);
        displayPanel.add(latLabel);
        displayPanel.add(latField);
        displayPanel.add(speedLabel);
        displayPanel.add(speedField);
        displayPanel.add(speedEditLabel);
        displayPanel.add(speedEditField);
        
        this.add(displayPanel, BorderLayout.WEST);

        JPanel conPanel = new JPanel();
        conPanel.add(conLabel);
        conArea.setWrapStyleWord(true);
//        conArea.setEnabled(false);
        conPanel.add(conArea);

        JPanel carPanel = new JPanel();
        carPanel.add(carsLabel);
        carArea.setEnabled(false);
        carPanel.add(carArea);
        
        JLabel stopLabel = new JLabel("StopNode? ");
        stopYes = new JRadioButton("true");
        stopNo = new JRadioButton("false");
        stopYes.addActionListener(new RadioButtonListener());
        stopNo.addActionListener(new RadioButtonListener());
        
        stopButtons = new ButtonGroup();
        stopButtons.add(stopYes);
        stopButtons.add(stopNo);        
        JPanel stopPanel = new JPanel();
        stopPanel.add(stopLabel);
        stopPanel.add(stopYes);
        stopPanel.add(stopNo);
        
        JLabel lightLabel = new JLabel("StopLight? ");
        lightYes = new JRadioButton("true");
        lightNo = new JRadioButton("false");
        lightYes.addActionListener(new LightRadioButtonListener());
        lightNo.addActionListener(new LightRadioButtonListener());
        
        lightButtons = new ButtonGroup();
        lightButtons.add(lightYes);
        lightButtons.add(lightNo);        
        JPanel lightPanel = new JPanel();
        lightPanel.add(lightLabel);
        lightPanel.add(lightYes);
        lightPanel.add(lightNo);
        
        JLabel blockLabel = new JLabel("block? ");
        blockYes = new JRadioButton("true");
        blockNo = new JRadioButton("false");
        blockYes.addActionListener(new BlockRadioButtonListener());
        blockNo.addActionListener(new BlockRadioButtonListener());
        
        blockButtons = new ButtonGroup();
        blockButtons.add(blockYes);
        blockButtons.add(blockNo);        
        JPanel blockPanel = new JPanel();
        blockPanel.add(blockLabel);
        blockPanel.add(blockYes);
        blockPanel.add(blockNo);
        
        
        this.add(conPanel,BorderLayout.CENTER);
        this.add(carPanel,BorderLayout.CENTER);
        this.add(stopPanel,BorderLayout.SOUTH);        
        this.add(lightPanel,BorderLayout.SOUTH);
        this.add(blockPanel,BorderLayout.SOUTH);
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
    public void setNode(Nd node){
        this.node=node;
    }
    
    
//            JTextField refField = new JTextField(6);
//        JTextField longField = new JTextField(6);
//        JTextField latField = new JTextField(6);
    
    public void setFieldsOnce(){
        refField.setText( Long.toString(node.getRef()));
        longField.setText(Double.toString(node.getLong()));
        latField.setText(Double.toString(node.getLat()));
        String output = "";
        for(int i=0;i<node.connections.size();i++){
            output=output+"  "+Long.toString(node.connections.get(i).getRef())+"\n";
        }
        conArea.setText(output);
    }
    public void setFields(){

        
        String output="";
        for(int i=0;i<node.cars.size();i++){
            output=output+"  "+Long.toString(node.cars.get(i).getRef())+"\n";
      
        }
        carArea.setText(output);
        
        if(node.isStop){
            stopButtons.setSelected(stopYes.getModel(), true);

            
        }else{
            stopButtons.setSelected(stopNo.getModel(), true);
            
        }
        
        if(node.isStopLight){
            lightButtons.setSelected(lightYes.getModel(), true);

            
        }else{
            lightButtons.setSelected(lightNo.getModel(), true);
            
        }
        
        speedField.setText(Double.toString(node.speedLimit));
        if(node.blocked){
            blockButtons.setSelected(blockYes.getModel(),true);
        }else{
            blockButtons.setSelected(blockNo.getModel(),true);
        }
        
    }
    //functions to edit the node
    //make functions to cycle through its connections.
    //maybe open another window that has info on cars connected to a node.
    //display graphs and charts
    
    private class RadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==stopYes){
                node.isStop=true;
            }
            if(e.getSource()==stopNo ){
                node.isStop=false;
            }
        }
        
    }
    
    private class LightRadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==lightYes){
                node.isStopLight=true;
            }
            if(e.getSource()==stopNo ){
                node.isStopLight=false;
            }
        }
        
    }
    
    private class BlockRadioButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==blockYes){
                node.blocked=true;
            }
            if(e.getSource()==blockNo ){
                node.blocked=false;
            }
        }
        
    }
    
}