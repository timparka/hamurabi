package Realhamurabi;               // package declaration
import java.util.InputMismatchException;
import java.util.Random;         // imports go here
import java.util.Scanner;

public class Realhamurabi {         // must save in a file named Hammurabi.java
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int people = 100;
    int grain = 2800;
    int acre = 1000;
    int land = 19;
    int populationDeath = 0;
    int immigrants = 5;
    int ratata = 200;
    int year = 1;
    int harvest = 2000;
    int efficiency = 3;
    boolean bought;
    int fed;
    int plagueLosses;
    int starvationLosses;
    int planted;

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

        while(year < 10) {
            System.out.println("O great Hammurabi!\n"
                    + "You are in year" + year + "of your ten year rule.\n"
                    + "In the previous year 0 people starved to death.\n"
                    + "In the previous year 5 people entered the kingdom.\n"
                    + "The population is now 100.\n" +
                    "We harvested 3000 bushels at 3 bushels per acre.\n"
                    + "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n"
                    + "The city owns 1000 acres of land.\n"
                    + "Land is currently worth 19 bushels per acre.");
            askHowManyAcresToBuy(land, grain);
            year++;
        }

    }
    public int askHowManyAcresToBuy(int price, int bushels){
        int acresToBuy = 0;
        while (true) {
            int userInput = getNumber("How many acres do you wish to buy? ");
            int totalPrice = userInput * price;
            if (totalPrice > bushels) {
                System.out.println("Sorry, you don't have enough bushels to buy that many acres.");
            } else {
                acresToBuy += userInput;
                bushels -= totalPrice;
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
            } else if (userInput * 2 > population) {
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
            deaths = (int) (population * (rand.nextDouble() * 0.25 + 0.05));
            System.out.println(deaths + " people died of the plague.");
        }
        return deaths;
    }
    public int starvationDeaths(int population, int bushelsFedToPeople){
        int deaths = 0;
        if (bushelsFedToPeople < population * 20) {
            deaths = population - bushelsFedToPeople / 20;
            System.out.println(deaths + " people starved to death.");
        }
        return deaths;
    }
    public boolean uprising(int population, int howManyPeopleStarved){
        int odds = 0;
        if (howManyPeopleStarved > 0) {
            odds = howManyPeopleStarved * 100 / population + 1;
        }
        return rand.nextInt(100) < odds;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage){
        int immigrants = 0;
        if (population > 100) {
            int foodPerPerson = grainInStorage / population;
            int landPerPerson = acresOwned / population;
            immigrants = rand.nextInt(20) + 1;
            if (foodPerPerson >= 20 && landPerPerson >= 7) {
                int people = immigrants;
                System.out.println(immigrants + " people entered the kingdom.");
            }
        }
        return immigrants;
    }
//    public int harvest(int acres){
//        int harvest = acres * (rand.nextInt(4) + 1) + bushelsUsedAsSeed;
//        System.out.println("You harvested " + harvest + " bushels.");
//        return harvest;
//    }

    public int grainEatenByRats(int bushels){
        int eatenByRats = rand.nextInt(3) * bushels / 100;
        System.out.println(eatenByRats + " bushels were eaten by rats.");
        return eatenByRats;
    }

    public int newCostOfLand(){
        int newCost = rand.nextInt(6) + 17;
        System.out.println("Land is now worth " + newCost + " bushels per acre.");
        return newCost;
    }

    String printSummary(int year, int starved, int immigrants, int harvested, int eatenByRats, int bought, int sold, int acresOwned, int bushels, int population, int acresPlanted, int grainFed, boolean uprising){
        String summary = "In year " + year + ", " + starved + " people starved to death, "
                + immigrants + " people entered the kingdom, " + harvested + " bushels were harvested, "
                + eatenByRats + " bushels were eaten by rats, " + bought + " acres were bought, "
                + sold + " acres were sold, leaving " + acresOwned + " acres owned.\n"
                + "The city has " + bushels + " bushels in storage and a population of "
                + population + " with " + acresPlanted + " acres planted.\n"
                + grainFed + " bushels were used to feed the people.\n";
        if (uprising) {
            summary += "There was an uprising in the city!\n";
        }
        return summary;
    }


}