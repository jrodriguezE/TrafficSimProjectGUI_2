/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author paleo
 */
public class Renderer extends JFrame
{
    
    int playSpeed = 1;
    DrawStuff drawer = new DrawStuff();
    ArrayList<Road> roads = new ArrayList<>();
    ArrayList<Auto> cars = new ArrayList<>();
    ArrayList<Shape> roadShapes = new ArrayList<Shape>();
    ArrayList<Shape> intersectShapes = new ArrayList<Shape>();
    ArrayList<Shape> carShapes = new ArrayList<Shape>();
//    MouseHandler mouseHandler;
    //send a Nd to this with mouse! check that this is not null first. 
    NodeInfoFrame nodeInfoFrame;
    CarInfoFrame carsPanel;



    double mouseLong=0;
    double mouseLat=0;
    int mouseOffSetX=0;
    int mouseOffSetY=0;

    //drawRoad = new Line2D.Float(lons.get(i)/scale,lats.get(i)/scale,lons.get(i+1)/scale,lats.get(i+1)/scale);
    
    //for adjusting map
    double scale = 1;
   
    
    Renderer(double scale, ArrayList<Road> roads, double minLat, double maxLat, double minLon, double maxLon){
        getContentPane().setBackground(new Color (0,0,0));
        this.scale=scale;
        this.roads=roads;
        this.minLat=minLat;
        this.minLon=minLon;
        this.maxLon=maxLon;
        this.maxLat=maxLat;// i made these global because of testing. sys out.prints are all debug testing related
        
        drawWindow();
//        latRange = (int)((maxLat-minLat)*scale1);// calculate the size of the window
//        lonRange = (int)((maxLon- minLon)*scale1);
//        System.out.println("SIZE OF WINDOW: " + lonRange + " " +latRange );
//        this.setSize(lonRange,latRange); // regular stuff for jframe 
//        
//        this.setTitle("Traffic Sim");
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.add(new DrawStuff(), BorderLayout.CENTER);
//        this.setVisible(true);
        
        
    }
    
    public void setScale(double scale)
    {
        this.scale = scale;
        roadShapes = new ArrayList<>();
        intersectShapes=new ArrayList<>();
//        reSizeWindow();
        setMap();
        drawer.repaint();
        //drawWindow();
        
    }    
    //for transforming the long&lat to pixels
    

    int scale1 = 100000;
    
    
    public void stepCars(ArrayList<Auto> cars)
    {
        this.cars=cars;
        drawer.repaint();
        //drawWindow();
    }
    
    private double latToGrid(double lat)
    {
        Double y1=(-lat+maxLat)*scale1/scale+mouseOffSetY;
        
        return y1;        
    }
    private double longToGrid(double lon)
    {
        Double x1=(lon-minLon)*scale1/scale+mouseOffSetX;
        return x1;
    }
    
