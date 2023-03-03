import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ColorPIcker extends JFrame{
	private JFrame frame = new JFrame();
	public ColorPIcker() {
		frame.setTitle("Custom Color Picker");
		
		JButton btnApply = new JButton("Apply");
		
		JColorChooser colorChooser1 = new JColorChooser(Color.black);
		frame.add(colorChooser1,BorderLayout.CENTER);
		frame.add(btnApply,BorderLayout.SOUTH);

		frame.setSize(new Dimension(600,300));
		btnApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TopPanel.customColors.add(colorChooser1.getColor());
				TopPanel.customColorButton.setBackground(colorChooser1.getColor());
				frame.dispose();
			}
		});
		frame.setVisible(true);
	}
}
