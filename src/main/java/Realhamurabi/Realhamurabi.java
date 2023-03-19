package Realhamurabi;               // package declaration
import com.sun.jdi.CharValue;

import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Realhamurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int population = 100; // population
    int bushel = 2800; //currency same thing as grain
    int acreOwned = 1000; //every acre is 19 bushel
    int landValue = 19; // 19 bushels per acre
    int immigrants = 5;
    int ratata = 200;
    int year = 1;
    int harvest = 2000;
    int fed;
    int plagueLosses;
    int planted;
    int starvationLosses;
    boolean ratFlag;
    boolean plagueFlag;
    int efficiency = 3;

    public static void main(String[] args) { // required in every Java program
        new Realhamurabi().playGame();
    }
    /**
     * Prints the given message (which should ask the user for some integral
     * quantity), and returns the number entered by the user. If the user's
     * response isn't an integer, the question is repeated until the user
     * does give a integer response.
     *
     * @param message The request to present to the user.
     * @return The user's numeric response.
     */
    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        // statements go after the declations
        ratFlag = false;
        plagueFlag = false;

        while(year < 10) {
            System.out.println("O great Hammurabi!\n"
                    + "You are in year " + year + " of your ten year rule.\n"
                    + "In the previous year 0 people starved to death.\n"
                    + "In the previous year 5 people entered the kingdom.\n"
                    + "The population is now 100.\n" +
                    "We harvested 3000 bushels at 3 bushels per acre.\n"
                    + "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n"
                    + "The city owns 1000 acres of land.\n"
                    + "Land is currently worth 19 bushels per acre.");
            int acreOwnedBeforePurchase = acreOwned;
            acreOwned += askHowManyAcresToBuy(landValue, bushel);
            if (acreOwnedBeforePurchase == acreOwned) {
                acreOwned -= askHowManyAcresToSell(acreOwned);
            }
            fed = askHowMuchGrainToFeedPeople(bushel);
            bushel -= fed;
            planted = askHowManyAcresToPlant(acreOwned, population, bushel);
            bushel -= planted;
            plagueLosses = plagueDeaths(population);
            population -= plagueLosses;
            starvationLosses = starvationDeaths(population, fed);
            if (starvationLosses > 0 && uprising(population, starvationLosses)){
                gameOver();
                break;
            }
            population -= starvationLosses;
            if (starvationLosses == 0){
                immigrants = immigrants(population, acreOwned, bushel);
            } else {
                immigrants = 0;
            }
            population += immigrants;
            harvest = harvest(planted);
            ratata = grainEatenByRats(bushel);
            bushel -= ratata;
            landValue = newCostOfLand();
            bushel += harvest;
            printSummary(year, plagueLosses,starvationLosses, harvest, plagueFlag, ratFlag);
            year++;
        }
    printEnding(population, acreOwned, starvationLosses);
    }



    public int askHowManyAcresToBuy(int price, int bushels){
        int acresToBuy = 0;
        while (true) {
            int userInput = getNumber("How many acres do you wish to buy? ");
            int totalPrice = userInput;
            if (totalPrice * price > bushels) {
                System.out.println("Sorry, you don't have enough bushels to buy that many acres.");
            } else {
                acresToBuy += userInput;
                bushel -= totalPrice;
                break;
            }
        }
        return acresToBuy;
    }
    public int askHowManyAcresToSell(int acresOwned){
        while (true) {
            int userInput = getNumber("How many acres do you wish to sell? ");
            if (userInput > acresOwned) {
                System.out.println("Sorry, you don't own that many acres.");
            } else {
                return userInput;
            }
        }
    }
    int askHowMuchGrainToFeedPeople(int bushels){
        while (true) {
            int userInput = getNumber("How many bushels do you wish to feed your people? ");
            if (userInput > bushels) {
                System.out.println("Sorry, you don't own that many bushels.");
            } else {
                return userInput;
            }
        }
    }
    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        while (true) {
            int userInput = getNumber("How many acres do you want to plant with seed?");
            if (userInput > acresOwned) {
                System.out.println("Sorry, you don't own that many acres.");
            } else if (userInput * 10 > population) {
                System.out.println("Sorry, you don't have enough people to plant that many acres.");
            } else if (userInput > bushels / 2) {
                System.out.println("Sorry, you don't have enough bushels of grain to plant that many acres.");
            } else {
                return userInput;
            }
        }
    }
    public int plagueDeaths(int population){
        int deaths = 0;
        if (rand.nextInt(100) < 15) {
            deaths = population/2;
        }
        return deaths;
    }
    public int starvationDeaths(int population, int bushelsFedToPeople){
        int deaths = 0;
        if (bushelsFedToPeople < population * 20) {
            deaths = population - bushelsFedToPeople / 20;
        }
        return deaths;
    }
    public boolean uprising(int population, int howManyPeopleStarved){
        return (double) howManyPeopleStarved / population > .45;
    }

    public int immigrants(int population, int acresOwned, int bushel){
        int immigrants = (20 * acresOwned + bushel)/(100 * population) + 1;
        return immigrants;
    }
    public int harvest(int planted){
        efficiency = rand.nextInt(6) + 1;
        return ((planted+1)/2) * efficiency;
    }

    public int grainEatenByRats(int bushel){
        if (rand.nextInt(100) <= 39){
            return (int) (bushel * (double) (rand.nextInt(20) + 10)/100);
        }
        return 0;
    }

    public int newCostOfLand(){
        return rand.nextInt(7) + 17;
    }
    public void printSummary(int year, int plagueLosses, int starvationLosses, int harvest, boolean plagueFlag, boolean ratFlag){
        System.out.println("In year " + year);
        System.out.println((plagueLosses + starvationLosses) + " have died.");
        System.out.println(harvest + "bushels were harvested!");
        if (plagueFlag){
            System.out.println("Plague happened :(");
        }
        if (ratFlag){
            System.out.println("Rats came and ate your grain!");
        }
    }
    public void printEnding(int population, int acreOwned, int starvationLosses){
        System.out.println("You have completed the 10 years of your reign.");
        System.out.println(population + " have lived during your reign.");
        System.out.println("You had " + acreOwned + " acres of land.");
        System.out.println("You only let " + starvationLosses + " starve to death!");
        System.out.println("Good job king!");
    }
    private void gameOver() {
        System.out.println("You suck. You lost.");
    }

}