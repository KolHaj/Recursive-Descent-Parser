package RDP_GUI;

/**
* File: MainParse.java
* Author: Kolger Hajati
* Date: April 12, 2019
* Purpose: The file takes in text input file to generate
* a GUI calculator using recursive descent parser and stack
*/

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class MainParse {
    //Variables
    private BufferedReader BR;
    private static Stack <String> charValue;
    private JFrame windowBody;
    private boolean windowCheck;
    private JPanel body;
    private String textValue;
    private ButtonGroup groupR;
    private JRadioButton buttonR;
    private JTextField textInput;
    private final String characterSplit = "(?<=[(),\".;:])|(?=[(),\".;:])| ";

	public static void main (String[] args){
        MainParse startGUI = new MainParse();
    }
    //Starts parse process
    public MainParse() {
    	charValue = new Stack <String>();
        retrieveFile();
    }
    /**
     * Analyzing of input text file
     */
    private void retrieveFile() {
        /**
         * JFileChooser opens current source code directory for easy resources access
         * The test files are located in the resources folder
         */
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();

        try {
            //Open the save dialog
            //file.showSaveDialog(null);
        	//File file = new File("C:\\Source_Code\\IntelliJ_Work\\RecursiveDP_GUI\\src\\main\\resources\\Nest_Panel.txt");
            BR = new BufferedReader(new FileReader(file));
            inputBreak();
            reverse();
            descentParser();
        } 
        catch (IOException e){
        	e.printStackTrace(); 
        }
    }
    /**
     * This function holds the tokenized pieces of the text file
     * It breaks up each line
     */
	private void inputBreak() {
		try {
			//Variables
			String value;
			String space = "";
			boolean quotes = false;

			while ((value = BR.readLine()) != null) {
				value = value.trim();
				String [] split = value.trim().split(characterSplit);
				for (String lineE : split) {
					lineE = lineE.trim();

					if (quotes == true) {
						if (lineE.equals("\"")) {

							charValue.push(space.trim());
							space = "";
							charValue.push(lineE);
						} 
						else {
							space += lineE + " ";
						}
					} 
					else if (lineE.trim().length() > 0) {
						charValue.push(lineE.trim());
					}
					if (lineE.equals("\"")) {
						quotes = !quotes;
					}
				}
			}

		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Alert", "Error in file content!",
                    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(); 
			return;
		}
	}
	/**
	 * Using a recursive function to reorder the stack
	 */
    void inputTurn(String x) { 
        if(charValue.isEmpty()) 
        	charValue.push(x); 
        else { 
            String a = charValue.peek(); 
            charValue.pop(); 
            inputTurn(x); 
            charValue.push(a); 
        } 
    } 
    void reverse() { 
        if(charValue.size() > 0) {   
            String x = charValue.peek(); 
            charValue.pop(); 
            reverse(); 
            inputTurn(x); 
        } 
    } 
    /**
     * Recursive descent is used with if statement to check each string value
     * Errors are checked in each section to be reported.
     */
    private void descentParser () {
    	//Variables
    	int width, height;
    	//JFrame
        if (charValue.peek().equalsIgnoreCase("Window")) {
            windowCheck = true;
            windowBody = new JFrame();
            
            windowBody.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            charValue.pop();
            //Title and width
            if (charValue.peek().equals("\"")) {
            	charValue.pop();
                windowBody.setTitle(charValue.peek());
                charValue.pop();

                if (charValue.peek().equals("\"")) {
                	charValue.pop();
                	
                    if (charValue.peek().equals("(")) {
                    	charValue.pop();
                        try {
                            width = Integer.parseInt(charValue.peek());

                        } catch (NumberFormatException e) {
                        	JOptionPane.showMessageDialog(null, "Alert", "Error in width!", JOptionPane.ERROR_MESSAGE);
                        	e.printStackTrace(); 
                            return;
                        }
                        //Height
                        charValue.pop();
                        if (charValue.peek().equals(",")) {
                        	charValue.pop();
                            try {
                                height = Integer.parseInt(charValue.peek());
                            } catch (NumberFormatException e) {
                            	JOptionPane.showMessageDialog(null, "Alert", "Error in height!", JOptionPane.ERROR_MESSAGE);
                            	e.printStackTrace(); 
                                return;
                            }
                            charValue.pop();

                            //Sets width and height and End
                            if (charValue.peek().equals(")")) {
                                windowBody.setSize(width, height);

                                charValue.pop();
                                if (layoutP()) {

                                    if (widgetP()) {

                                        if (charValue.peek().equalsIgnoreCase("End")) {
                                        	charValue.pop();
                                            if (charValue.peek().equals(".")) {

                                                windowBody.setVisible(true);
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Error GUI", "Alert", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Layout of the window
     */
    private boolean layoutInput(){
    	//Variables
        int rowValue, columnValue, spaceColumn, spaceRow;
        
        if(charValue.peek().equalsIgnoreCase("Flow")){
            if(windowCheck){
                windowBody.setLayout(new FlowLayout());
            }
            else{
                body.setLayout(new FlowLayout());
            }
            charValue.pop();
            return true;
        }
        else if(charValue.peek().equalsIgnoreCase("Grid")){
        	charValue.pop();
            //Checks and parse column and row
            if(charValue.peek().equals("(")){
            	charValue.pop();
                try{
                	rowValue = Integer.parseInt(charValue.peek());
                }
                catch(NumberFormatException e){
                	JOptionPane.showMessageDialog(null, "Invalid Row Value", "Alert", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                charValue.pop();
                if(charValue.peek().equals(",")){
                	charValue.pop();
                    try{
                    	columnValue = Integer.parseInt(charValue.peek());
                    }
                    catch(NumberFormatException e){
                    	JOptionPane.showMessageDialog(null, "Invalid Column Value", "Alert", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    charValue.pop();
                    //Sets values
                    if(charValue.peek().equals(")")){
                        if(windowCheck){
                            windowBody.setLayout(new GridLayout(rowValue,columnValue));   
                        }
                        else{
                            body.setLayout(new GridLayout(rowValue,columnValue));
                        }
                        charValue.pop();
                        return true;
                    }
                    //Checks and parse column space and row space
                    else if(charValue.peek().equals(",")){
                    	charValue.pop();
                        try{
                        	spaceColumn = Integer.parseInt(charValue.peek());
                        }
                        catch(NumberFormatException e){
                        	JOptionPane.showMessageDialog(null, "Invalid Column Space", "Alert", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        charValue.pop();
                        if(charValue.peek().equals(",")){
                        	charValue.pop();
                            try{
                            	spaceRow = Integer.parseInt(charValue.peek());
                            }
                            catch(NumberFormatException e){
                            	JOptionPane.showMessageDialog(null, "Invalid Row Space", "Alert", JOptionPane.ERROR_MESSAGE);
                                return false;
                            }
                            charValue.pop();
                            if(charValue.peek().equals(")")){
                                if(windowCheck){
                                    windowBody.setLayout(new GridLayout(rowValue, columnValue, spaceColumn, spaceRow));
                                }
                                else{
                                    body.setLayout(new GridLayout(rowValue, columnValue, spaceColumn, spaceRow));
                                }
                                charValue.pop();
                                return true;
                            }
                        }
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Layout Parse Error", "Alert", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    /**
     * Checking and parsing layout
     */
    private boolean layoutP(){
        if(charValue.peek().equalsIgnoreCase("Layout")){
        	charValue.pop();
            if(layoutInput()){
                if(charValue.peek().equals(":")){
                	charValue.pop();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Function that handles recursion of widget sections
     */
    private boolean widgetP(){
        if(parseWidget()){
            if(widgetP()){
                return true;
            }
            return true;
        }
        return false;
    }
    /**
     * Function that handles recursion of radio button
     */
    private boolean radioP(){
        if(parseRadioButton()){
            if(radioP()){
                return true;
            }
            return true;
        }
        return false;
    }
    /**
     * Each widget is parsed matching the grammar
     */
    private boolean parseWidget(){
        
        if(charValue.peek().equalsIgnoreCase("Button")){
        	charValue.pop();
            if(charValue.peek().equals("\"")){
            	charValue.pop();
                textValue = charValue.peek();
                charValue.pop();
                if(charValue.peek().equals("\"")){
                	charValue.pop();
                    if(charValue.peek().equals(";")){
                        if(windowCheck){
                            JButton button = new JButton(textValue);
                            button.addActionListener((ActionEvent e) -> {
                            	textInput.setText(button.getText());
                            });
                            windowBody.add(button);
                        }
                        else{
                            JButton button = new JButton(textValue);
                            button.addActionListener((ActionEvent e) -> {
                            	textInput.setText(button.getText());
                            });
                            body.add(button);
                        }
                        charValue.pop();
                        return true;
                    }
                }
            }
        }
        else if(charValue.peek().equalsIgnoreCase("Group")){
            groupR = new ButtonGroup();
            charValue.pop();
            if(radioP()){
                if(charValue.peek().equalsIgnoreCase("End")){
                	charValue.pop();
                    if(charValue.peek().equals(";")){
                    	charValue.pop();
                        return true;
                    }
                }
            }
        }
        else if(charValue.peek().equalsIgnoreCase("Label")){
        	charValue.pop();
            if(charValue.peek().equals("\"")){
            	charValue.pop();
                textValue = charValue.peek();
                charValue.pop();
                if(charValue.peek().equals("\"")){
                	charValue.pop();
                    if(charValue.peek().equals(";")){
                        if(windowCheck){
                            windowBody.add(new JLabel(textValue));
                        }
                        else{
                            body.add(new JLabel(textValue));
                        }
                        charValue.pop();
                        return true;
                    }
                }
            }
        }
        else if(charValue.peek().equalsIgnoreCase("Panel")){
            if(windowCheck){
                windowBody.add(body = new JPanel());
            }
            else{
                body.add(body = new JPanel());
            }
            windowCheck = false;
            charValue.pop();
            if(layoutP()){
                if(widgetP()){
                    if(charValue.peek().equalsIgnoreCase("End")){
                    	charValue.pop();
                        if (charValue.peek().equals(";")) {
                        	charValue.pop();
                            return true;
                        }
                    }
                }
            }
        }    
        else if(charValue.peek().equalsIgnoreCase("Textfield")){
            int length;
            charValue.pop();
            try{
                length = Integer.parseInt(charValue.peek());
            }
            catch(NumberFormatException e){
            	JOptionPane.showMessageDialog(null, "Error In Text", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            charValue.pop();
            if(charValue.peek().equals(";")){
                if(windowCheck){
                    windowBody.add(textInput = new JTextField(length));
                }
                else{
                    body.add(textInput = new JTextField(length));
                }
                charValue.pop();
                return true;
            }
        }
        return false;
    }
    /**
     * Parses radio buttons
     */
    private boolean parseRadioButton(){
        if (charValue.peek().equalsIgnoreCase("Radio")) {
        	charValue.pop();
            if (charValue.peek().equals("\"")) {
            	charValue.pop();
                textValue = charValue.peek();
                charValue.pop();
                if (charValue.peek().equals("\"")) {
                	charValue.pop();
                    if (charValue.peek().equals(";")) {
                    	buttonR = new JRadioButton(textValue);
                        groupR.add(buttonR);
                        if (windowCheck) {
                            windowBody.add(buttonR);
                        } 
                        else {
                            body.add(buttonR);
                        }
                        charValue.pop();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}