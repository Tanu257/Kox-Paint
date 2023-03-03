import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JFrame;


public class Main {

	
	public static void main(String[] args) {
		
		Pane pane = new Pane();
		
		JFrame frame = new JFrame();

		Panel topPanel = new TopPanel(frame,pane);
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Kox Paint");
		
		frame.add(topPanel,BorderLayout.NORTH);
		frame.add(pane,BorderLayout.CENTER);
	
		frame.setVisible(true);
	}

}
