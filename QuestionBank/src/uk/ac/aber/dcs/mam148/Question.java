package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * To support an individual question
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class Question {
    String questionStatement;
    String[] answers;
    String language;

    /**
     * No argument constructor
     */
    public Question(){
        questionStatement = "unknown";
        answers = new String[1];
        language = "english";
    }

    /**
     * Contructor for the question
     * @param questionStatement The question statement
     * @param answers Answers for the question
     * @param language Language of the question
     */
    public Question(String questionStatement, String[] answers, String language){
        this.questionStatement = questionStatement;
        this.answers = answers;
        this.language = language;
    }

    /**
     * Set the statement of the question
     * @param newQuestionStatement Statement of the question
     */
    public void setQuestionStatement(String newQuestionStatement){
        questionStatement = newQuestionStatement;
    }

    /**
     * Obtain the statement of the question
     * @return Statement of The question
     */
    public String getQuestionStatement(){
        return questionStatement;
    }

    /**
     * Set answers for the question
     * @param newAnswers Answers for the question
     */
    public void setAnswers(String[] newAnswers){
        answers = newAnswers;
    }

    /**
     * Obtain answers for the question
     * @return Answers for the question
     */
    public String[] getAnswers(){
        return answers;
    }

    /**
     * Set the language of the question
     * @param newLanguage Language of the question
     */
    public void setLanguage(String newLanguage){
        language = newLanguage;
    }

    /**
     * Obtain the language of the question
     * @return Language of the question
     */
    public String getLanguage(){
        return language;
    }

    /**
     * Reads in Question information from the file
     */
    public void load(Scanner infile){

        questionStatement = infile.next();
        language = infile.next();
        int numAnswers = infile.nextInt();
        answers = new String[numAnswers];
        for(int count = 0; count < numAnswers; count++){
            String answer = infile.next();
            answers[count] = answer;
        }
    }

    /**
     * Saves the Question information
     */
    public void save(PrintWriter pw){
        pw.println(questionStatement);
        pw.println(language);
        pw.println(answers.length);
        for(int count = 0; count < answers.length; count++){
            pw.println(answers[count]);
        }
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    @Override
    public String toString() {
        return "Question{" +
                "question='" + questionStatement + '\'' +
                ", answers=" + Arrays.toString(answers) +
                '}';
    }
}
