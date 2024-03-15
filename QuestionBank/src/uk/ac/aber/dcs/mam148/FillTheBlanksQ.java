package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * To support an individual fill the blanks question
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class FillTheBlanksQ extends Question{
    private int numOfBlanks;

    /**
     * No argument constructor
     */
    public FillTheBlanksQ(){
        questionStatement = "unknown";
        answers = new String[1];
        language = "english";
        numOfBlanks = 10;
    }

    /**
     * Constructor for the fill the blanks question
     * @param questionStatement The question statement
     * @param numOfB Number of blanks
     * @param answers All answers of the question
     * @param language Language of the question
     */
    public FillTheBlanksQ(String questionStatement, int numOfB ,String[] answers, String language){
        super(questionStatement, answers, language);
        numOfBlanks = numOfB;
    }

    /**
     * Set the number of blanks of the question
     * @param newNumOfBlanks The number of blanks
     */
    public void setNumOfBlanks(int newNumOfBlanks){
        numOfBlanks = newNumOfBlanks;
    }

    /**
     * Obtain the number of blanks of the question
     * @return The number of blanks
     */
    public int getNumOfBlanks(){
        return numOfBlanks;
    }

    /**
     * Reads in information about the fill the blanks question from the file
     */
    @Override
    public void load(Scanner infile){
        super.load(infile);
    }

    /**
     * Saves information about the fill the blanks question to the file
     */
    @Override
    public void save(PrintWriter pw){
        pw.println("filltheblanks");
        super.save(pw);
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    public String toString(){
        return "Question statement is: " + questionStatement + "\n" +
                "Question language is: " + getLanguage() + "\n" +
                "Number of missing words/numbers: " + answers.length + "\n" +
                "Missing words/numbers: " + Arrays.toString(answers) + "\n";
    }

}
