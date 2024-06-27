/*==================================================
Author: Kyle Stranick
Class: ITN261
Class Section: 201
Assignment: Assignment 4: Score Tracker
Notes:
-future changes to the program: adding more teams, handling overtime scenarios,
saving/loading game data, class expansion, GUI interface, input for time scored
-look up other exceptions, maybe I can use logic instead of try catch on easy programs
=====================================================
Code adapted from:
my assignment 3 java project
https://www.geeksforgeeks.org/for-each-loop-in-java/
https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html
https://stackoverflow.com/questions/16720059/creating-your-own-method
https://www.programiz.com/java-programming/methods
https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-print-table-format-printf-chart-console-scanner-println-line
https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/text/WordUtils.html#wrap(java.lang.String
https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/text/WordUtils.html
http://www.cburch.com/books/java/ch12-methods/index.html
Pluralsight.com
Starting out with Java: Control Structures through Objects 8th edition
===================================================== */
package dtcc.itn261;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BasketballScoreKeeper {
    // Scanner declared as static, so it can be used anywhere in this class
    // without having an instance of the class.
    private static final Scanner keyboard = new Scanner(System.in);

    /*
    method: main()
    @parameters String[] args
    intended use:main entry point of the program using a for loop to enter
    scores into each array per quarter until the loop is finished
    then displays the final results and prints who won the game
    */
    public static void main(String[] args) {

        // The arrays to hold the totals for the 4 quarters, one for each team.
        int[] team1 = new int[4];
        int[] team2 = new int[4];

        /* Notice in the following code that, using a for loop, the qtr variable is
        incremented from 0 to 3.  That means that the enterScores() method is called 4 times,
        once for each quarter.  The two team arrays are passed to the method along with the current
        quarter - 1.  All the scores for the game are entered in enterScores method. */

        for (int qtr = 0; qtr < 4; qtr++) {

            enterScores(team1, team2, qtr);

        }

        displayFinalResults(team1, team2);
        determineWinner(team1, team2);
        keyboard.close();

    }
    /*
    method: totalTeamScores()
    @parameters int[] team when called parameter used is the array for each team
    intended use: Using the passed team array, this method will add the points a team has scored during all four
    quarters of a game.
    */
    private static int totalTeamScores(int[] team) {

        int total = 0;

        for (int score : team) {

            total += score;

        }

        return total;
    }

    /*
    method: determineWinner()
    @parameters int[] team1, int[] team2
    intended use: to use the sum from totalTeamScores() to print a win, loss, tie message
    */
    private static void determineWinner(int[] team1, int[] team2) {

        int team1Total = totalTeamScores(team1);
        int team2Total = totalTeamScores(team2);

        if (team1Total > team2Total) {

            System.out.println("Team 1 has won the game!");

        } else if (team1Total < team2Total) {

            System.out.println("Team 2 has won the game!");

        } else {

            System.out.println("Both teams tied!");

        }
    }

    /*
    method: enterScores()
    @parameters int[] team1, int[] team2, int qtr
    @return teamNumber the entered team buy the user
    intended use: main logic for entering and collecting scores per quarter in each team's array by selecting either
    team and moving to the next element in the array when -1 is entered
    overview:
        - using a while loop, will continue collect input scores until -1 is selected to move to the
        next quarter (element)
        - first if statement will run selectTeam() method and store the value in the int teamNumber variable
        - if enter team is -1 then the program moves to the next quarter (element)
        - the second if statement will store input scores into either the team1 array or team 2 array
    */
    private static void enterScores(int[] team1, int[] team2, int qtr) {
        // This method will continue to prompt user which team scored, and how many points are scored (1, 2 or 3)
        System.out.println("###\tQuarter: " + (qtr + 1) + "\t###");
        System.out.println("(Enter -1 to end the quarter)\n");

        while (true) {

            int teamNumber = selectTeam(); // asks which team scored

            if (teamNumber == -1) {

                System.out.println("The quarter has ended!\n");
                break;

            }

            int points = getValidScore(teamNumber); // how many points did each team score

            if (teamNumber == 1) {

                team1[qtr] += points;

            } else {

                team2[qtr] += points;

            }

            System.out.println(); // for line spacing

        }
    }

    /*
    method: selectTeam()
    @parameters none
    @return teamNumber the entered team buy the user
    intended use: to continuously prompt the user for team 1, team 2 or -1 for quarter end
    and return that value as teamNumber
    overview:
        - using a while loop, will continue to prompt user for valid input if non-valid input is entered
        - using try...catch to resolve any exceptions that may arise from non number input
        - if statement returns valid entered points (-1, 1, or 2), prints error message logically if
        invalid number is entered or if any numbed that isn't -1, 1, or 2
    */
    private static int selectTeam() {
        while (true) {

            int teamNumber;

            System.out.print("Which team scored team 1 or team 2?: ");

            try {

                teamNumber = BasketballScoreKeeper.keyboard.nextInt();

                if (teamNumber == -1 || teamNumber == 1 || teamNumber == 2) {

                    return teamNumber;

                } else {

                    System.out.println("\nWrong team selected please enter (1 or 2)");

                }

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input: Please enter 1 or 2");
                BasketballScoreKeeper.keyboard.nextLine();
            }
        }
    }

    /*
    method: displayFinalResults
    @parameters int[] team1, int[] team2
    intended use: To display the total points scored per quarter and display the final total per team
    overview:
        - using a while loop, will continue to prompt user for valid input if non-valid input is entered
        - using try...catch to resolve any exceptions that may arise from non number input
        - if statement returns valid entered points (1,2, or 3), prints error message logically if invalid number is entered
    */
    private static void displayFinalResults(int[] team1, int[] team2) {
        // Header
        System.out.println("##################################");
        System.out.println("         Q1  Q2  Q3  Q4   Final");

        // Display scores for Team 1
        System.out.print("Team 1 :");

        for (int score : team1) {

            System.out.printf("%4d", score); // Each score is formatted to take up at least 4 spaces

        }
        System.out.printf("%8d\n", totalTeamScores(team1)); // Total is right-aligned with an 8-space padding

        // Display scores for Team 2
        System.out.print("Team 2 :");

        for (int score : team2) {

            System.out.printf("%4d", score);

        }
        System.out.printf("%8d\n", totalTeamScores(team2));

        // Footer
        System.out.println("##################################");
    }

    /*
    method: getValidScore
    @parameters int teamNumber == the pre-selected team
    @return the number of points scored
    intended use: collect user input for points scored for pre-selected team
    overview:
        - using a while loop, will continue to prompt user for valid input if non-valid input is entered
        - using try...catch to resolve any exceptions that may arise from non number input
        - if statement returns valid entered points (1,2, or 3), prints error message logically if invalid number is entered
    */
    private static int getValidScore(int teamNumber) {

        while (true) {

            System.out.print("How many points did team " + (teamNumber) + " score (1, 2, or 3)?: ");

            try {

                int points = BasketballScoreKeeper.keyboard.nextInt();

                if ((points >= 1 && points <= 3)) {

                    return points;

                } else {
                    System.out.println("\nInvalid input: Please enter 1,2 or 3 only");
                }

            } catch (InputMismatchException e) {

                System.out.println("\nInvalid input: Please enter 1,2 or 3 only");

                BasketballScoreKeeper.keyboard.nextLine();
            }
        }
    }
}
