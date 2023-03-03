import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Pane extends JPanel implements MouseMotionListener,MouseListener{
	
	private static String[] shapes = {"rect","circle"};
	public int selectedShape = 0;
	
	public static ArrayList<ArrayList<Integer>> shape_Rectangles = new ArrayList<>();
	public static ArrayList<ArrayList<Integer>> shape_Circle = new ArrayList<>();
	
	private boolean isPending = false;
	private int pending_index_rect = 0;
	private int pending_index_circle = 0;
	
	TopPanel topPanel = new TopPanel();
	public static String[] getShapes() {
		return shapes;
	}
	public Pane() {
		this.setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		repaint();
	}
	public void paint(Graphics g2) {
		Graphics2D	g=(Graphics2D)g2;
		super.paint(g);
		g.setColor(Color.black);
		drawAllElements(g);
		
	
	}
	public void saveFile() {

		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D new_g = image.createGraphics();
		super.paint(new_g);
		drawAllElements(new_g);
		try {
			ImageIO.write(image, "png", new File(showDielog()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private String showDielog() {
		JFileChooser fileChooser = new JFileChooser("D:");
		int i =fileChooser.showSaveDialog(null);
		if (i== JFileChooser.APPROVE_OPTION) {
			File file= fileChooser.getSelectedFile();
			return file.getPath();
		}
		else {
			return null;
		}
		}
	public void clearBoard() {
		isPending = false;
		pending_index_rect = 0;
		shape_Rectangles.removeAll(shape_Rectangles);
		shape_Circle.removeAll(shape_Circle);
		pending_index_circle = 0;
		repaint();
	}
	private static void drawAllElements(Graphics2D g) {
		for (int i = 0; i < shape_Rectangles.size(); i++) {
			Color toDrawColor;
			if ( shape_Rectangles.get(i).get(6) <0) {
				g.setStroke(new BasicStroke(shape_Rectangles.get(i).get(7)));
				toDrawColor = TopPanel.customColors.get(-shape_Rectangles.get(i).get(6)-1);
				g.setColor(toDrawColor);
			}
			else {
				g.setStroke(new BasicStroke(shape_Rectangles.get(i).get(7)));
				g.setColor(TopPanel.mColorList[shape_Rectangles.get(i).get(6)]);
			}
			g.drawRect(shape_Rectangles.get(i).get(0),shape_Rectangles.get(i).get(1),shape_Rectangles.get(i).get(2),shape_Rectangles.get(i).get(3));
		}
		for (int i = 0; i < shape_Circle.size(); i++) {
			//g.setColor(TopPanel.mColorList[shape_Circle.get(i).get(4)]);
		//	g.setStroke(new BasicStroke(shape_Circle.get(i).get(5)));
			//g.drawArc(shape_Circle.get(i).get(0), shape_Circle.get(i).get(1),shape_Circle.get(i).get(2), shape_Circle.get(i).get(3), 0, 360);
			Color toDrawColor;

			g.setStroke(new BasicStroke(shape_Circle.get(i).get(5)));
			if ( shape_Circle.get(i).get(4) <0) {
				toDrawColor = TopPanel.customColors.get(-shape_Circle.get(i).get(4)-1);
				g.setColor(toDrawColor);
			}
			else {
				g.setColor(TopPanel.mColorList[shape_Circle.get(i).get(4)]);
			}
			g.drawArc(shape_Circle.get(i).get(0), shape_Circle.get(i).get(1),shape_Circle.get(i).get(2), shape_Circle.get(i).get(3), 0, 360);
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			
			if (selectedShape == 0) {
				ArrayList<Integer> rArrayList = new ArrayList<>();
				int x_ =e.getPoint().x;
				int y_=e.getPoint().y;
				rArrayList.add(x_);
				rArrayList.add(y_);
				rArrayList.add(1);
				rArrayList.add(1);
				rArrayList.add(x_);
				rArrayList.add(y_);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Rectangles.add(rArrayList);
				repaint();
				isPending = true;
			}
			if (selectedShape == 1) {
				ArrayList<Integer> rArrayList = new ArrayList<>();
				int x_ =e.getPoint().x;
				int y_ = e.getPoint().y;
				rArrayList.add(x_);
				rArrayList.add(y_);
				rArrayList.add(1);
				rArrayList.add(1);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Circle.add(rArrayList);
				repaint();
				isPending = true;
			}
			
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		isPending = false;
		if(selectedShape == 0) {

			pending_index_rect += 1;
		}
		if(selectedShape == 1) {

			pending_index_circle += 1;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if (isPending && selectedShape == 1) {
			double xDis = shape_Circle.get(pending_index_circle).get(0) - e.getPoint().x;
			double yDis = shape_Circle.get(pending_index_circle).get(1) - e.getPoint().y;
			shape_Circle.get(pending_index_circle).remove(2);
			shape_Circle.get(pending_index_circle).add(2, (int)Math.abs(xDis) );
			shape_Circle.get(pending_index_circle).remove(3);
			shape_Circle.get(pending_index_circle).add(3, (int) Math.abs(yDis));
			repaint();
		}
		if (isPending && selectedShape == 0) {
			ArrayList<Integer> rArrayList = new ArrayList<>();
			
			int origin_x = shape_Rectangles.get(pending_index_rect).get(4);
			int origin_y = shape_Rectangles.get(pending_index_rect).get(5);
			
			int got_x = e.getPoint().x;
			int got_y = e.getPoint().y;
			
			if(got_x - origin_x <0 && got_y-origin_y <0) {
				
				rArrayList.add(got_x);
				rArrayList.add(got_y);
				rArrayList.add(origin_x - got_x);
				rArrayList.add(origin_y - got_y);
				rArrayList.add(origin_x);
				rArrayList.add(origin_y);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Rectangles.remove(pending_index_rect);
				shape_Rectangles.add(rArrayList);
				repaint();
			}
			else if (got_x - origin_x <0) {
				
				rArrayList.add(got_x);
				rArrayList.add(origin_y);
				rArrayList.add(origin_x - got_x);
				rArrayList.add(got_y-origin_y);
				
				rArrayList.add(origin_x);
				rArrayList.add(origin_y);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Rectangles.remove(pending_index_rect);
				shape_Rectangles.add(rArrayList);
				repaint();
			}
			else if (got_y - origin_y <0) {
				rArrayList.add(origin_x);
				rArrayList.add(got_y);
				rArrayList.add(got_x-origin_x);
				rArrayList.add(origin_y - got_y);
				
				rArrayList.add(origin_x);
				rArrayList.add(origin_y);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Rectangles.remove(pending_index_rect);
				shape_Rectangles.add(rArrayList);
				repaint();
			}
			else {

				rArrayList.add(origin_x);
				rArrayList.add(origin_y);
				rArrayList.add(got_x - origin_x);
				rArrayList.add(got_y - origin_y);
				rArrayList.add(origin_x);
				rArrayList.add(origin_y);
				rArrayList.add(TopPanel.selectedColorIndex);
				rArrayList.add(TopPanel.strokeSize);
				shape_Rectangles.remove(pending_index_rect);
				shape_Rectangles.add(rArrayList);
				repaint();
			}
		}
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	

}
