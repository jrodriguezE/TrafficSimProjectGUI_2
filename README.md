# TrafficSimProject

TAMUCT COSC-3320-110 Programmers Group 1

TODO:

-Create simulation method to start the program paused without loading cars.
Give options to load map, pick number of cars and any other start options.
10/8/2018 *set to do 10/15/2018.hour. 

Heatmap by two node segments. Do by moving average. Lines? Filled translucent rectangles (use calculate offset
coordinate function on two nodes in line)? multiple circles? 
10/14/2018

-Average trip length display
10/8/2018

-Get a frame for general car stats&options. Display a live graph of something. 
10/14/2018*do 10/15/2018

-Implement "major disaster" scenario. Click a spot on the map and every car
will run away from it and attempt to path OFF of the map (evacuation simulation). 
10/8/2018

-Clock needs a "how many hours since (time)" function.  
10/8/2018



-make cars rectangle shaped and align their axis with their velocity vector. 
10/12/2018-

-Editing connections between nodes. Make connection display in NodeInfoFrame
a selectable list to jump between nodes.
10/1/2018


-Build Stop lights. manual placement with node info tool
9/28/2018

-Ability to add new nodes
9/28/2018

-Merge current tool windows into a menu that docks on the side of the window.
(like photoshop).  
10/8/2018

-Set Git up to keep track of revisions.
9/21/2018

ANNOUNCEMENTS:

10/15/2018 added some tasks (stat w/ graphs, load/save/start options, heatmap)
Maybe track stats... heave the heat map work on a rolling average. This plus
scenarios will be cool. Need to be able to adjust min route distance while 
running. Also change min distance to a range with weights for various distances.


10/14/2018 There are actual turn lanes in addition to all the other lanes now

10/12/2018 Multiple lanes enabled, cars will pass. . 
Use this to determine how cars should turn at an intersection:
https://math.stackexchange.com/questions/274712/calculate-on-which-side-of-a-straight-line-is-a-given-point-located
If waypoint is a stop... check if there is another Nd in route (Directions.stepIndx<Directions.directions.size())
Calculate distance from node to stop.... this will work for getting the
cars turning right to position themselves correctly and can get the ones turning
left to at least start from middle road position... could handle them
turning PAST the waypoint if using boolean to flag that it has passed 0 distance
and then when it gets a positive distance away from the node, go to next waypoint...
This should get them to maintain their lanes throughout the turns!

Also use this to determine going into a turn lane!!! yay!!! 

The above formula will also be used to set up realistic stoplights! 


10/11/2018 Disabled continuous offset system and changed car behavior to 
physically move to their correct lane. They get a little tight on turns that
have a distant connection Nd. 
Need to set up offset on waypoint to be dependent on what lane a car is in.
If a car gets too close in front of a car, it will change lanes if one is
available to pass. Eventually we can add randomized preference to speed or
drive slow (adjust their perception of the speed limits by .9 or 1.1 or 
something like that)

Figured a much less computationally intense method of maintaining
correct lanes at turns. Just gotta add an extra "stop sign" node between
intersections and all their connections. 

10/10/2018 Turned car spacing below distance to stop before stop sign.

10/10/2018 IDEA: For a traffic scenario, schools can be used as destinations that
will be weighted in a pool with the regular random destinations to simulate
traffic when parents are picking up or dropping off their kids. Schools are
a good choice because there are a lot of them in open street map. This system
can later be used to direct traffic to stores, hospitals and other buildings as part
of the regular traffic routine with the school event happening depending on the
clock timer. 


10/10/2018 Cars were not slowing down upon entering lower speed limit zone... 
now they do.
Road width, currently we are at 12 feet lanes. 
https://www.quora.com/How-wide-is-a-typical-town-road-in-the-US
Eliminated more incorrect "highway" types from the OSM file, 
"track","footway","path"

10/9/2018 Cars are now offset to the right of the road to represent separate 
lanes. Offset is in renderer... maybe do it in actual physical sim of world
to more easily handle turns (they "jump" at the turns now). 
(consider this an EXPERIMENT)... I think instead could do this in the physical 
model... insteadof calculating offset for the cars position every step... set 
offset on the waypoint at begining of route leg and let car path to appropriate 
location on its own.

