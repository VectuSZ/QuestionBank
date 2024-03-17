package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
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
        super();
    }

    /**
     * Constructor for the fill the blanks question
     * @param questionStatement The question statement
     * @param numOfBlanks Number of blanks
     * @param answers All answers of the question
     * @param language Language of the question
     */
    public FillTheBlanksQ(String questionStatement, int numOfBlanks, List<String> answers, String language){
        super(questionStatement, answers, language);
        this.numOfBlanks = numOfBlanks;
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
        numOfBlanks = infile.nextInt();
    }

    /**
     * Saves information about the fill the blanks question to the file
     */
    @Override
    public void save(PrintWriter pw){
        pw.println("filltheblanks");
        super.save(pw);
        pw.println(numOfBlanks);
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    public String toString(){
        return "Question statement is: " + getQuestionStatement() + "\n" +
                "Question language is: " + getLanguage() + "\n" +
                "Number of missing words/numbers: " + getNumOfBlanks() + "\n" +
                "Missing words/numbers: " + getAnswers() + "\n";
    }

}
