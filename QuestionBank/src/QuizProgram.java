import uk.ac.aber.dcs.mam148.*;
import uk.ac.aber.dcs.mam148.Module;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class runs the quiz program
 *
 * @author Mateusz Mazur
 * @version 20th January 2024
 */
public class QuizProgram {
    private final Module module;
    private final Scanner scanner;
    private QuestionBank questionBank;
    private String filename, languageForStudent, language;

    private QuizProgram() {
        module = new Module("CS12310");
        scanner = new Scanner(System.in);
    }

    /**
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Enter the file name of Question Bank information: ");
        filename = scanner.nextLine();
        System.out.println("Using file " + filename);

        try {
            module.load(filename);
            System.out.println("File loaded successfully");
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + filename + " does not exist.");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /**
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu() {
        while (true) {
            System.out.println("Are you a student or a teacher? (Type 'student' or 'teacher', or 'exit' to quit)");
            String answer = scanner.nextLine().toLowerCase();
            switch (answer) {
                case "student":
                    runStudentMode();
                    break;
                case "teacher":
                    runTeacherMode();
                    break;
                case "exit":
                    System.out.println("Exiting the program...");
                    return;

                default:
                    System.out.println("Invalid input. Please enter 'student', 'teacher', or 'exit'.");
                    break;
            }
        }
    }

    /*
     * runStudentMode() method runs all the methods to start a student mode
     */
    private void runStudentMode() {
        startQuiz();
        module.listQuestionBanksForStudent();
        displayQuiz();
    }

    /**
     * runStudentMode() method runs all the methods to start a teacher mode
     */
    private void runTeacherMode() {
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

    /**
     * printTeacherMode() method prints all the teacher options for question bank
     */
    private void printTeacherMenu() {
        System.out.println("*** WELCOME IN QUESTION BANK MODE ***");
        System.out.println("1 - create a new Question Bank");
        System.out.println("2 - add a new fill the blanks question");
        System.out.println("3 - add a new single choice question");
        System.out.println("4 - list and remove questions");
        System.out.println("5 - list all the question banks for a specific module");
        System.out.println("6 - remove a question bank");
        System.out.println("Q - save and exit\n");
    }

    /*
     * printStudentMode() method prints all the student options for question bank
     */
    private void printQuizMenu() {
        System.out.println("1 - go to previous question");
        System.out.println("2 - go to next question");
        System.out.println("Q - end quiz");
    }

    /**
     * createNewQuestionBank() creates new question bank
     */
    private void createNewQuestionBank() {
        System.out.println("Enter a module identifier (for example: 'AA11111'): ");
        String moduleIdentifier = scanner.nextLine().toUpperCase();

        if (!module.getModuleIdentifier().equals(moduleIdentifier)) {
            System.out.println("Invalid module indetifier");
            return;
        }

        System.out.println("Enter a bank indentifier:");
        String questionBankIdentifier = scanner.nextLine();

        if (questionBankIdentifier.isEmpty()) {
            System.out.println("Invalid bank identifier");
            return;
        }

        questionBank = new QuestionBank(moduleIdentifier, questionBankIdentifier);
        module.addQuestionBank(questionBank);
        System.out.println("Question bank created successfully");
    }

    /**
     * addFillTheBlanks() add Fill The Blank question to a selected question bank
     */
    private void addFillTheBlanks() {
        validateLanguage();

        System.out.println("Enter a question statement (add 'BLANK' in fill space): ");
        String questionStatement = scanner.nextLine();

        System.out.println("Enter the number of blanks: ");
        int blanks;
        try {
            blanks = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for the number of blanks.");
            return;
        }

        if (blanks <= 0) {
            System.out.println("Number of blanks must be a positive integer.");
            return;
        }

        List<String> answers = new ArrayList<>();
        for (int i = 0; i < blanks; i++) {
            System.out.printf("Enter correct answer for blank %d: ", i + 1);
            answers.add(scanner.nextLine());
        }

        FillTheBlanksQ fillTheBlanksQ = new FillTheBlanksQ(questionStatement, blanks, answers, language);

        checkBankIdentifierValidation(fillTheBlanksQ);
    }

    /**
     * addSingleChoice() add Single Choice question to a selected question bank
     */
    private void addSingleChoice() {
        validateLanguage();

        System.out.println("Enter a question statement: ");
        String questionStatement = scanner.nextLine();

        System.out.println("Enter correct answer: ");
        String correctAnswer = scanner.nextLine();

        List<String> answers = new ArrayList<>();
        System.out.println("Enter number of possible answers you want to add new line each including correct answer:");
        int numOfAnswers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numOfAnswers; i++) {
            System.out.printf("Enter answer %d: ", i + 1);
            String answer = scanner.nextLine();
            answers.add(answer);
        }
        List<String> lowercaseAnswers = answers.stream()
                .map(String::toLowerCase)
                .toList();
        String lowercaseCorrectAnswer = correctAnswer.toLowerCase();

        if (!lowercaseAnswers.contains(lowercaseCorrectAnswer)) {
            System.out.println("The correct answer must be one of the provided answers.");
            return;
        }

        SingleChoiceQ singleChoiceQ = new SingleChoiceQ(questionStatement, answers, correctAnswer, language);

        checkBankIdentifierValidation(singleChoiceQ);
    }

