package ca.utoronto.utm.paint;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseButton;

/**
 * This is class is aim to provide a polyline strategy for user in paintmodel
 * in order to draw polyline
 * @author Alex
 *
 */
class PolylineManipulatorStrategy extends ShapeManipulatorStrategy{

	PolylineManipulatorStrategy(PaintModel paintModel) {
		super(paintModel);
	}
	
	private PolylineCommand polylineCommand;
	private boolean leftClick = false;
	
	/**
	 * mouseMoved method used for execute specific command
	 * when user action mouse-moving was captured
	 */
	public void mouseMoved(MouseEvent e){
		if (leftClick == true) {
			this.polylineCommand.setPotential(new Point((int)e.getX(),(int)e.getY()));
		}
	}
	
	/**
	 * mouseClicked method used for execute specific command
	 * when user action mouse-clicking was captured
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseButton.PRIMARY) {
			if (leftClick == false) {
				this.polylineCommand = new PolylineCommand(new Point((int)e.getX(), (int)e.getY()));
				this.addCommand(polylineCommand);
			}else {
				polylineCommand.getPoints().add(new Point((int)e.getX(),(int)e.getY()));
			}
			leftClick = true;
		}else if (e.getButton()==MouseButton.SECONDARY) {
			leftClick = false;
			polylineCommand.getPoints().add(polylineCommand.getPotential());
		}
	}
}
