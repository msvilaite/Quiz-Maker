import javax.swing.*;
import java.awt.*;

public class HomeWindow extends JFrame{
	private Container contentPane;
	private JButton fileChooser = new JButton("Choose a file");
	private JTextArea messageToUser = new JTextArea();
	private FileChooserButtonListener listener = new FileChooserButtonListener(messageToUser);
	
	public HomeWindow() {
		super();
		setSize(800, 500);
		setTitle("Quiz Maker");
		contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(fileChooser);
		contentPane.add(messageToUser);
		fileChooser.addActionListener(listener);
		
		setDefaultCloseOperation(HomeWindow.EXIT_ON_CLOSE);	
	}

}
