/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficsimproject;
import java.util.ArrayList;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 *
 * @author paleo
 */

// reads osm map and makes an ArrayList of the Roads, each Road contains 
// an ArrayList of Nd and second ArrayList of IntersectionNd. 
// each Nd has its node ID, longitude and lattitude. 
// IntersectNd extends Nd and includes a field to tell you what road it
// intersects with (the field is called secondaryRef). 
public class MapReader {

    // these were for testing 
    double minLat =0;
    double maxLat =0;
    double minLon =0;
    double maxLon=0;
    
    
    
//holds the list of  Roads of all the roads on the map   
    public ArrayList<Road> roads = new ArrayList<Road>();
//    int startIndex = 0;
// this holds all the nodes for use in search for coordinates 
// compared to Way refs
    NodeList allNodes;
    //docString is file name
    MapReader(String docString){
        
        
        // this reads the xml document into memory
        Document xmlDoc = getDocument(docString);
        
        // looks like testing stuff, ill clean it up later dont wanna break 
        // it right now
        String nodeName = xmlDoc.getDocumentElement().getNodeName();
        
        System.out.println("nodeName: " + nodeName);
        
        //
        //
        //This is a list of ALL <ways> on the map. the program
        // will refine this list to only the <ways> that are Roads
        NodeList listOfWays = xmlDoc.getElementsByTagName("way");
        
        // Getting the bounds from the file
        NodeList bounds = xmlDoc.getElementsByTagName("bounds");
        //seting bounds of map (max and min long and lat) so the renderer can use it later
        setBounds(bounds);
        
        // This list of nodes is searched against to find longitude and latitude
        // to link to the node IDs in our roads. 
        allNodes = xmlDoc.getElementsByTagName("node");
        
        
        System.out.println("Number of ways " + listOfWays.getLength());
        
        //NodeList listOfNodes =
        //
        // ******** This actually starts the real work. All the real work is in
        // getListOfNd
        // this shit is sloppy.TODO: tidy this up
        getListOfNd(listOfWays);
        
        //tells us how many roads we got. the process to get the number of
        // roads is in getListOfNd
        System.out.println("Number of Roads: " + roads.size());
        
        setIntersections();
    }

    // this is to set the min and max lats and longs
    private void setBounds(NodeList bounds){
        Element showElement = (Element)bounds.item(0);
        
        
        minLat=Double.parseDouble(showElement.getAttribute("minlat"));
        maxLat=Double.parseDouble(showElement.getAttribute("maxlat"));
        minLon=Double.parseDouble(showElement.getAttribute("minlon"));
        maxLon=Double.parseDouble(showElement.getAttribute("maxlon"));
        
        
        System.out.println("MINLAT:"+showElement.getAttribute("minlat"));
        System.out.println("MAXLAT:"+showElement.getAttribute("maxlat"));
        System.out.println("MINLON:"+showElement.getAttribute("minlon"));
        System.out.println("MAXLON:"+showElement.getAttribute("maxlon"));
    }
    
    // * When u initialize this class, use this function to get the list of Roads
    // to build a tree and do whatever eelse u need. The whole point
    // of this entire class is to give u this ArrayList<Road> roads
    public ArrayList<Road> getRoads(){
        return roads;
    }
    
    
    //roadCounter for debug purposes
    int roadCounter = 0;
    
