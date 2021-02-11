COSC Lab 10

For some reason this program makes my computer really hot sometimes. It might be really inefficient but I didn't really focus on fixing that because I had a lot of ideas to implement. 
Ellipses and Splines are probably the culprit because they're defined as huge arrays of (x,y) coordinates

Hover over the toolbar to see tooltips
Click on a tool to use it
Click inside the drawing pad to begin drawing
Drawing tools will automatically snap to a nearby object or its start/stop nodes. Hold Ctrl to suppress snap. For some reason this doesn't always work. Try restarting the program if the first object drawn does not exhibit correct snap behavior

 - When marquee is selected:
The nearest object to the mouse will be highlighted. 
Click to select highlighted object.
Shift + Click for additive selection
Click on a selected object to unselect it
Ctrl+A to select all
All relevant nodes for selected objects will be shown in teal. Click and drag nodes to edit objects
Click and drag a selected object to move all selected objects
	Warnings: Only select and move one node at a time (i.e. do not shift+click while editing 		
	objects), don't move foci more than 2*a (semi major axis) away from each other, and don't 				
	superimpose endpoints.
Click and drag an unselected object to clear selection, select it, and move it.
Press delete to delete selected objects
Click while no objects are highlighted to deselect everything
 - One click for a node
 - Two clicks for a line
 - Five clicks for an ellipse (See tooltip)
Usage hints: 
For a circle with diametrically opposite points 1 and 2, do: click@1, click@2, enter, enter
For a circular arc, define the circle with two clicks, press enter, and then define the arc
When defining an arc, do not make the end coincide with the beginning.
 - Any number of clicks for a spline. Press enter instead of clicking for the last point that you want to set. Hold ctrl to set points in reverse.
Example: say you wanted to make a spline from the points 1 2 3 and 4 (in that order). You could do 
click@1, click@2, click@3, enter@4
click@1, ctrl + click@4, click@2, enter@3 or
click@1, ctrl+click@4, ctrl+click@3, enter@2.
	What the program will display for the last option: 
	after click@1: 		spline from 1-mouse 
	after ctrl+click@4: 	spline from 1-mouse-4 
	after ctrl+click@3:	spline from 1-mouse-3-4, 
	after ctrl+click@2: 	spline from 1-2-3-4
If you click to set the beginning and then ctrl+click to set the end, the spline shown on screen throughout subsequent steps will more closely match your final intended spline.
 - Follow the prompt for save and open. Do not include the full path; just the name.
	Format for save files:
        	The first line is one integer - the number of objects. Data for objects follows.
        	For each object, the first line gives the type of object and number of nodes
        	Each node is a separate line, with x and y coordinates.
        	Ellipses have a third line giving semi major axis and endpoints
 - Print will print the Asymptote code for the drawing to standard output
	Go to https://artofproblemsolving.com/texer/ copy + paste the code (needs the [asy]...[/asy] brackets to render) into the left, and click "render as pdf" to see the drawing. NB: may have to click "open PDF in new tab" to see image
 - Exit exits 

Ways to abort drawing:
Clicking on a tool will abort and select that tool
Clicking (or pressing enter) outside of the pad
Hotkeys

Hotkeys: 
Space = marquee
1/2/3/4 = node/line/ellipse/spline
Ctrl + P/S/O/A = print/save/open/select all. NB: Ctrl, NOT Cmd
Esc = exit
	

