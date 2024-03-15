package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * To support an individual single choice question
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class SingleChoiceQ extends Question{
    private String correctAnswer;

    /**
     * No argument constructor
     */
    public SingleChoiceQ(){
        questionStatement = "unkmown";
        answers = new String[10];
        language = "english";
        correctAnswer = "unknown";
    }

    /**
     * Contructor for the Single choice question
     * @param questionStatement The question statement
     * @param answers All possible answers
     * @param cAnswer Correct answer for the question
     * @param language Language of the question
     */
    public SingleChoiceQ(String questionStatement, String[] answers, String cAnswer, String language){
        super(questionStatement, answers, language);
        correctAnswer = cAnswer;
    }

    /**
     * Set the correct answer of the question
     * @param newCorrectAnswer The correct answer of the question
     */
    public void setCorrectAnswer(String newCorrectAnswer){
        correctAnswer = newCorrectAnswer;
    }

    /**
     * Obtain the correct answer of the question
     * @return The correct answer of the question
     */
    public String getCorrectAnswer(){
        return correctAnswer;
    }

    /**
     * Reads in information about the single choice question from the file
     */
    @Override
    public void load(Scanner infile){
        super.load(infile);
        correctAnswer = infile.next();
    }

    /**
     * Saves information about the single choice question to the file
     */
    @Override
    public void save(PrintWriter pw){
        pw.println("singlechoice");
        super.save(pw);
        pw.println(correctAnswer);

    }

    /**
     * A basic implementation to just return all the data in string form
     */
    public String toString(){
        return "Question statement is: " + questionStatement + "\n" +
                "Question language is: " + getLanguage() +"\n" +
                "Possible answers: " + Arrays.toString(answers) + "\n" +
                "Correct answer is: " + correctAnswer + "\n";
    }
}
