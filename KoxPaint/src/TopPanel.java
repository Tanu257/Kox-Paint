import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class TopPanel extends Panel implements ActionListener,ItemListener{

	public static Color[] mColorList = {Color.black,Color.WHITE,Color.red,Color.gray,Color.green,Color.cyan,Color.yellow};
	public static ArrayList<Color> customColors = new ArrayList<>();
	public static int selectedColorIndex = 0;
	public static JButton customColorButton;
	public static int strokeSize = 1;
	private Pane pane;
	private JFrame frame2;
	private JComboBox<String> shapeComboBox;
	public TopPanel() {	
		setupAllComponents();
		
	}
	public TopPanel(JFrame frame,Pane pane2) {
		
		frame2 = frame;
		pane = pane2;
		setupMenuBar();

		setupAllComponents();
	}
	private void setupShapeSelector() {
		String[] aStrings = Pane.getShapes();
		shapeComboBox = new JComboBox<String>(aStrings);
		shapeComboBox.addItemListener(this);
		this.add(shapeComboBox);
	}
	private void setupColors() {
		
		createColorButton(0);
		createColorButton(1);
		createColorButton(2);
		createColorButton(3);
		createColorButton(4);
		createColorButton(5);
		
		{
			customColorButton = new JButton();
			customColorButton.setBackground(mColorList[0]);
			customColorButton.setPreferredSize(new Dimension(25,25));
			
			customColorButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					selectedColorIndex = -(customColors.size());
				}
			});
			
			JButton colorPickerButton = new JButton();
			colorPickerButton.setText("Custom Color ->");
			colorPickerButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unused")
					ColorPIcker pIcker = new ColorPIcker();
					selectedColorIndex = -(customColors.size()+1);				}
			});
			this.add(colorPickerButton);	
			this.add(customColorButton);
		}
		
	}
	private void setupStroke(){
		
		JSlider strockSlider = new JSlider(1,50);
		strockSlider.setMinorTickSpacing(2);
		strockSlider.setMajorTickSpacing(10);
		strockSlider.setValue(1);
		strockSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				strokeSize = strockSlider.getValue();
				
			}
		});
		this.add(strockSlider);
	}
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.saveFile();
			}
		});
		JMenuItem clearMenuItem = new JMenuItem("Clear");
		clearMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.clearBoard();
				
			}
		});
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitApp();
				
			}
		});
		
		fileMenu.add(saveMenuItem);
		fileMenu.add(clearMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		frame2.setJMenuBar(menuBar);
	}
	private void createColorButton(int colorIndex) {
		JButton jButton0 = new JButton();
		jButton0.setBackground(mColorList[colorIndex]);
		jButton0.setPreferredSize(new Dimension(25,25));
		jButton0.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedColorIndex = colorIndex;
				
			}
		});
		this.add(jButton0);
	}
	private void setupAllComponents() {
		setupShapeSelector();
		setupColors();
		setupStroke();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void ExitApp() {
		frame2.dispose();
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == shapeComboBox) {
			pane.selectedShape = shapeComboBox.getSelectedIndex();
		}
		
	}
}
