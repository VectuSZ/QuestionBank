package uk.ac.aber.dcs.mam148;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a Question bank
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class QuestionBank {
    private String moduleIdentifier;
    private String bankIdentifier;
    private final List<Question> questions;
    private int capacity;

    /**
     * No argument constructor
     */
    public QuestionBank(){
        moduleIdentifier = "";
        bankIdentifier = "";
        questions = new ArrayList<>();
    }

    /**
     * Constructor for the question bank
     * @param moduleIdentifier The module's identifier
     * @param bankIdentifier The bank's identifier
     */
    public QuestionBank(String moduleIdentifier, String bankIdentifier){
        this.moduleIdentifier = moduleIdentifier;
        this.bankIdentifier = bankIdentifier;
        questions = new ArrayList<>();
    }

    /**
     * Set the identifier of the module which is assign to the question bank
     * @param moduleIdentifier The identifier of the module
     */
    public void setModuleIdentifier(String moduleIdentifier) {
        this.moduleIdentifier = moduleIdentifier;
    }

    /**
     * Set the identifier of the question bank
     * @param bankIdentifier The identifier of the question bank
     */
    public void setBankIdentifier(String bankIdentifier) {
        this.bankIdentifier = bankIdentifier;
    }

    /**
     * Obtain the identifier of the module
     * @return The identifier of the module
     */
    public String getModuleIdentifier() {
        return moduleIdentifier;
    }

    /**
     * Obtain the identifier of the question bank
     * @return The identifier of the question bank
     */
    public String getBankIdentifier() {
        return bankIdentifier;
    }

    /**
     * Set the capacity of the question bank
     * @param capacity The capacity of the question bank
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Obtain the capacity of the question bank
     * @return The capacity of the question bank
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Obtain questions from the question bank
     * @return Questions arraylist
     */
    public List<Question> getQuestions(){
        return questions;
    }

    /**
     * Obtain the question from the question bank
     * @param number Number of question
     * @return Question by its number
     */
    public Question getQuestion(int number){
        return questions.get(number);
    }

    /**
     * To add a question to question bankk
     * @param question Quesiton you want to add
     */
    public void addQuestion(Question question){
        questions.add(question);
    }

    /**
     * List all question from the question bank with its number
     */
    public void listQuestions(){
        questions.forEach(question -> System.out.println("Question number " + (questions.indexOf(question) + 1) + ":\n" + question));
    }

    /**
     * To remove question from the question bank by its number
     * @param questionNumber Number of the question
     */
    public void removeQuestion(int questionNumber){
        Question newQ = null;
        for(Question q : questions){
            if(questions.indexOf(q) + 1 == questionNumber){
                newQ = q;
            }
        }
        if(newQ != null){
            questions.remove(newQ);
            System.out.println("You've removed question number: " + questionNumber);
        }
        else{
            System.out.println("Invalid number of question, try again");
        }
    }

    /**
     * To shuffle all questions in questions arraylist
     */
    public void shuffleQuestions(){
        Collections.shuffle(questions);
    }

    /**
     * Reads in Question bank information from the file
     */
    public void load(Scanner infile){

        moduleIdentifier = infile.next();
        bankIdentifier = infile.next();

        while (infile.hasNext()){
            Question question = null;
            String type = infile.next();
            switch (type){
                case "singlechoice":
                    question = new SingleChoiceQ();
                    break;
                case "filltheblanks":
                    question = new FillTheBlanksQ();
                    break;
                case "newquestionbank":
                    break;
            }
            if(question != null){
                question.load(infile);
                this.addQuestion(question);
            }
            if(question == null){
                break;
            }
        }
    }

    /**
     * Saves the Question bank information
     */
    public void save(PrintWriter pw) throws IOException{

            pw.println(moduleIdentifier);
            pw.println(bankIdentifier);
            questions.forEach(question -> question.save(pw));
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    public String toString(){
        return  "Question Bank module identifier: " + moduleIdentifier + "\n" +
                "Question Bank identifier: " + bankIdentifier + "\n" +
                "Questions: " + questions + "\n";
    }
}
