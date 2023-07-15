import java.util.Random;         // imports go here
import java.util.Scanner;

public class Temp {

    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int year = 1;
    int bushels = 2800;
    int acres = 1000;
    int people = 100;
    int landValue = 19;
    int starvationDeaths = 0;
    int plagueDeaths = 0;
    int harvest = 0;
    int immigrants = 0;
    int grainsEatenByRats = 0;
    int priceOfLand = 0;
    int bushelsUsedAsSeed = rand.nextInt(7);


    public static void main(String[] args) { // required in every Java program
        new Temp().playGame();
    }

    void playGame() {

        while(year<=10) {

            if(year==1) {
                printSummary(year, 0, 5, people, bushels, 200, landValue,
                3000, bushelsUsedAsSeed, acres);
            } else {
                printSummary(year, starvationDeaths, immigrants, people, bushels, grainsEatenByRats, priceOfLand,
                        harvest, bushelsUsedAsSeed, acres);
            }

            // no need to initialize variable b/c we are setting the value as the return value from the method
            int acresBuy = askHowManyAcresToBuy(landValue, bushels);
            int acresSell = 0;
            if (acresBuy == 0) {
                acresSell = askHowManyAcresToSell(acres); //if user doesn't buy acres, run this method
            }
            int grainToFeed = askHowMuchGrainToFeedPeople(bushels);
            int acresToPlant = askHowManyAcresToPlant(acres, people, bushels);
            plagueDeaths = plagueDeaths(people);
            starvationDeaths = starvationDeaths(people, grainToFeed);

            boolean uprising = uprising(people, starvationDeaths);
            if(uprising) {break;}// if uprising is true, break out of game
            else {
                immigrants = immigrants(people, acres, grainToFeed);
                people += immigrants;
            }

            harvest = harvest(acres, acresToPlant);
            grainsEatenByRats = grainEatenByRats(bushels);
            priceOfLand = newCostOfLand();

            year++; //increment year
        }


        if(year<11) { //if year reaches 10; user won game
            System.out.println("Good Job protecting your land and people for 10 years!");
        } else { // else user lost
            System.out.println("DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
                    "BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
                    "ALSO BEEN DECLARED PERSONA NON GRATA!!");
        }

    }

    void  printSummary(int year, int starvationDeaths, int immigrants, int people, int bushels, int ratsDestroyed,
                        int priceOfLand, int harvest, int bushelsUsedAsSeeds, int totalAcresLand) {

        System.out.println("O great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year " + starvationDeaths + " people starved to death.\n" +
                "In the previous year " + immigrants + " people entered the kingdom.\n" +
                "The population is now " + people + ".\n" +
                "We harvested " + harvest + " bushels at " + bushelsUsedAsSeeds + " bushels per acre.\n" +
                "Rats destroyed " + ratsDestroyed + " bushels, leaving " + bushels + " bushels in storage.\n" +
                "The city owns " + totalAcresLand + " acres of land.\n" +
                "Land is currently worth " + priceOfLand + " bushels per acre.");
    }


    int askNumber() { // checks if user input is a valid input of integer
        while(true) {
            try { // check if input is valid integer
                return scanner.nextInt(); //return breaks loop
            } catch (Exception e) { // if its not valid, return error message and loop until user inputs valid integer
                System.out.println("Invalid Number.");
            }
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        System.out.println("How many acres would you like to buy? "); // ask user
        int acresToBuy = askNumber(); // store input to a variable after verifying input
        while(acresToBuy < 0 || acresToBuy > bushels/price) { //if input is less than 0 or is greater than the amount of acres user can buy
            System.out.println("O Great Hammurabi, surely you jest! We only have " + bushels + " bushels left.");
            acresToBuy = askNumber(); //update variable and run through loop again
        }
        return acresToBuy;

    }

    int askHowManyAcresToSell(int acresOwned) {
        System.out.println("How many acres would you like to sell? "); // ask user
        int acresToSell = askNumber(); // store input to a variable after verifying input
        while(acresToSell < 0 || acresToSell > acresOwned) { //if input is less than 0 or is greater than the amount of acres user can sell
            System.out.println("O Great Hammurabi, surely you jest! We only have" + acresOwned + " acres of land.");
            acresToSell = askNumber(); //update variable and run through loop again
        }
        return acresToSell;

    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        System.out.println("How much grain would you like to feed your people? "); //ask user
        int grainToFeedPeople = askNumber(); //store input to a variable after verifying input
        while(grainToFeedPeople < 0 || grainToFeedPeople > bushels) {
            System.out.println("O Great Hammurabi, surely you jest! We only have" + bushels + " bushels left.");
            grainToFeedPeople = askNumber(); //update variable and run through loop again
        }
        return grainToFeedPeople;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        System.out.println("How many acres would you like to plant with grain? "); //ask user
        int acresToPlant = askNumber(); //store input to a variable after verifying input
        while(acresToPlant > acresOwned && acresToPlant/10 > population && acresToPlant > (bushels/2)) {
            System.out.println("O Great Hammurabi, surely you jest! We only have" + acresOwned + " acres of land, with " + population +
                    " people and " + bushels + " bushels left.");
            acresToPlant = askNumber();
        }
        return acresToPlant;
    }

    int plagueDeaths(int population) {
        int getPlagueDeaths = 0;
        if(rand.nextInt(100) < 15) {
            getPlagueDeaths = rand.nextInt(population / 2);
        }
        return getPlagueDeaths;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        int starvationDeaths = population - (bushelsFedToPeople/20);
        return starvationDeaths;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        if(howManyPeopleStarved > ((population*45)/100)) {
            return true;
        }
        return false;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        int incomingImmigrants = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
        return incomingImmigrants;
    }

    int harvest(int acres, int bushelsUsedAsSeed) {
        int harvestedBushels = (acres * bushelsUsedAsSeed);
        return harvestedBushels;
    }

    int grainEatenByRats(int bushels) {
        int grainsRatsAte = 0;
        if(rand.nextInt(100) < 40) {
            grainsRatsAte = ((bushels * (rand.nextInt(21) + 10)) / 100);
        }
        return grainsRatsAte;
    }

    int newCostOfLand() {
        int priceOfLand = rand.nextInt(7) + 17;
        return priceOfLand;
    }
}