    //this needs to be revisited
    // this does everything, it needs improvement and to be tidyed up.
    // can modularize this code
    private void getListOfNd(NodeList wayList){
  //      try{
            
            //This is the start og going through the unfiltered <ways> to
            // build up our Roads
            for(int i = 0; i< wayList.getLength();i++){
                
                // the following pattern repeats as we go through the nodes
                // of the XML file to get all the data we need to
                // build up the list of roads with all their info
                
                // make grab an individual Node from i to wayList.getLength()
                Node way = wayList.item(i);
                
                // turn that Node into an Element because
                // elements have lots of methods that are useful
                Element showElement = (Element)way;
                
                //This is making a node list of all the nodes (nd) that
                // make up the <way> 
                NodeList ndList = showElement.getElementsByTagName("nd");
//                System.out.println("way" + way.toString());

                ////////
                ///////// you need a node list of nds from ways
                ////////  that include a tag with attribute of 
                ////////  highway or road 
                /////////

                ///// This checks if the way is a Road. go visit
                // its function to see implementation details. 
                if(!isRoad(showElement)){
                    continue; // if the <way> is not a road, start loop over
                }
                

                ++roadCounter;
                if(i%100==0){
                    System.out.println("...building Road # "+ roadCounter);
                }


                // grabbing id of the Road
                int roadID = Integer.parseInt(showElement.getAttribute("id"));
//                System.out.println("Road ID: " + roadID);

//////////////// tempRoad is made to build a road that holds all its nodes 
//////////////// with all their data. After it is built, it is added to
//////////////// an ArrayList and then reinitialized here
                Road tempRoad = new Road();
                tempRoad.setID(roadID); 
                
                NodeList waynessList = showElement.getElementsByTagName("tag");
                
                for(int j = 0;j<waynessList.getLength();j++){
                    Node wayNd = waynessList.item(j);
                    Element waynessShowElement = (Element)wayNd;
                    String wayness = waynessShowElement.getAttribute("k");
  //                  System.out.println("ONEWAY: "+wayness);
                    if(wayness.equals("oneway")){
//                        System.out.println("ONEWAY");
                        String isOneway = (waynessShowElement.getAttribute("v"));
//                        System.out.println(isOneway);
                        if(isOneway.equals("yes")){
//                            System.out.println(isOneway);
                            tempRoad.oneWay=true;
                        }
                      
                    }  ////////////// WORKING HERE*********************
                    else if(wayness.equals("highway")){
//                        System.out.println("ONEWAY");
                        String roadType = (waynessShowElement.getAttribute("v"));
//                        System.out.println(isOneway);
                        if(roadType.equals("primary")||roadType.equals("primary_link")
                                ||roadType.equals("trunk_link")||roadType.equals("trunk")
                                ||roadType.equals("motorway")||roadType.equals("motorway_link")){
//                            System.out.println(isOneway);
                            tempRoad.speed=.0004;
                            tempRoad.numLanes=3;
                        }else if(roadType.equals("secondary")||roadType.equals("secondary_link")
                        ){
                            tempRoad.speed=.00027;
                            tempRoad.numLanes=2;
                        }else if(roadType.equals("service")||roadType.equals("service_link")){
                            tempRoad.speed=.0001;
                            tempRoad.numLanes=1;
                        }else if(roadType.equals("tertiary")||roadType.equals("tertiary_link")){
                            tempRoad.speed=.00022;
                            tempRoad.numLanes=2;
                        }else if(roadType.equals("residential")||roadType.equals("residential_link")){
                            tempRoad.speed=.00016;
                            tempRoad.numLanes=1;
                        }
                    }
                }
                
                
                
                // This cycles through all the nodes in a Road
                for(int j=0;j<ndList.getLength();j++){
                    Nd tempNd = new Nd();// familiar pattern
                    
                    
                    Node nd = ndList.item(j);
                    Element ndShowElement =(Element)nd;
                    NodeList refList = ndShowElement.getElementsByTagName("ref");
                    
                    
                    
                    
                    
                    
                    //System.out.println(nd.toString());
                    
                    // Ref is the ID# of a Nd (a node that makes up a road)
                    long ref = Long.parseLong(ndShowElement.getAttribute("ref"));
                    /////////////////////////////////////////////////////////////                           /WORKING HERE


//////////////////// This grabs the coordinates. This makeNd() function is so
//////////////////// inefficient. it needs to be reimplemented in binary search
//////////////////// before we can process the entire map of killeen. 
//////////////////// it should be lightening fast after its turned to Binary Search. 
                    tempNd=makeNd(ref);
                    tempNd.numLanes=tempRoad.numLanes;
                    tempNd.parentRoad=tempRoad;
                    tempRoad.addNode(tempNd);
                    
                    
                    //System.out.println(ndShowElement.getAttribute("ref"));
//                    for(int k = 0; k<refList.getLength();k++){
//                        Node ref = refList.item(k);
//                        Element refShowElement = (Element)ref;
//                        System.out.println("ref: "+ ref.toString());
//                    }
//                    
                    
                    
                
                }
                
                //
                // add the road we built to the array list, entire point of the class
                //
                roads.add(tempRoad);
                //
                
            }
        }
 //       catch(Exception ex){
 //           System.out.println("error; private void getListOfNd");
 //           System.out.println(ex.getMessage());
 //       }
 //   }
    public void makeTrafficLightList(Element elem){
        NodeList list = elem.getElementsByTagName("tag");
        
    }

    // this function is for testing if a <way> is a road vs something else
    private boolean isRoad(Element showElement){
        boolean isRoad = false;
        NodeList list = showElement.getElementsByTagName("tag");
 //       System.out.println("isRoad?" + showElement.toString());

        
        for(int i = 0; i<list.getLength();i++){
            Node tag = list.item(i);
 //           System.out.println(tag.toString());
            
            Element tagShowElement =(Element)tag;
            
            NamedNodeMap test = tag.getAttributes();
            
                    
//            System.out.println(tagShowElement.getAttribute("k"));
            String tagString = tagShowElement.getAttribute("k");
            if(tagString.equalsIgnoreCase("highway")){
                
                String wayType = tagShowElement.getAttribute("v");
                if(wayType.equals("footway")||wayType.equals("path")
                        ||wayType.equals("track")){
//                    System.out.println("footway");
                    return false;
                }
                return true;
            }
            //////////// 
            //////// THIS IS BROKEN
        }
        
        return isRoad;
    }
    
