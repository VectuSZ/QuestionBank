package uk.ac.aber.dcs.mam148;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a module
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class Module {
    private String moduleIdentifier;
    private final List<QuestionBank> questionBanks;

    /**
     * No argument constructor
     */
    public Module() {
        moduleIdentifier = "";
        questionBanks = new ArrayList<>();
    }

    /**
     * Constructor for the module
     * @param moduleIdentifier The module's identifier
     */
    public Module(String moduleIdentifier) {
        this.moduleIdentifier = moduleIdentifier;
        questionBanks = new ArrayList<>();
    }

    /**
     * Obtain the identifier of the module
     * @return Module identifier
     */
    public String getModuleIdentifier() {
        return moduleIdentifier;
    }

    /**
     * Set the identifier of the module
     * @param moduleIdentifier Module identifier
     */
    public void setModuleIdentifier(String moduleIdentifier) {
        this.moduleIdentifier = moduleIdentifier;
    }

    /**
     * Obtain the question bank by its bank identifier
     * @param bankIdentifier bank identifier you want get
     * @return Question bank
     */
    public QuestionBank getQuestionBank(String bankIdentifier) {
        return questionBanks.stream().filter(qb -> bankIdentifier.equals(qb.getBankIdentifier())).findFirst().orElse(null);

    }

    /**
     * Add question bank to question banks arraylist
     * @param questionBank question bank you want to add
     */
    public void addQuestionBank(QuestionBank questionBank) {
        questionBanks.add(questionBank);
    }

    /**
     * List all question banks from the module
     */
    public void listQuestionBanks() {
        System.out.println(questionBanks);
    }

    /**
     * List all question banks from the module only by its question bank identifier
     */
    public void listQuestionBanksForStudent(){
        System.out.println("All available question banks: ");
        questionBanks.forEach(qb -> System.out.println("Question bank: " + qb.getBankIdentifier()));
    }

    /**
     * Remove question bank from the module by its bank identifier
     * @param bankIdentifier Question bank identifier you want to remove
     */
    public void removeQuestionBank(String bankIdentifier) {
        if (getQuestionBank(bankIdentifier).getQuestions().isEmpty()) {
            questionBanks.remove(getQuestionBank(bankIdentifier));
            System.out.println("Question bank " + bankIdentifier + " has been successfully deleted.");
        } else {
            System.out.println("You can't remove question bank if it's not empty");
        }
    }

    /**
     * Checks if given question bank exists in the module
     * @param bankIdentifier Question bank identifier you want to check if exists
     * @return true if exists, false if doesn't exist
     */
    public boolean doesQuestionBankExist(String bankIdentifier){
        return questionBanks.stream().anyMatch(qb -> bankIdentifier.equals(qb.getBankIdentifier()));
    }

    /**
     * Reads in information about the module from the file
     */
    public void load(String infileName) throws IOException {
        try (FileReader fr = new FileReader(infileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            infile.useDelimiter("\r?\n|\r");
            moduleIdentifier = infile.next();
            infile.next();

            while (infile.hasNext()) {
                    QuestionBank questionBank = new QuestionBank();
                    questionBank.load(infile);
                    this.addQuestionBank(questionBank);
                }
        }
    }


    /**
     * Saves information about module to the file
     */
    public void save(String filename) throws IOException{
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {

            outfile.println(moduleIdentifier);
            for(QuestionBank qb : questionBanks){
                outfile.println("newquestionbank");
                qb.save(outfile);
            }
        }
    }

    /**
     * A basic implementation to just return all the data in string form
     */
    @Override
    public String toString() {
        return "Module{" +
                "moduleIdentifier='" + moduleIdentifier + '\'' +
                ", questionBanks=" + questionBanks +
                '}';
    }
}