10/9/2018 Can save processed map files as well as batches of direction files. 
Loading time is a few orders of magnitude better. 

Changed sort to find min in A*. Fixed node intersections: they are using highways
now. Bunch of other little bug fixes. 

10/8/2018 https://www.kcentv.com/article/news/local/killeen-neighborhood-it-takes-20-minutes-to-get-out-of-our-subdivision/600300276
The sim predicts this. The area in the link  is consistently the most congested area 
in MediumMap.osm.

10/7/2018 concurrency enabled: multiple route generators, running on seperate
threads, keep a buffer full of generated routes.

10/7/2018 A* mode that takes speed limits into account... uses time instead of 
distance as its metric. 
*Adjusted the traffic congestion heuristic on the A*. make slider to adjust this
from a menu.

10/6/2018 Set time frame with a hour select slider introduced. Fast forwards to
whatever time you select. A car selector that draws a rectangle on a clicked car
if you have the car frame open. You can now set speed limits of individual nodes
from the node info screen...I'll make a tool to select groups of nodes by mouse
and by entire roads. 

You can toggle accidents on a car to cause it to stop and block traffic. 
Once we finish the current TODO list, we will have fulfilled all project 
requirements.

Traffic Flow Rate is displayed in main window toolbar.
Roads now set speed from OSM file depending on whether they are a trunk,
primary, secondary or residential.

10/3/2018 Mouse wheel now zooms map while keeping focus of object under the
mouse cursor (just like Google maps). Pretty sweet.  

10/2/2018 https://www.openstreetmap.org/edit?editor=id#map=19/31.08832/-97.71982
Register an account and start updating the map with traffic lights. 

Mouse wheel zoom and map movement implemented. Zoom needs work.  

10/1/2018 There are a few buttons, pause, play, fast forward. 
Fast forward is button clicks^2. 
 
Mouse position is now converted to correct long and lat. Red square dropped where
the engine thinks you are in lon&lat. Zoom has been fixed. 

9/30/2018 Stop signs are working. Time for stop lights. 

Cars now keep distance between each other. 

A* now takes into account traffic levels at time of route generation!

Pause and play functionality.
Placeholder Frames to hold data and select options-still empty.

9/29/2019 Consolidated intersections into one node. Next task is making stoplights
*Nope... that broke a lot of intersections. set it back to old technique. 

One way roads are now implemented. This causes an issue if u set the cars
to generate new routes from their destination waypoint if they just got to the
end of a oneway road that dead ends at the end of the map and a few other
circumstances. To deal with this i set up cars to spawn at a random new starting
location when they finish their route. 

Pathfinding now takes distance traveled into account (A*). Rebuilt GraphBuilder
to consolidate intersections into single nodes. Next step is stops. 

9/28/2019 Figured out the methods of JComponent a little better and was able to 
fix the frame rate issue in the Renderer. Works a lot better now! 

List of nodes added to Nd that link to all cars who hold the node as a waypoint.
Might carry previous node to help figure orientation for handling stop signs.
Stop lights should be much simpler. Idea: build stop lights or stop signs
with mouse... map building tools.

New bug, sometimes the cars will cycle through more than one node when
updating waypoint. 
-Check on the... d/D, im thinking its when D gets near 0 or something. 

Pathfinding causes hiccups in frame rate on the full size Killeen map when
Random() picks two points that do not connect, leaving the algorithm to 
(sometimes) search the _entire_ map. 

I was thinking about signal time length for stop lights and started to
wonder, "should we add pedestrians?". 
https://nacto.org/publication/urban-street-design-guide/intersection-design-elements/traffic-signals/signal-cycle-lengths/
A lot of the decisions on timing schemes depends on vehicle traffic
AND pedestrian traffic. 


9/27/2018 The automobile can now update its position based on time and
velocity, this means it now moves smoothly between nodes.   

You can now spawn variable amount of cars at once, setCars(int numOfCars). 
Look at main() in TrafficSimProject for more details. 