    /**
     * checkBankIdentifierValidation() checks if selected bank identifier exists
     */
    private void checkBankIdentifierValidation(Question question) {
        boolean validBank = false;
        while (!validBank) {
            System.out.println("Enter bank identifier to add question to: ");
            String bankIdentifier = scanner.nextLine();

            if (module.doesQuestionBankExist(bankIdentifier)) {
                module.getQuestionBank(bankIdentifier).addQuestion(question);
                System.out.println("Question added successfully.");
                validBank = true;
            } else {
                System.out.println("Error: There is no such question bank.");
                System.out.println("Do you want to try again? (yes/no)");
                String tryAgain = scanner.nextLine().toLowerCase();
                if (!tryAgain.equals("yes")) {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }

    /**
     * validateLanguage() checks if user entered correct language from provided languages
     */
    private void validateLanguage() {
        System.out.println("Enter language of this question (English/Polish): ");
        language = scanner.nextLine().toLowerCase();

        if (!language.equals("polish") && !language.equals("english")) {
            System.out.println("Question bank supports only English and Polish.");
            return;
        }

        if (language.equals("polish")) {
            System.out.println("Reminder: Please add this question in Polish.");
        } else {
            System.out.println("Reminder: Please add this question in English.");
        }
    }

    /**
     * listAndRemoveQuestions() gives user possibility to remove selected question from selected question bank
     */
    private void listAndRemoveQuestions() {
        String answer;
        System.out.println("For which Question Bank you want to list questions? (type bank identifier): ");
        String bankIdentifier = scanner.nextLine();

        if (module.doesQuestionBankExist(bankIdentifier)) {
            System.out.println("This is all your questions: ");
            module.getQuestionBank(bankIdentifier).listQuestions();

            do {
                System.out.println("Do you want to remove question? (y/n)");
                answer = scanner.nextLine().toLowerCase();

                if (answer.equals("y")) {
                    System.out.println("Which one do you want to remove? (number of question): ");
                    int remove = scanner.nextInt();
                    scanner.nextLine();
                    module.getQuestionBank(bankIdentifier).removeQuestion(remove);
                    System.out.println("Your questions now: ");
                    module.getQuestionBank(bankIdentifier).listQuestions();
                } else if (answer.equals("n")) {
                    System.out.println("Continue");
                } else {
                    System.out.println("Invalid input");
                }

            } while (!answer.equals("n"));
        } else {
            System.out.println("There is no such question bank, try again");
        }
    }

    /**
     * listQuestionBanks() prints all question banks for selected module indetifier
     */
    private void listQuestionBanks() {
        System.out.println("Question banks for module: " + module.getModuleIdentifier());
        module.listQuestionBanks();
    }

    /**
     * deleteQuestionBank() deletes selected question bank
     */
    private void deleteQuestionBank() {
        System.out.println("Which question bank do you want to remove? (type bank identifier): ");
        String bankIdentifier = scanner.nextLine();
        if (module.doesQuestionBankExist(bankIdentifier)) {
            System.out.println("Are you sure you want to delete the question bank " + bankIdentifier + "? (y/n)");
            String confirmation = scanner.nextLine().toLowerCase();
            if (confirmation.equals("y")) {
                module.removeQuestionBank(bankIdentifier);
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("There is no such question bank, try again.");
        }
    }

    /**
     * startQuiz() runs the first part of starting a quiz for students
     */
    private void startQuiz() {
        System.out.println("*** WELCOME TO THE QUIZ MODE FOR MODULE " + module.getModuleIdentifier() + " ***");

        boolean validLanguage = false;

        while (!validLanguage) {
            System.out.println("Which language do you want to use? (Polish/English): ");
            languageForStudent = scanner.nextLine().toLowerCase();

            if (languageForStudent.equals("polish") || languageForStudent.equals("english")) {
                validLanguage = true;
            } else {
                System.out.println("Invalid language. Please select either 'Polish' or 'English'.");
            }
        }

        boolean validModuleIdentifier = false;
        String moduleIdentifierStudent;

        do {
            System.out.println("Enter the module identifier: ");
            moduleIdentifierStudent = scanner.nextLine().toUpperCase();

            if (moduleIdentifierStudent.equals(module.getModuleIdentifier())) {
                validModuleIdentifier = true;
            } else {
                System.out.println("Incorrect module identifier. Please try again.");
            }
        } while (!validModuleIdentifier);
    }

    /**
     * displayQuiz() starts the quiz from selecting a question bank, then displays questions and score
     */
    private void displayQuiz() {
        System.out.println("Which question bank do you choose? Enter the bank identifier: ");
        String bankIdentifier = scanner.nextLine();

        if (module.doesQuestionBankExist(bankIdentifier)) {
            QuestionBank selectedBank = module.getQuestionBank(bankIdentifier);
            int bankSize = selectedBank.getQuestions().size();
            System.out.println("That's the number of questions: " + bankSize);


            int numQuestionsToDisplay;
            do {
                System.out.println("How many questions do you want to display? ");
                numQuestionsToDisplay = scanner.nextInt();
                scanner.nextLine();

                if (numQuestionsToDisplay > bankSize) {
                    System.out.println("Requested number of questions exceeds the available questions in the bank.");
                }
            } while (numQuestionsToDisplay > bankSize);


            selectedBank.setCapacity(numQuestionsToDisplay);
            selectedBank.shuffleQuestions();
            questionBank = selectedBank;

            displayQuestions(questionBank);
        } else {
            System.out.println("Invalid question bank identifier. Please try again.");
        }


    }


    /**
     * displayQuestions() displays all the questions and is responsible for a quiz taken by students
     */
    private void displayQuestions(QuestionBank questionBank) {
        int numOfQuestionsToDisplay = questionBank.getCapacity();
        int numOfCorrectAnswers = 0;
        long startTime = System.nanoTime();

        for (int numOfQuestion = 0; numOfQuestion < numOfQuestionsToDisplay; numOfQuestion++) {
            Question question = questionBank.getQuestion(numOfQuestion);
            System.out.println("Question number " + (numOfQuestion + 1) + ": ");
            System.out.println(question.getQuestionStatement());

            if (question instanceof SingleChoiceQ singleChoiceQ) {
                System.out.println(singleChoiceQ.getAnswers());
                System.out.println("Which of these answers is correct (type correct answer): ");
                String answer = scanner.nextLine();
                if (answer.equals(singleChoiceQ.getCorrectAnswer())) {
                    numOfCorrectAnswers++;
                }
            } else if (question instanceof FillTheBlanksQ fillTheBlanksQ) {
                System.out.println("Number of blanks is: " + fillTheBlanksQ.getAnswers().size());
                List<String> answers = new ArrayList<>();
                System.out.println("Enter words/numbers in correct order of blanks in new line each: ");
                for (int i = 0; i < fillTheBlanksQ.getAnswers().size(); i++) {
                    answers.add(scanner.nextLine());
                }
                if (answers.equals(fillTheBlanksQ.getAnswers())) {
                    numOfCorrectAnswers++;
                }
            }

            printQuizMenu();
            String studentOption = scanner.nextLine().toUpperCase();
            if (studentOption.equals("1")) {
                numOfQuestion = Math.max(0, numOfQuestion - 1);
                numOfCorrectAnswers = Math.max(0, numOfCorrectAnswers - 1);
            } else if (studentOption.equals("Q")) {
                break;
            } else if (!studentOption.equals("2")) {
                System.out.println("Invalid input");
            }
        }

        int notAnswered = numOfQuestionsToDisplay - numOfCorrectAnswers;
        long endTime = System.nanoTime() - startTime;

        printScore(endTime, notAnswered, numOfCorrectAnswers, numOfQuestionsToDisplay);
    }

    /**
     * printScore() prints student results after taking quiz
     */
    private void printScore(long endTime, int notAnswered, int numOfCorrectAnswers, int numOfQuestionsToDisplay) {
        double percentageCorrect = ((double) numOfCorrectAnswers / numOfQuestionsToDisplay) * 100;

        System.out.println("------ Quiz Results ------");
        System.out.printf("Your score: %d out of %d questions (%.2f%% correct)%n", numOfCorrectAnswers, numOfQuestionsToDisplay, percentageCorrect);
        System.out.printf("Questions not answered: %d%n", notAnswered);
        System.out.printf("Total time spent: %.2f seconds%n", endTime / 1000000000.0);
        System.out.println("--------------------------\n");
    }

    /**
     * save() method runs from the main and writes back to file
     */
    private void save() {
        try {
            module.save(filename);
        } catch (IOException exception) {
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
