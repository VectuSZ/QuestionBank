import uk.ac.aber.dcs.mam148.*;
import uk.ac.aber.dcs.mam148.Module;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class runs the quiz program
 *
 * @author Mateusz Mazur
 * @version 25th April 2021
 */
public class QuizProgram {
    private Module module;
    private Scanner scanner;
    private QuestionBank questionBank;
    private int numOfCorrectAnswers, Q, notAnswered;
    private long endTime;
    private String filename, languageForStudent;

    private QuizProgram(){
        module = new Module("CS12310");

        scanner = new Scanner(System.in);
        System.out.println("Enter the file name of Question Bank information: ");
        filename = scanner.nextLine();

        questionBank = new QuestionBank();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            module.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + filename + " does not exist.");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu(){
        System.out.println("Who you are? Student/Teacher");
        String answer = scanner.nextLine().toLowerCase();
        if(answer.equals("student")){
            startQuiz();
            module.listQuestionBanksForStudent();
            selectQuestionBank();
            displayQuestions();
            printScore();
        }
        else if(answer.equals("teacher")) {
            String teacherOption;
            do {
                printTeacherMenu();
                teacherOption = scanner.nextLine().toUpperCase();
                switch (teacherOption) {
                    case "1":
                        createNewQuestionBank();
                        break;
                    case "2":
                        addFillTheBlanks();
                        break;
                    case "3":
                        addSingleChoice();
                        break;
                    case "4":
                        listAndRemoveQuestions();
                        break;
                    case "5":
                        listQuestionBanks();
                        break;
                    case "6":
                        deleteQuestionBank();
                        break;
                    case "Q":
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            } while (!teacherOption.equals("Q"));
        }
        else{
            System.out.println("Invalid input");
        }
    }


    private void printTeacherMenu(){
        System.out.println("*** WELCOME IN QUESTION BANK MODE ***");
        System.out.println("1 - create a new Question Bank");
        System.out.println("2 - add a new fill the blanks question");
        System.out.println("3 - add a new single choice question");
        System.out.println("4 - list and remove questions");
        System.out.println("5 - list all the question banks for a specific module");
        System.out.println("6 - remove a question bank");
        System.out.println("Q - save and exit\n");
    }

    private void printStudentMenu(){
        System.out.println("1 - go to previous question");
        System.out.println("2 - go to next question");
        System.out.println("Q - end quiz");
    }


    private void createNewQuestionBank(){
        System.out.println("Enter a module identifier (for example: 'AA11111'): ");
        String moduleIdentifier = scanner.nextLine().toUpperCase();
        if(module.getModuleIdentifier().equals(moduleIdentifier)) {
            System.out.println("Enter a bank identifier: ");
            String questionBankIdentifier = scanner.nextLine();
            questionBank = new QuestionBank(moduleIdentifier, questionBankIdentifier);
            module.addQuestionBank(questionBank);
        }
        else {
            System.out.println("Not valid module identifier, try again.");
        }
    }

    private void addFillTheBlanks(){
        System.out.println("Enter language of this question (English/Polish): ");
        String language = scanner.nextLine().toLowerCase();
        if(!language.equals("polish") && (!language.equals("english"))){
            System.out.println("Question bank supports only English and Polish");
        }
        else {
            if (language.equals("polish")) {
                System.out.println("Don't forget to add this question in English");
            } else {
                System.out.println("Don't forget to add this question in Polish");
            }
            System.out.println("Enter a question statement (add 'BLANK' in fill space): ");
            String questionStatement = scanner.nextLine();
            System.out.println("Enter a number of blanks: ");
            int blanks = scanner.nextInt();
            String[] answers = new String[blanks];
            System.out.println("Enter correct answers instead of 'BLANK' in new line each: ");
            for (int i = 0; i < blanks; i++) {
                answers[i] = scanner.next();
                scanner.nextLine();
            }
            FillTheBlanksQ fillTheBlanksQ = new FillTheBlanksQ(questionStatement, blanks, answers, language);
            System.out.println("Enter bank identifier to add question to: ");
            String bankIdentifier = scanner.nextLine();
            if (module.doesQuestionBankExist(bankIdentifier)) {
                module.getQuestionBank(bankIdentifier).addQuestion(fillTheBlanksQ);
                System.out.println(fillTheBlanksQ);
            } else {
                System.out.println("There is no such question bank, try again");
            }
        }
    }

    private void addSingleChoice(){
        System.out.println("Enter language of this question (English/Polish): ");
        String language = scanner.nextLine().toLowerCase();
        if(!language.equals("polish") && (!language.equals("english"))){
            System.out.println("Question bank supports only English and Polish");
        }
        else {
            if (language.equals("polish")) {
                System.out.println("Don't forget to add this question in English");
            } else {
                System.out.println("Don't forget to add this question in Polish");
            }
            System.out.println("Enter a question statement: ");
            String questionStatement = scanner.nextLine();
            System.out.println("Enter correct answer: ");
            String correctAnswer = scanner.nextLine();
            System.out.println("Enter ten possible answers in new line each including correct answer: ");
            String[] answers = new String[10];
            for (int i = 0; i < answers.length; i++) {
                answers[i] = scanner.next();
                scanner.nextLine();
            }
            boolean test = false;
            for (String ans : answers) {
                if (correctAnswer.equals(ans)) {
                    test = true;
                    break;
                }
            }
            if (!test) {
                System.out.println("There is no correct answer is all possible answers, try again.");
            } else {
                SingleChoiceQ singleChoiceQ = new SingleChoiceQ(questionStatement, answers, correctAnswer, language);
                System.out.println("Enter bank identifier to add question to: ");
                String bankIdentifier = scanner.nextLine();
                if (module.doesQuestionBankExist(bankIdentifier)) {
                    module.getQuestionBank(bankIdentifier).addQuestion(singleChoiceQ);
                    System.out.println(singleChoiceQ);
                } else {
                    System.out.println("There is no such question bank, try again");
                }
            }
        }
    }

    private void listAndRemoveQuestions(){
        String answer;
        System.out.println("For which Question Bank you want to list questions? (type bank identifier): ");
        String bankIdentifier = scanner.nextLine();
        if(module.doesQuestionBankExist(bankIdentifier)){
            System.out.println("This is all your questions: ");
            module.getQuestionBank(bankIdentifier).listQuestions();
            do {
                System.out.println("Do you want to remove question? (y/n)");
                answer = scanner.nextLine().toLowerCase();
                if(answer.equals("y")) {
                    System.out.println("Which one do you want to remove? (number of question): ");
                    int remove = scanner.nextInt();
                    scanner.nextLine();
                    module.getQuestionBank(bankIdentifier).removeQuestion(remove);
                    System.out.println("Your questions now: ");
                    module.getQuestionBank(bankIdentifier).listQuestions();
                }
                else if(answer.equals("n")){
                }
                else{
                    System.out.println("Invalid input");
                }
            }while(!answer.equals("n"));
        }
        else {
            System.out.println("There is no such question bank, try again");
        }
    }

    private void listQuestionBanks(){
        System.out.println("Question banks for module: " + module.getModuleIdentifier());
        module.listQuestionBanks();
    }

    private void deleteQuestionBank(){
        System.out.println("Which question bank do you want to remove? (type bank identifier): ");
        String bankIdentifier = scanner.nextLine();
        if(module.doesQuestionBankExist(bankIdentifier)){
            module.removeQuestionBank(bankIdentifier);
        }
        else {
            System.out.println("There is no such question bank, try again.");
        }
    }

    private void startQuiz(){
        System.out.println("*** WELCOME IN QUIZ MODE FOR MODULE " + module.getModuleIdentifier() +  " ***");
        System.out.println("Which language do you want to use? (Polish/English): ");
        languageForStudent = scanner.nextLine().toLowerCase();
        if(languageForStudent.equals("polish") || languageForStudent.equals("english")) {
            System.out.println("Enter correct module identifier: ");
            String moduleIdentifierStudent = scanner.nextLine().toUpperCase();
            while (!moduleIdentifierStudent.equals(module.getModuleIdentifier())) {
                System.out.println("Incorrect module identifier");
                System.out.println("Enter correct module indentifier: ");
                moduleIdentifierStudent = scanner.nextLine().toUpperCase();
            }
        }
        else{
            System.out.println("Invalid language");
        }
    }

    private void selectQuestionBank(){
        System.out.println("Which question bank do you choose? Enter bank identifier: ");
        String whichQuestionBank = scanner.nextLine();
        if(module.doesQuestionBankExist(whichQuestionBank)){
            System.out.println("How many question do you want to display? ");
            Q = scanner.nextInt();
            scanner.nextLine();
            if(Q > module.getQuestionBank(whichQuestionBank).getQuestions().size()){
                module.getQuestionBank(whichQuestionBank).setCapacity(Q);
            }
            module.getQuestionBank(whichQuestionBank).shuffleQuestions();
            questionBank = module.getQuestionBank(whichQuestionBank);
        }
        else{
            System.out.println("Invalid Question bank");
        }
    }


    private void displayQuestions(){
        QuestionBank studentQuestionBank = new QuestionBank();
        for(Question q : questionBank.getQuestions()){
            if(languageForStudent.equals(q.getLanguage())){
                studentQuestionBank.addQuestion(q);
            }
        }
        if(Q > studentQuestionBank.getQuestions().size()) {
            Q = studentQuestionBank.getQuestions().size();
        }
        numOfCorrectAnswers = 0;
        long startTime = System.nanoTime();
        int numOfQuestion;
        for(numOfQuestion = 0; numOfQuestion < Q;){
            printStudentMenu();
            String studentOption = scanner.nextLine().toUpperCase();
            if(studentOption.equals("1")){
                numOfQuestion--;
                numOfCorrectAnswers--;
            }
            else if(studentOption.equals("2")){
            }
            else if(studentOption.equals("Q")){
                break;
            }
            else{
                System.out.println("Invalid input");
            }
            notAnswered = Q;
            Question q = studentQuestionBank.getQuestion(numOfQuestion);
            System.out.println("Question number " + (numOfQuestion + 1) + ": ");
            System.out.println(q.getQuestionStatement());
            numOfQuestion++;
            if(q instanceof SingleChoiceQ){
                System.out.println(Arrays.toString(q.getAnswers()));
                System.out.println("Which of these answers is correct (type correct answer): ");
                String answer = scanner.nextLine();
                if(answer.equals(((SingleChoiceQ) q).getCorrectAnswer())){
                    numOfCorrectAnswers++;
                }
            }
            if(q instanceof FillTheBlanksQ){
                System.out.println("Number of blanks is: " + q.getAnswers().length);
                String[] answers = new String[q.getAnswers().length];
                System.out.println("Enter words/numbers in correct order of blanks in new line each: ");
                for(int i = 0; i < answers.length; i++){
                    answers[i] = scanner.next();
                    scanner.nextLine();
                }
                if(Arrays.equals(answers, q.getAnswers())){
                    numOfCorrectAnswers++;
                }
            }
        }
        notAnswered = notAnswered - numOfQuestion;
        endTime = System.nanoTime() - startTime;
    }

    private void printScore(){
        System.out.println("Your score is " + numOfCorrectAnswers + " out of " + Q + " questions.");
        System.out.println("You haven't answered " + notAnswered + " questions.");
        System.out.println("You've spent " + endTime/1000000000 + " seconds on this quiz.\n");

    }

    /*
     * save() method runs from the main and writes back to file
     */
    private void save(){
        try {
            module.save(filename);
        } catch (IOException exception){
            System.err.println("Problem when trying to write to file: " + filename);
        }
    }

    public static void main(String[] args) {
        QuizProgram demo = new QuizProgram();
        demo.initialise();
        demo.runMenu();
        demo.save();
    }
}