9/26/2018 It looks like routeFind() is fixed. You can enter a start node and
end node and it will automatically generate a path and animate the car taking
the nodes between both points. A new bug has come up, when you first run the
program, if you use the medium map or larger, the window will not update the
animation until after you MINIMIZE the window and then open it back up. 
The renderer needs to be rebuilt and a controller/handler for setting up cars 
needs to be written. The car controller needs to be able to generate a 
defined amount of cars and give all of them random start and end nodes. The
controller needs to hold the cars in a list and update them all iteratively, 
every "step". After they are all updated a list of all the cars positions needs
to be passed to the renderer to update the next frame. 

9/26/2018 The demo now animates in a loop. I added a debug() method to the
Auto class. call the debug method after your car already has a waypoint assigned 
to its waypointNode and you can manually traverse the nodes. waypointNodes 
represent the next node the car is supposed to take on a route. 
Debugging of the node system revealed that the map nodes are all correctly
linked. Since the node system is fine, the bug is most probably in 
Direction routeFind(). *see BUGS  

9/24/2018 DEMO:
Once the demo loads, you can press a key into the console and hit enter to cause
a car to take a "step". Each step will traverse one node towards the car's
destination. The code is in the Simulation class under demo().


9/24/2018 There is a new Directions class. It has some stuff to build lists of
directions for the car to follow. Pathfinding algorithm needs to generate a
list of choices that the car will make at each node. The automobile can take the
direction list and it will travel from a starting node through the route with every
call of the .step() function. The renderer must be called separately to display
the car on the map. When we generate many cars we will update the cars position 
in the geographic system and then form a list of coordinates (and possibly orientations) 
along with type to send to the renderer to display. 
There is a findRoute() in Directions but it is very buggy, see BUGS. 
-ww
Cleaned up main and made a Simulation class to make things more manageable. 
Need to fix protected vs private vs public on a lot of stuff. 

9/23/2018 There individual nodes can be built into a tree structure that gives
every node a pointer to its connecting nodes along with edge weight data. I'm
working on an "Auto" class that can traverse the nodes while keeping track
of its current position. It takes a set of "directions" that are in the
form of a list of integers. each integer represents the index of the appropriate
connecting node. 
-ww

9/22/2018 The map is displaying and is finding all intersections correctly. We've got
everything we need to set up a graph of the roads and then implement a 
graph traversal algorithm.
-ww



BUGS



COMPLETED TASKS

COMPLETED 10/14/2018 - added turn lanes

COMPLETED 10/14/2018 --improve how turns are handled with multiple lanes (adjust where car decides to
switch waypoints... check connection position after intersection for cars route
then see if next connection is on left, right or same line 
(<0,0,>0 check in range of 0 instead of at since double).
if turn right, change waypoint BEFORE targetNode, 
if  turning left, change waypoint AFTER targetNode
if going straight, AT targetNode 
Do this next. 
if multiple lanes, move car to appropriate lane to turn 
est 10/12/2018 - 10/13/2018)

COMPLETED 10/12/2018 -add radioboxes to RoadInfoFrame to set an entire road's number of lanes. (req 5b)

COMPLETED 10/12/2018-multi lane roads - 4 lane roads and visual road in correct lane offset. Lanes determined between
waypoint and lastwaypoint (between each two nodes). Calculate point a specific
distance perpendicular to a point on a line! 
https://stackoverflow.com/questions/17195055/calculate-a-perpendicular-offset-from-a-diagonal-line
started 10-11-2018 

COMPLETED 10/12/2018 -Car passing

COMPLETED 10/12/2018 -initialize numLanes in Nd from mapreader. Update NdBuilder and FileBuilder to reflect

COMPLETED 10/11/2018 -Toggle hiding a node from pathfinding and eliciting reroute from cars 
attempting to route through them.  
9/28/2018

COMPLETED 10/10/2018 -Display cars offset from center of road depending on their lane. 

COMPLETED 10/9/2018: -Create data structure to save processed and edited map data. Needs to be 
enough to let GraphBuilder do its job. 

