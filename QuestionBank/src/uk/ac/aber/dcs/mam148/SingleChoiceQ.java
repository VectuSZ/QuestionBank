package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
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
        super();
        correctAnswer = "unknown";
    }

    /**
     * Contructor for the Single choice question
     * @param questionStatement The question statement
     * @param answers All possible answers
     * @param correctAnswer Correct answer for the question
     * @param language Language of the question
     */
    public SingleChoiceQ(String questionStatement, List<String> answers, String correctAnswer, String language){
        super(questionStatement, answers, language);
        this.correctAnswer = correctAnswer;
    }

    /**
     * Set the correct answer of the question
     * @param correctAnswer The correct answer of the question
     */
    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
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
        return "Question statement is: " + getQuestionStatement() + "\n" +
                "Question language is: " + getLanguage() +"\n" +
                "Possible answers: " + getAnswers() + "\n" +
                "Correct answer is: " + correctAnswer + "\n";
    }
}