    public void stepCarsOLD(ArrayList<Auto> cars)
    {
        carShapes = new ArrayList<Shape>();
        for(int i =0; i<cars.size();i++)
        {
            Double x1=(cars.get(i).posNode.getLong()-minLon)*scale1;
            Double y1=(cars.get(i).posNode.getLat()-minLat)*scale1;
        
            Shape carPos = new Rectangle2D.Double(x1/scale-10,y1/scale-10,20,20);
            carShapes.add(carPos);
        }
//        drawer.repaint();
        drawWindow();
    }
    
    
    public void setMap()
    {
        roadShapes=new ArrayList<Shape>();

        for(int i = 0; i<roads.size();i++)
        {

            for(int j = 0 ; j<roads.get(i).nodeList.size()-1;j++)
            {
                Double y1=latToGrid(roads.get(i).nodeList.get(j).getLat());
                Double x1=longToGrid(roads.get(i).nodeList.get(j).getLong());
                Double y2=latToGrid(roads.get(i).nodeList.get(j+1).getLat());
                Double x2=longToGrid(roads.get(i).nodeList.get(j+1).getLong());

                
                
                
//                Double y1=(-roads.get(i).nodeList.get(j).getLat()+maxLat)*scale1;
//                Double x1=(roads.get(i).nodeList.get(j).getLong()-minLon)*scale1;
//                Double y2=(-roads.get(i).nodeList.get(j+1).getLat()+maxLat)*scale1;
//                Double x2=(roads.get(i).nodeList.get(j+1).getLong()-minLon)*scale1;
                
                Shape tempRoad = new Line2D.Double(x1,y1,x2,y2);
//                AlphaComposite alpha = new AlphaComposite();
                roadShapes.add(tempRoad);
                
            }
//            for(int j=0; j<roads.get(i).intersections.size();j++){
//                Double x1 = (roads.get(i).intersections.get(j).getLong()-minLon)*scale1;
//                Double y1 = (roads.get(i).intersections.get(j).getLat()-minLat)*scale1;
//                
//
//                Shape tempCept = new Rectangle2D.Double(x1/scale,y1/scale,5,5);
//                intersectShapes.add(tempCept);
//            }
        }
        
//        System.out.println("minLon"+minLon);
//        System.out.println("maxLon"+maxLon);
//        System.out.println("minLat"+minLat);
//        System.out.println("maxLat"+maxLon);
    }
    

    
    
    Shape testCar = new Rectangle2D.Double(0,0,0,0);
    
    public void setAutoPos(Nd node)
    {
        Double x1=(node.getLong()-minLon)*scale1;
        Double y1=(node.getLat()-minLat)*scale1;
        
        testCar = new Rectangle2D.Double(x1/scale-10,y1/scale-10,20,20);
        
        drawWindow();
        
//        System.out.println("STEP");
    }

    
    // I was using these to test some things but a few of the functions
    // rely on them now. need to fix this shit so they are not global. 
    double minLat=0;
    double minLon=0;
    double maxLon=0;
    double maxLat =0;
    int latRange=0;
    int lonRange=0;
    
    
    //Graphics2D graph2;
    
    
    // this is the constructor... i was using it as a spot to 
    // test code in the class.it takes the values for max and min borders of
    // the whole map as parameters.
    
    

    public void reSizeWindow()
    {
        this.setSize((int)(((maxLon- minLon)*scale1)/scale),(int)
                (((maxLat-minLat)*scale1)/scale));
    }
    
    JLabel timeLabel = new JLabel(" Clock: 00:00:00 ");
   
    JLabel totalCar = new JLabel(" Completed: ");
    JLabel flowRate = new JLabel("   Flow:");
    TimeFrame timeFrame;
    RoadFrame roadFrame;
    
    boolean nodeButtonOn=false;
    boolean timeButtonOn=false;
    boolean carButtonOn=false;
    boolean roadButtonOn=false;
    boolean saveRoute=false;
    boolean saveMap=false;
    
   
    
