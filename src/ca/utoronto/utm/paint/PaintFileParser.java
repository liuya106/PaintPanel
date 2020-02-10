package ca.utoronto.utm.paint;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.paint.Color;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author Alex
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel; 
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");
	
	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");
	
	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");
	
	private Pattern pPolylineStart=Pattern.compile("^Polyline$");
	private Pattern pPolylineEnd=Pattern.compile("^EndPolyline$");
	
	private Pattern pPointsStart=Pattern.compile("^points$");
	private Pattern pPointsEnd=Pattern.compile("^endpoints$");
	
	private Pattern pColor=Pattern.compile("^color:((1?\\d{1,2}|2[0-4]\\d|25[0-5]),){2}(1?\\d{1,2}|2[0-4]\\d|25[0-5])$");
	private Pattern pFilled=Pattern.compile("^filled:(false|true)$");
	private Pattern pCenter=Pattern.compile("^center:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pRadius=Pattern.compile("^radius:\\d+$");
	
	private Pattern pPoint=Pattern.compile("^point:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pp1=Pattern.compile("^p1:\\(-?\\d+,-?\\d+\\)$");
	private Pattern pp2=Pattern.compile("^p2:\\(-?\\d+,-?\\d+\\)$");
	
	private Pattern pblank=Pattern.compile("^\\n*$");
	// ADD MORE!!
	
	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}
	
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following commands. As we parse the file, we modify 
		// the appropriate command.
		
		CircleCommand circleCommand = null; 
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
		PolylineCommand polylineCommand = null;
		ArrayList<PaintCommand> newCommands = new ArrayList();
	
		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				this.lineNumber++;
				if (pblank.matcher(l).matches()){
					continue;
				}
				l=l.replaceAll("\\s","");
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
					case 0:
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Expected Start of Paint Save File");
						return false;
					case 1: // Looking for the start of a new object or end of the save file
						if(pCircleStart.matcher(l).matches()){
							// ADD CODE!!!
							state=3;
							break;
						}
						if(pRectangleStart.matcher(l).matches()){
							state=4; 
							break;
						}
						if(pSquiggleStart.matcher(l).matches()){
							state=5;
							break;
						}
						if(pPolylineStart.matcher(l).matches()){
							state=6;
							break;
						}
						if(pFileEnd.matcher(l).matches()) {
							state='e';
							break;
						}
						error("Expected start of objects or end of save file");
						return false;
//					case 2: (abandoned case)
//						if(pCircleEnd.matcher(l).matches()){
//							state=1; 
//							break;
//						}
//						if (pRectangleEnd.matcher(l).matches()) {
//							state=1;
//							break;
//						}
//						if (pSquiggleEnd.matcher(l).matches()) {
//							state=1;
//							break;
//						}
//						if (pPolylineEnd.matcher(l).matches()) {
//							state=1;
//							break;
//						}
//						error("Expected end of object");
//						return false;
					case 3:
						if(pColor.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Color color = Color.rgb(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),Integer.parseInt(parameter[1]),Integer.parseInt(parameter[2]));
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if(pFilled.matcher(l).matches()) {
								parameter = l.split(":");
								Boolean filled = Boolean.parseBoolean(parameter[1]);
								lineNumber++;
								l = inputStream.readLine();
								l=l.replaceAll("\\s","");
								System.out.println(lineNumber+" "+l+" "+state);
								if(pCenter.matcher(l).matches()) {
									parameter = l.split(",");
									Point point = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
									lineNumber++;
									l = inputStream.readLine();
									l=l.replaceAll("\\s","");
									System.out.println(lineNumber+" "+l+" "+state);
									if(pRadius.matcher(l).matches()){
										parameter = l.split(":");
										Integer radius = Integer.parseInt(parameter[1]);
										lineNumber++;
										l = inputStream.readLine();
										l=l.replaceAll("\\s","");
										System.out.println(lineNumber+" "+l+" "+state);
										if(pCircleEnd.matcher(l).matches()){
											state=1; 
											circleCommand = new CircleCommand(point, radius);
											circleCommand.setColor(color);
											circleCommand.setFill(filled);
											newCommands.add(circleCommand);
											break;
										}
									}
								}
							}
						}
						error("Expected valid circle parameters");
						return false;
					case 4:
						if(pColor.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Color color = Color.rgb(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),Integer.parseInt(parameter[1]),Integer.parseInt(parameter[2]));
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if(pFilled.matcher(l).matches()) {
								parameter = l.split(":");
								Boolean filled = Boolean.parseBoolean(parameter[1]);
								lineNumber++;
								l = inputStream.readLine();
								l=l.replaceAll("\\s","");
								System.out.println(lineNumber+" "+l+" "+state);
								if(pp1.matcher(l).matches()) {
									parameter = l.split(",");
									Point p1 = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","").replaceFirst("1","")),
											Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
									lineNumber++;
									l = inputStream.readLine();
									l=l.replaceAll("\\s","");
									System.out.println(lineNumber+" "+l+" "+state);
									if(pp2.matcher(l).matches()){
										parameter = l.split(",");
										Point p2 = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","").replaceFirst("2","")),
												Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
										lineNumber++;
										l = inputStream.readLine();
										l=l.replaceAll("\\s","");
										System.out.println(lineNumber+" "+l+" "+state);
										if (pRectangleEnd.matcher(l).matches()) {
											state=1;
											rectangleCommand = new RectangleCommand(p1, p2);
											rectangleCommand.setColor(color);
											rectangleCommand.setFill(filled);
											newCommands.add(rectangleCommand);
											break;
										}
									}
								}
							}
						}
						error("Expected valid rectangle parameters");
						return false;
					case 5:
						if(pColor.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Color color = Color.rgb(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
									Integer.parseInt(parameter[1]),Integer.parseInt(parameter[2]));
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if(pFilled.matcher(l).matches()) {
								parameter = l.split(":");
								Boolean filled = Boolean.parseBoolean(parameter[1]);
								lineNumber++;
								l = inputStream.readLine();
								l=l.replaceAll("\\s","");
								System.out.println(lineNumber+" "+l+" "+state);
								if(pPointsStart.matcher(l).matches()) {
									squiggleCommand = new SquiggleCommand();
									lineNumber++;
									l = inputStream.readLine();
									l=l.replaceAll("\\s","");
									System.out.println(lineNumber+" "+l+" "+state);
									if(pPointsEnd.matcher(l).matches()){
										lineNumber++;
										l = inputStream.readLine();
										l=l.replaceAll("\\s","");
										System.out.println(lineNumber+" "+l+" "+state);
										if (pSquiggleEnd.matcher(l).matches()) {
											state=1;
											break;
										}
									}
									if(pPoint.matcher(l).matches()) {
										parameter = l.split(",");
										Point point = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
												Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
										squiggleCommand.add(point);
										squiggleCommand.setColor(color);
										squiggleCommand.setFill(filled);
										state=7;
										break;
									}
								}
							}
						}
						error("Expected valid Squiggle parameters");
						return false;
					case 6:
						if(pColor.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Color color = Color.rgb(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
									Integer.parseInt(parameter[1]),Integer.parseInt(parameter[2]));
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if(pFilled.matcher(l).matches()) {
								parameter = l.split(":");
								Boolean filled = Boolean.parseBoolean(parameter[1]);
								lineNumber++;
								l = inputStream.readLine();
								l=l.replaceAll("\\s","");
								System.out.println(lineNumber+" "+l+" "+state);
								if(pPointsStart.matcher(l).matches()) {
									lineNumber++;
									l = inputStream.readLine();
									l=l.replaceAll("\\s","");
									System.out.println(lineNumber+" "+l+" "+state);
									if(pPointsEnd.matcher(l).matches()){
										error("Expected at least 2 points for polyline");
										return false;
									}
									if(pPoint.matcher(l).matches()) {
										parameter = l.split(",");
										Point point = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
												Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
										polylineCommand = new PolylineCommand(point);
										polylineCommand.setColor(color);
										polylineCommand.setFill(filled);
										state=8;
										break;
									}
								}
							}
						}
						error("Expected valid polyline parameters");
						return false;
					case 7:
						if(pPoint.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Point point = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
									Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
							squiggleCommand.add(point);
							state=7;
							break;
						}
						if(pPointsEnd.matcher(l).matches()) {
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if (pSquiggleEnd.matcher(l).matches()) {
								newCommands.add(squiggleCommand);
								state=1;
								break;
							}
						}
						error("Expected squiggle point section");
						return false;
					case 8:
						if(pPoint.matcher(l).matches()) {
							String[] parameter = l.split(",");
							Point point = new Point(Integer.parseInt(parameter[0].replaceAll("[\\D]","")),
									Integer.parseInt(parameter[1].replaceAll("[\\D]","")));
							polylineCommand.getPoints().add(point);
							polylineCommand.setPotential(point);
							state=8;
							break;
						}
						if(pPointsEnd.matcher(l).matches()) {
							lineNumber++;
							l = inputStream.readLine();
							l=l.replaceAll("\\s","");
							System.out.println(lineNumber+" "+l+" "+state);
							if (pPolylineEnd.matcher(l).matches()) {
								newCommands.add(polylineCommand);
								state=1;
								break;
							}
						}
						error("Expected polyline point section");
						return false;
					case 'e':
						if (l!=null) {
							error("Expected no more information");
							return false;
						}
				}
			}	
			if (state!='e') {	
				return false;
			}			
		}  catch (Exception e){
			System.out.println(e);
		}
		paintModel.reset();
		for(int i=newCommands.size()-1;i>=0;i--) {
			paintModel.addCommand(newCommands.get(i));
		}
		return true;
	}
}				
						
					
			
		
		
