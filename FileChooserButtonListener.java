import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FileChooserButtonListener implements ActionListener{
	private JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private File f;
	private String path;
	private JTextArea messageToUser;
	
	public FileChooserButtonListener(JTextArea area) {
		messageToUser = area;
	}
	
	public void actionPerformed(ActionEvent e) {
		int returnValue = fileChooser.showOpenDialog(null);			
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getPath();
			f = new File(path);
			try {
				PDDocument doc = PDDocument.load(f);
				PDFTextStripper pdfStripper = new PDFTextStripper();
				String fileTextContent = parseTheFile(pdfStripper, doc);
				//if file contents comply with the needed structure, generate a window with a quiz
				if (verifyContents(fileTextContent)) {
					QuizWindow quizWindow = new QuizWindow(fileTextContent);
					quizWindow.setVisible(true);
				}
				else {
					messageToUser.setText("File contents are not valid, please choose another file");
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
				messageToUser.setText("File format is not valid, please choose another file");
			}
		}			
	}
	
	// extracts plain text from a PDF file
	private String parseTheFile(PDFTextStripper pdfStripper, PDDocument doc) throws IOException {
		return pdfStripper.getText(doc);   
	}
	
	//verifies that the text in the file complies with the required structure
	private boolean verifyContents(String text) {
		int numberSquareBullets = 0;
		int numberRoundBullets = 0;				
		for (int i = 0; i < text.length(); i++) {
		    if (text.charAt(i) == '▪') numberSquareBullets++;  
		    else if (text.charAt(i) == '•') numberRoundBullets++;
		}
		// at least 15 bullet points are needed to generate a quiz
		if (numberSquareBullets>=15 || numberRoundBullets>=15) return true; 
		else return false;
	}

}