COMPLETED 10/7/2018: -Create thread to handle path finding. 
Create an array to hold 10 directions, as new cars are generated, they will pull
from the array to get their directions(disposing of source direction) and the 
direction thread will then make a new direction, based on current traffic
conditions, to replace it... If there are no new directions, car can go inactive
until a new route is ready. 

COMPLETED 10/7/2018: -makeDirection() in Simulation needs to be able to set a minimum distance
between start and end point. 

COMPLETED 10/7/2018:-Adjust A* to take speed limit into account

COMPLETED 10/7/2018: Set speed limit from OSM file.

COMPLETED 10/7/2018: change speed limits by Road

COMPLETED 10/6/2018: Traffic traffic load and flow rate. Increment counter as cars reach destination
and respawn.

COMPLETED 10/6/2018: Clock & Set Time

COMPLETED 10/6/2018: Speed limits *by node

COMPLETED 10/3/2018: -Center camera to mouse on zoom

COMPLETED 10/3/2018: When hit detection is turned on, deadlock can occur at stops. Currently using
a bandaid to deal alleviate symptoms. Investigate and fix root cause!!! 

COMPLETED 10/2/2018: -Zoom and map scroll by mouse button hold. 

COMPLETED 10/2/2018 BUG: fix california stoppers 

COMPLETED 10/1/2018: -Test play and pause button functionality.

COMPLETED 10/1/2018:-Test mouse interaction.

COMPLETED 10/1/2018:-Function to find a node based on its longitude and latitude. 

COMPLETED 10/1/2018 BUG: -Fix the hit detection bug!!!!!!!!!!!!!!!

COMPLETED 10/1/2018 BUG: Map is displaying upside down because origin (0,0) is top left corner for the JFrame.
Need to flip vertically.

COMPLETED 10/1/2018: zoom broken since redesigning renderer

COMPLETED 9/30/2018: pause/play functionality

COMPLETED 9/30/2018: Stop signs.

COMPLETED 9/29/2018: Link to Road from nodes. 

COMPLETED 9/29/2018: Change intersection node set up.. currently every intersection is made up of
two nodes... need to change to ONE node at each intersection... this will greatly
simplify stop signs and stop lights. 

COMPLETED 9/29/2018: Alter localRoadBuilder to implement unidirectional node connections for
one-way roads. 

COMPLETED 9/28/2018: Sometimes the cars will cycle through more than one node when
updating their waypoint. Might be a problem with node connections. 
*problem was with updating the waypoint and posNode of auto. 

COMPLETED 9/28/2018: Fixed frame rate issue and the minimize-maximize bug
Renderer needs to rebuilt. Its doing a lot of unnecessary work.

COMPLETED  9/27/2019: Implement update of car position between nodes. Solve for x and y from
distance formula, Pythagorean theorem, vectors. 

COMPLETED 9/26/2018: Implement a graph search. 

COMPLETED 9/26/2018 BUG FIXED: findRoute() sort of works. Need to check if the problem is in the algorithm (likely)
or something with the linking of nodes crossing roads (in some test cases a path
will be generated that involves changing roads at intersections, but sometimes
the algorithm gets stuck in an infinite loop, need to review the code but im too
tired atm). 

COMPLETED 9/23/2018: Building a tree out of the road and intersection data. 

COMPLETED 9/22/2018: Renderer needs to be reworked. I just slapped it together to see if the program was getting
the coordinates of nodes correctly. This needs to be fixed so we can visually see what we are doing. 


COMPLETED 9/21/2018: Determining order of nodes in a road. This is important for drawing the roads correctly on the map.
I am thinking going through list of nodes and then doing distance formula distance=sqrt((x1-x2)^2+(y1-y2)^2)
to find the shortest distances to two nodes from and one node in a road will work to figure their correct order.
There are lots of nodes on curves so that shouldnt cause a problem but some of the roads will be "ends" and only have one node connected to them. 

COMPLETED 9/21/2018: Reimplement the method "private Nd makeND(long ref){ }" inside the "MapReader" as a binary search. 
*important to test the program with a map of the entire city of killeen to fullfil requirements. 
O(n) vs O(log(n)) on test map at 28,000 nodes, that is 28,000/14 = 1000 times faster.  