    ///

    //private void getListOfWays(){
    //    
    //}

    
    
    // get the XML file so we can work with it
    private Document getDocument(String docString){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
//            factory.setValidating(true);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            return builder.parse(new InputSource(docString));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
 
    //
    

    ArrayList<Nd> arrayAllNodes = new ArrayList<Nd>();
    
    private void makeNodeList(){
        for(int i = 0; i<allNodes.getLength();i++){
            
            Node tempNode = allNodes.item(i);
            Element showElement = (Element)tempNode;
            
            
            Nd nd = new Nd();
            nd.setRef(Long.parseLong(showElement.getAttribute("id")));
            nd.setLat(Double.parseDouble(showElement.getAttribute("lat")));
            nd.setLong(Double.parseDouble(showElement.getAttribute("lon")));
            System.out.println("................................................." +i);
            arrayAllNodes.add(nd);
        }
    }
    
    
    //SORT ALLNODES
    private void sortAllNodes(){
        
        for(int i = 0;i<arrayAllNodes.size()-1;i++){
            
            for(int j = i+1;j<arrayAllNodes.size();j++){
                
                Nd tempNd = arrayAllNodes.get(i);
                
                if(arrayAllNodes.get(i).getRef()<arrayAllNodes.get(j).getRef()){
                    arrayAllNodes.get(i).setRef(arrayAllNodes.get(j).getRef());
                    arrayAllNodes.get(i).setLong(arrayAllNodes.get(j).getLong());
                    arrayAllNodes.get(i).setLat(arrayAllNodes.get(j).getLat());
                    
                    arrayAllNodes.get(j).setRef(tempNd.getRef());
                    arrayAllNodes.get(j).setLong(tempNd.getLong());
                    arrayAllNodes.get(j).setLat(tempNd.getLat());
                    
                }
            }
        }
    }
    
//BINARY IMPLEMENTATION    
    private Nd makeNd(long ref){
//        makeNodeList();
//        sortAllNodes();
        Nd nd = new Nd();   // the Nd is one of our data types that holds the
                            // node data: ref, longitude, latitude
        
        int left=0;
        int right = allNodes.getLength();
        while(left<=right){
            int index = (left+right)/2;
            Node temp = allNodes.item(index);   //pulls an individual node from 
                                                //list of all nodes
            Element showElement = (Element)temp;// this convets it to Element
                                                    // to let us get the attributes
//            System.out.println("index "+index);
//            System.out.println("***********************"+Double.parseDouble(showElement.getAttribute("id")));
            if(ref==(Double.parseDouble(showElement.getAttribute("id")))){
                nd.setRef(ref); // Set reference ID number of the node
                nd.setLat(Double.parseDouble(showElement.getAttribute("lat")));  // seting latitude
                nd.setLong(Double.parseDouble(showElement.getAttribute("lon"))); // setting longitude
                
                    
                
                return nd;
            }else if(ref>(Double.parseDouble(showElement.getAttribute("id")))){
                left=index+1;
            }else if(ref<Double.parseDouble(showElement.getAttribute("id"))){
                right = index-1;
            }
                
        }
        
        return nd;
    }
    

    
        void setIntersections(){
        for(int i = 0;i<roads.size();i++){
            for(int j =0;j<roads.get(i).nodeList.size();j++){
                for(int k = 0; k<roads.size();k++){
                    if(k==i){
                        continue;
                    }
                    
                    for(int l=0;l<roads.get(k).nodeList.size();l++){
                        if(roads.get(i).nodeList.get(j).getRef()==roads.get(k).nodeList.get(l).getRef()){
                            IntersectNd tempNode = new IntersectNd();
///////////////////////////////WORK ON THIS
                            tempNode.connections.add(roads.get(i).nodeList.get(j));
                            tempNode.setLat(roads.get(i).nodeList.get(j).getLat());
                            tempNode.setLong(roads.get(i).nodeList.get(j).getLong());
                            tempNode.setRef(roads.get(i).nodeList.get(j).getRef());
                            roads.get(i).addIntersection(tempNode);
//                            System.out.println("Found Intercept");
                        }
                    }
                    
                    
                }
            }
        }
    }

    
}