    public void drawWindow()
    {
         UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial", Font.BOLD, 18));
            
        setIconImage(new ImageIcon("./src/mainMenu3.png").getImage());
        latRange = (int)((maxLat-minLat)*scale1);// calculate the size of the window
        lonRange = (int)((maxLon- minLon)*scale1);
        System.out.println("SIZE OF WINDOW: " + lonRange/scale + " " +latRange/scale );
        this.setSize(lonRange,latRange); // regular stuff for jframe 
//        System.out.println(lonRange+" "+latRange);
        this.setTitle("Traffic Sim");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(drawer, BorderLayout.CENTER);
        
        
        JPanel toolPanel = new JPanel();
        ////////////////////////////////////////////////////////
        JButton pauseButton;
//        Icon pauseIcon = new ImageIcon(getClass().getResource("pause4.png"));
        pauseButton = new JButton(new ImageIcon("./src/pause4.png"));
        //pauseButton = new JButton("Pause", pauseIcon);
        pauseButton.setToolTipText("Pause");
        pauseButton.setBackground(new Color(242,242,242));
        
        //pauseButton.setForeground(Color.white);
        pauseButton.setFocusPainted(false);
        pauseButton.setPreferredSize(new Dimension(125, 25));
        
        pauseButton.addActionListener((ActionEvent e) -> 
        {
            //                nodeInfoFrame=new NodeInfoFrame();
            playSpeed = 0;
        });
        //////////////////////////////////////////////////////
        JButton playButton;
        playButton = new JButton(new ImageIcon("./src/play4.png"));
        playButton.setToolTipText("Play");
        playButton.setBackground(new Color(242,242,242));
        
        playButton.setFocusPainted(false);
        playButton.setPreferredSize(new Dimension(125, 25));
        playButton.addActionListener((ActionEvent e) ->
        {
            
            playSpeed=1;
//                nodeInfoFrame=new NodeInfoFrame();
        });
        //////////////////////////////////////////////////
        JButton fastPlayButton;
        fastPlayButton = new JButton(new ImageIcon("./src/fast4.png"));
        fastPlayButton.setToolTipText("Fast");
        fastPlayButton.setBackground(new Color(242,242,242));
        fastPlayButton.setFocusPainted(false);
        fastPlayButton.setPreferredSize(new Dimension(125, 25));
        fastPlayButton.addActionListener((ActionEvent e) -> 
        {
            playSpeed++;
//                nodeInfoFrame=new NodeInfoFrame();
        });
        /////////////////////////////////////////////////////
        JButton saveRtButton;
        saveRtButton = new JButton(new ImageIcon("./src/save4.png"));
        saveRtButton.setToolTipText("Save");
        saveRtButton.setPreferredSize(new Dimension(125, 25));
        saveRtButton.setBackground(new Color(242,242,242));
        saveRtButton.setFocusPainted(false);
        
        saveRtButton.addActionListener((ActionEvent e) -> {
            saveRoute=true;
        });
        /////////////////////////////////////////////////////
        JButton saveMapButton;
        saveMapButton= new JButton(new ImageIcon("./src/saveMap.png"));
        //saveMapButton.setToolTipText("Save Map");
        saveMapButton.setBackground(new Color(242,242,242));
        saveMapButton.setToolTipText("Save Map");
        saveMapButton.setFocusPainted(false);
        saveMapButton.setPreferredSize(new Dimension(125, 25));
        saveMapButton.addActionListener((ActionEvent e) -> {
            saveMap=true;
        });
        /////////////////////////////////////////////////////
        JButton nodeToolButton;
        nodeToolButton = new JButton(new ImageIcon("./src/node4.png"));
        nodeToolButton.setToolTipText("Node Tool");
        nodeToolButton.setPreferredSize(new Dimension(125, 25));
        nodeToolButton.setBackground(new Color(242,242,242));
        nodeToolButton.setFocusPainted(false);
        //nodeToolButton.setBorderPainted(false);
        nodeToolButton.addActionListener((ActionEvent e) -> {
            //                nodeInfoFrame=new NodeInfoFrame();
            if(nodeButtonOn)
            {
                nodeButtonOn=false;
                nodeInfoFrame.dispose();
            }
            else
            {
                nodeInfoFrame=new NodeInfoFrame();
                nodeButtonOn=true;                              
            }
        });
        //////////////////////////////////////////////////////
        JButton roadButton;
        roadButton = new JButton(new ImageIcon("./src/road4.png"));
        roadButton.setToolTipText("Road");
        roadButton.setPreferredSize(new Dimension(125, 25));
        roadButton.setBackground(new Color(242,242,242));
        roadButton.setFocusPainted(false);
        //roadButton.setBorderPainted(false);
        roadButton.addActionListener((ActionEvent e) -> {
            //                nodeInfoFrame=new NodeInfoFrame();
            if(roadButtonOn)
            {
                roadButtonOn=false;
                roadFrame.dispose();
            }
            else
            {
                roadFrame=new RoadFrame();
                roadButtonOn=true;
//                    nodeButtonOn=false;
//                    nodeInfoFrame.dispose();
            }
        });
        //////////////////////////////////////////////////////
        JButton timeButton;
        timeButton = new JButton(new ImageIcon("./src/time4.png"));
        timeButton.setToolTipText("Time");
        timeButton.setBackground(new Color(242,242,242));
        timeButton.setPreferredSize(new Dimension(125, 25));
        timeButton.addActionListener((ActionEvent e) -> {
            if(timeButtonOn)
            {
                timeButtonOn=false;
                timeFrame.dispose();
            }
            else
            {
                timeFrame=new TimeFrame();
                timeButtonOn=true;                
            }
        });

        JButton carsButton;
        carsButton = new JButton(new ImageIcon("./src/car4.png"));
        carsButton.setPreferredSize(new Dimension(125, 25));
        carsButton.setToolTipText("Car");
        carsButton.setBackground(new Color(242,242,242));
        carsButton.setFocusPainted(false);
        
        carsButton.addActionListener((ActionEvent e) -> {
            if(carButtonOn)
            {
                carButtonOn=false;
                carsPanel.dispose();
            }
            else
            {
                carsPanel=new CarInfoFrame();
                
                carButtonOn=true;               
            }
        });
        
//        this.add(carToolButton, BorderLayout.NORTH); 
        toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,1));
        toolPanel.add(pauseButton);
        toolPanel.add(playButton);
        toolPanel.add(fastPlayButton);
        toolPanel.add(nodeToolButton);
        toolPanel.add(roadButton);
        toolPanel.add(carsButton);
