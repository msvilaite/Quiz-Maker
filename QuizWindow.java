import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Pattern;

public class QuizWindow extends JFrame {
	private Container contentPane;
	
	public QuizWindow(String text) {
		super();
		setSize(700, 750);
		setTitle("Quiz Maker");
		contentPane = getContentPane();
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		LinkedList<String>[] gatheredData = generateQuizData(text);
		
		JLabel[] labels = new JLabel[5];
		JRadioButton[] radioButtons = new JRadioButton[3];
	
		// populate the Quiz Window with 5 quiz questions
		for(int i=0; i<5; i++) {
			labels[i] = new JLabel(gatheredData[i].getFirst());
			panel.add(labels[i]);
			for (int j=0; j<3; j++) {
				radioButtons[j] = new JRadioButton(gatheredData[i].get((j+1)));
				panel.add(radioButtons[j]);
			}
		}
		
		JButton submitButton = new JButton("Submit");
		JLabel label = new JLabel();
		
		panel.add(submitButton);
		panel.add(label);

		/* For display purposes, a random score is presented to the user 
		 after submitting the answers to the quiz questions.
		 To calculate an actual score, a data structure can be implemented
		 to hold the data about which answers are correct to a particular question.*/
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random random = new Random();
				int percentage = random.nextInt(100) + 1;
				label.setText("Your score is " + percentage + "%");
			}
		});
	}
	
	public LinkedList<String>[] generateQuizData(String textFromFile) {
		LinkedList<String>[] arrayOfLists = new LinkedList[5];
		
		// cut up the text string into chunks that can be used for questions
		Pattern p = Pattern.compile("[1-9][.]|[1-9][0-9][.]");
		String[] array1 = p.split(textFromFile);
		
		// use these in case some questions do not have enough answers
		String[] extraAnswers = new String[] {"None of the above", "All of the above"};

		for(int i=0; i<5; i++) {
			arrayOfLists[i] = new LinkedList<String>();
			Random random = new Random();
			int questionIndex = random.nextInt(100) + 1;
			String question = array1[questionIndex];
			String[] array2 = question.split("▪");
			arrayOfLists[i].add(array2[0]);
			
			// to avoid Index Out Of Bounds exception, make sure that array2 has enough elements
			// if it does not, populate it with values from extraAnswers array
			if(array2.length==2) {
				String[] copy = array2;
				String[] newArr = new String[4];
				array2 = newArr;
				array2[0] = copy[0];
				array2[1] = copy[1];
				array2[2] = extraAnswers[0];
				array2[3] = extraAnswers[1];
			}
			if(array2.length==3) {
				String[] copy = array2;
				String[] newArr = new String[4];
				array2 = newArr;
				array2[0] = copy[0];
				array2[1] = copy[1];
				array2[2] = copy[1];
				array2[3] = extraAnswers[0];
			}
			for (int j=1; j<4; j++) {
				arrayOfLists[i].add(array2[j]);
			}
		}
		return arrayOfLists;
	}
}
