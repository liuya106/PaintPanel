package ca.utoronto.utm.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * newly added polyline drawing function. Now we could 
 * make use of this class in PaintModel to draw some polyline.
 * 
 * @author Alex
 *
 */
class PolylineCommand extends PaintCommand{
	private Point potential;
	private ArrayList<Point> points;
	
	public PolylineCommand(Point start){
		potential = start;
		points = new ArrayList<Point>();
		points.add(start);
	}
	
	/**
	 * the method used to set the unsettled points as user mouse move.
	 * @param Point p the next potential poly-point
	 */
	public void setPotential(Point p){ 
		this.potential = p;
		this.setChanged();
		this.notifyObservers();
	}
	public Point getPotential(){ return this.potential; }
	
	public ArrayList<Point> getPoints(){ return points;}
	
	/**
	 * User uses execute() method to execute all commands in PaintModel.commands
	 */
	@Override
	public void execute(GraphicsContext g) {
		g.setStroke(this.getColor());
		for (int i=0;i<points.size()-1;i++) {
			g.strokeLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
		}
		g.strokeLine(points.get(points.size()-1).x, points.get(points.size()-1).y, potential.x, potential.y);
	}
	
	/**
	 * @return a well formatted command string used for file saving and parsing
	 */
	public String toString() {
		String point = "";
		point+="\tpoints\n";
		for (int i=0;i<points.size();i++) {
			point+= "\t\tpoint:"+points.get(i).toString()+"\n";
		}
		point+="\tend points\n";
		String s = "Polyline\n"+
				super.toString() + 
				point+
				"End Polyline\n";
		return s;
	}
}
