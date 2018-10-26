/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 *
 * @author paleo
 */
public class TimeFrame extends JFrame{
    JLabel time = new JLabel();
    boolean changeTime = false;
    JSlider timeSlide;
    JButton setTime;
    int hour=0;
    TimeFrame(){

        this.setSize(700,120); // regular stuff for jframe 
        this.setLayout(new BorderLayout());
        this.setTitle("Time");
        
        this.add(time);
        timeSlide = new JSlider(JSlider.HORIZONTAL,0,24,6);
        timeSlide.setPaintTicks(true);
        timeSlide.setMinorTickSpacing(1);
        timeSlide.setPaintTicks(true);
        timeSlide.setPaintLabels(true);
        timeSlide.setLabelTable(timeSlide.createStandardLabels(1));

        //ChangeEvent event = new ChangeEvent();
        //timeSlide.addChangeListener(event);

        this.add(timeSlide,BorderLayout.NORTH);
        setTime = new JButton("Set");
        setTime.setPreferredSize(new Dimension(70, 20));
        setTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                changeTime=true;
                hour=timeSlide.getValue();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(setTime);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }
    
    void setTime(String strTime){
        time.setText(strTime);
    }
    
    // use this to give window the Node 
    // once you got the node fill all data about it
    // in this window.
    
//    private class ChangeEvent implements ChangeListener{
//
//        @Override
//        public void stateChanged(javax.swing.event.ChangeEvent e) {
//            hour=timeSlide.getValue();
//        }
//    
//}

}

