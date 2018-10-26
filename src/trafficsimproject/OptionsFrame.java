/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import javax.swing.JFrame;

/**
 *
 * @author paleo
 */
public class OptionsFrame extends JFrame {
    OptionsFrame(){

        this.setSize(400,700); // regular stuff for jframe 
        
        this.setTitle("Options");
        
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
    

    
}