//        toolPanel.add(optionsButton);
        toolPanel.add(saveRtButton);
        toolPanel.add(saveMapButton);
        toolPanel.add(timeButton);
        toolPanel.add(timeLabel);
        
        totalCar = new JLabel("    Completed: ");
        totalCar.setFont(new Font("Serif", Font.BOLD, 16));
        toolPanel.add(totalCar);
        flowRate = new JLabel("    Rate: ");
        flowRate.setFont(new Font("Serif", Font.BOLD, 16));
        toolPanel.add(flowRate);
        
        
        toolPanel.setPreferredSize(new Dimension(300,25));
//        toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,1));
        this.add(toolPanel, BorderLayout.NORTH);
        MouseHandler mouseHandler = new MouseHandler();
        this.addMouseListener(mouseHandler);
        this.addMouseWheelListener(mouseHandler);
        //ugh, add these buttons to a frame and then put on north of thie frame
        
        this.setVisible(true);
        

    }    
    // this draws stuff
    // 
    
    private double xToLong(double lon)
    {
        return ((lon-13)*scale)/scale1+minLon;
    }
    private double yToLat(double lat)
    {
        return -((lat-60)*scale)/scale1+maxLat;
    }
    
    boolean carSearchPlease=false;
    boolean nodeSearchPlease=false;
    boolean roadSearchPlease=false;
    boolean showClickSpot=true;
    
    private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener{
        


        @Override
        public void mouseClicked(MouseEvent e)
        {
//            System.out.println("clicked at, X="+e.getX()+" Y="+e.getY());
            mouseLong=((e.getX()-8-mouseOffSetX)*scale)/scale1+minLon;
            mouseLat=-((e.getY()-55-mouseOffSetY)*scale)/scale1+maxLat;

            if(carButtonOn)
            {
                carSearchPlease=true;
//                nodeSearchPlease = false;
//                nodeInfoFrame.dispose();
            }
            if (roadButtonOn)
            {
//                System.out.println("roadSearchOn");
                roadSearchPlease=true;
            }
            if(nodeButtonOn)
            {
                nodeSearchPlease = true;
                
            }
            
//            if(showClickSpot){
//                
////                System.out.println("moX="+e.getX()+" moY="+e.getY());
////                System.out.println("clX="+longToGrid(mouseLong)/scale+" clY="+latToGrid(mouseLat)/scale);
////                
//
//            }
            
            
        }

        int x1;
        int y1;
        @Override
        public void mousePressed(MouseEvent e)
        {
            if(SwingUtilities.isRightMouseButton(e))
            {
                x1=e.getX();
                y1=e.getY();
            }
        }


        @Override
        public void mouseReleased(MouseEvent e) 
        {
            if(SwingUtilities.isRightMouseButton(e))
            {
                mouseOffSetX+=-x1+e.getX();
                mouseOffSetY+=-y1+e.getY();
                setMap();
            }
        }


        @Override
        public void mouseEntered(MouseEvent e) {
       
        }


        @Override
        public void mouseExited(MouseEvent e) {
   
        }


        @Override
        public void mouseDragged(MouseEvent e)
        {
        if(SwingUtilities.isRightMouseButton(e)){
//                mouseOffSetX=-x1+e.getX();
//                mouseOffSetY=-y1+e.getY();
//                setMap();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) 
        {
            
        }


        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int wheelRot=e.getWheelRotation();
//            System.out.println("mouse="+wheelRot);
            if(wheelRot>0)
            {//zoom out

                scale++;

                    mouseOffSetX-=(int)((mouseOffSetX-e.getX()+8)/scale);
                    mouseOffSetY-=(int)((mouseOffSetY-e.getY()+55)/scale);
            }
            else if(wheelRot<0)
            {//zoom in



                if(scale-1<=0)
                {
                    scale=.5;
                    

                }
                else
                {
                    scale--;


                    mouseOffSetX+=(int)((mouseOffSetX-e.getX()+8)/scale);//8 and 55 are offsets
                    mouseOffSetY+=(int)((mouseOffSetY-e.getY()+55)/scale);
                    
                }                
            }            
            setMap();

        }
//        int wheelRot=0;
    }
    
    int carSize=6;
    private class DrawStuff extends JComponent
    {
        public void paint(Graphics g)
        {
            int halfCarSize=carSize/2;
            Graphics2D graph2 = (Graphics2D)g;
            

//            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                    RenderingHints.VALUE_ANTIALIAS_ON);

            graph2.setPaint(Color.DARK_GRAY);

//            BufferedImage test = new BufferedImage(graph2.);
            for(int i =0; i<roadShapes.size();i++)
            {  
                graph2.draw(roadShapes.get(i));                
            }
            
//            graph2.setPaint(Color.RED);
            
//            for(int i =0; i<carShapes.size();i++){
//                
//                graph2.draw(carShapes.get(i));
//
//                
//            }

//            carShapes = new ArrayList<Shape>();
            for(int i =0; i<cars.size();i++)
            {
//                Nd drawAuto = calcOffset(cars.get(i).lastWaypointNode,cars.get(i).posNode.getLong(),cars.get(i).posNode.getLat(),cars.get(i).waypointNode);
                Nd drawAuto = cars.get(i).posNode;                
                
                Double x1=longToGrid(drawAuto.getLong());
                Double y1=latToGrid(drawAuto.getLat());
        
                Shape carPos = new Rectangle2D.Double(x1-(halfCarSize)/scale,y1-(halfCarSize)/scale,carSize/scale,carSize/scale);
 //               carShapes.add(carPos);
                if(cars.get(i).stop)
                {
                    graph2.setPaint(Color.RED);
                }
                else
                    graph2.setPaint(Color.YELLOW);
                graph2.draw(carPos);
            }
            
            if(showClickSpot)
            {
                Shape mouseClick = new Rectangle2D.Double();
                if(roadButtonOn&&roadFrame.road!=null){
                    graph2.setPaint(Color.blue);
                    for(int ndInd=0;ndInd<roadFrame.road.nodeList.size();ndInd++){

                        mouseClick = new Rectangle2D.Double(longToGrid(roadFrame.road.nodeList.get(ndInd).getLong())-5,latToGrid(roadFrame.road.nodeList.get(ndInd).getLat())-5,10,10);
                        graph2.draw(mouseClick);
                    }
                }
                if(carButtonOn&&carsPanel.auto!=null){
                    graph2.setPaint(Color.GREEN);
                    mouseClick = new Rectangle2D.Double(longToGrid(carsPanel.auto.posNode.getLong())-12,latToGrid(carsPanel.auto.posNode.getLat())-12,24,24);
                    graph2.draw(mouseClick);
                }
                if(nodeButtonOn&&(nodeInfoFrame.node!=null)){
                    graph2.setPaint(Color.cyan);
                    mouseClick = new Rectangle2D.Double(longToGrid(nodeInfoFrame.node.getLong())-5,latToGrid(nodeInfoFrame.node.getLat())-5,10,10);
                    graph2.draw(mouseClick);
                }
                if(!carButtonOn&&!nodeButtonOn&&!roadButtonOn){
                    graph2.setPaint(Color.GREEN);
                    mouseClick = new Rectangle2D.Double(longToGrid(mouseLong)-5,latToGrid(mouseLat)-5,10,10);
                    graph2.draw(mouseClick);
                }
            }
//            for(int i=0; i<intersectShapes.size();i++){
//                graph2.setPaint(Color.RED);
//                graph2.draw(intersectShapes.get(i));
// //               System.out.println("*********************************************************DRAWING INTERCEPT");
// //               System.out.println("INTERSECT X: "+intersectShapes.get(i).getBounds().x);
// //               System.out.println("INTERSECT Y: "+intersectShapes.get(i).getBounds().y);
//            }
            
            //TEST CAR
//            graph2.setPaint(Color.GREEN);
//            graph2.draw(testCar);
            
//            System.out.println("SIZE OF WINDOW: " + (maxLon*100000-minLon*100000) + " " +(maxLat*100000-minLat*100000) );
            graph2.dispose();
            g.dispose();
            
        }
        
        private Nd calcOffset(Nd posNode,double x, double y, Nd endPoint){
            double px=posNode.getLong()-endPoint.getLong();
            double py=posNode.getLat()-endPoint.getLat();
        
            double nx=(-py);
            double ny= px;
            double norm = (Math.sqrt(nx*nx+ny*ny));
            nx=nx/norm;
            ny=ny/norm;
        
            Nd offNode = new Nd();
            double cx=x+.00003*nx;
            double cy=y+.00003*ny;
            offNode.setLong(cx);
            offNode.setLat(cy);
            return offNode;
        }

        public void paintComponent(Graphics g)
        {
            Graphics2D graph2 = (Graphics2D)g;
            

//            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                    RenderingHints.VALUE_ANTIALIAS_ON);

            
//            graph2.setPaint(Color.BLACK);
//
////            BufferedImage test = new BufferedImage(graph2.);
//            for(int i =0; i<roadShapes.size();i++){
//                
//                graph2.draw(roadShapes.get(i));
//
//                
//            }
            
            graph2.setPaint(Color.GREEN);
            
//            for(int i =0; i<carShapes.size();i++){
//                
//                graph2.draw(carShapes.get(i));
//
//                
//            }

//            carShapes = new ArrayList<Shape>();
            for(int i =0; i<cars.size();i++){
                Double x1=longToGrid(cars.get(i).posNode.getLong());
                Double y1=latToGrid(cars.get(i).posNode.getLat());
        
                Shape carPos = new Rectangle2D.Double((x1/scale-5),(y1/scale-5),10/scale,10/scale);
 //               carShapes.add(carPos);
                graph2.draw(carPos);
            }


            
//            for(int i=0; i<intersectShapes.size();i++){
//                graph2.setPaint(Color.RED);
//                graph2.draw(intersectShapes.get(i));
// //               System.out.println("*********************************************************DRAWING INTERCEPT");
// //               System.out.println("INTERSECT X: "+intersectShapes.get(i).getBounds().x);
// //               System.out.println("INTERSECT Y: "+intersectShapes.get(i).getBounds().y);
//            }
            
            //TEST CAR
//            graph2.setPaint(Color.GREEN);
//            graph2.draw(testCar);
            
//            System.out.println("SIZE OF WINDOW: " + (maxLon*100000-minLon*100000) + " " +(maxLat*100000-minLat*100000) );
            graph2.dispose();
            g.dispose();
            
        }
    } 
}