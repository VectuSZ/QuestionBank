package uk.ac.aber.dcs.mam148;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * To support an individual question
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class Question {
    private String questionStatement;
    private List<String> answers;
    private String language;

    /**
     * No argument constructor
     */
    public Question(){
        this("unknown", new ArrayList<>(), "english");
    }

    /**
     * Contructor for the question
     * @param questionStatement The question statement
     * @param answers Answers for the question
     * @param language Language of the question
     */
    public Question(String questionStatement, List<String>answers, String language){
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
     * @param answers Answers for the question
     */
    public void setAnswers(List<String> answers){
        this.answers = answers;
    }

    /**
     * Obtain answers for the question
     * @return Answers for the question
     */
    public List<String> getAnswers(){
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
        answers = new ArrayList<>();
        for(int count = 0; count < numAnswers; count++){
            answers.add(infile.next());
        }
    }

    /**
     * Saves the Question information
     */
    public void save(PrintWriter pw){
        pw.println(questionStatement);
        pw.println(language);
        pw.println(answers.size());
        answers.forEach(pw::println);
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    @Override
    public String toString() {
        return "Question{" +
                "question='" + questionStatement + '\'' +
                ", answers=" + answers +
                '}';
    }
}
