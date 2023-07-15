import java.util.Random;         // imports go here
import java.util.Scanner;

public class Hammurabi {

    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int year = 1;
    int bushels = 2800;
    int acres = 1000;
    int people = 100;
    int landValue = 19;
    int starvationDeaths = 0;
    int plagueDeaths = 0;
    int harvested = 0;
    int immigrants = 0;
    int grainsEatenByRats = 0;
    int priceOfLand = 0;
    int acresSell = 0;
    int bushelsUsedAsSeed = (rand.nextInt(6) + 1);


    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {

        while(year<=10) { // loop game until it reaches the 10th year

            if(year==1) { // for the first year, set values for variables whose values will be calculated during game
                printSummary(year, 0, 5, people, bushels, 200, landValue,
                3000, bushelsUsedAsSeed, acres);
            } else { // remaining years will have updated values for the variables
                printSummary(year, starvationDeaths, immigrants, people, bushels, grainsEatenByRats, priceOfLand,
                        harvested, bushelsUsedAsSeed, acres);
            }


            // set variable to the return value of number of acres bought
            int acresBuy = askHowManyAcresToBuy(landValue, bushels);
            acres += acresBuy; // add to the current total acres
            if(year==1) {
                bushels -= (acresBuy * landValue); // on the first year, subtract the product of the acres bought and the initial set value per acre from bushels
            } else { bushels -= acresBuy * priceOfLand; } // remaining years, subtract from bushels the product of acres bought and the new value per acre


            if (acresBuy == 0) {
                acresSell = askHowManyAcresToSell(acres); //if user doesn't buy acres, run this method
                acres -= acresSell; // subtract acres sold from total acres
                if(year==1) { //on the first year, add the product of the acres sold and the initial set value per acre to bushels
                    bushels += (acresSell * landValue);
                } else { bushels += acresSell * priceOfLand;} // remaining years, add to bushels the product of acres bought and the new value per acre
            }

            // subtract the number of bushels to feed people from total bushels
            int grainToFeed = askHowMuchGrainToFeedPeople(bushels);
            bushels -= grainToFeed;

            // subtract the total number of bushels needed to plant from total bushels
            int acresToPlant = askHowManyAcresToPlant(acres, people, bushels);
            bushels -= acresToPlant * 2;


            plagueDeaths = plagueDeaths(people);
            if(plagueDeaths > 0) { // if there are deaths from plague, display message
                System.out.println("A horrible plague struck your land! " + plagueDeaths + " people have died.");
            }
            people -= plagueDeaths; // subtract deaths from plagues from total people


            starvationDeaths = starvationDeaths(people, grainToFeed);
            people -= starvationDeaths; // subtract deaths from starvation from total people


            boolean uprising = uprising(people, starvationDeaths);
            if(uprising) {break;}// if uprising is true, break out of game
            else { // if false, add the number of immigrants to total people
                immigrants = immigrants(people, acres, grainToFeed);
                people += immigrants;
            }


            harvested = harvest(acresToPlant, bushelsUsedAsSeed);
            bushels += harvested; // add harvested bushels to total bushels


            grainsEatenByRats = grainEatenByRats(bushels);
            bushels -= grainsEatenByRats; // subtract bushels eaten by rats from total bushels


            priceOfLand = newCostOfLand();

            year++; //increment year
        }


        if(year > 10) { //if year reaches 10; user won game
            System.out.println("Good Job protecting your land and people for 10 years!");
        } else { // else user lost
            System.out.println("DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
                    "BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
                    "ALSO BEEN DECLARED PERSONA NON GRATA!!");
        }

    }


    void  printSummary(int year, int starvationDeaths, int immigrants, int people, int bushels, int ratsDestroyed,
                        int priceOfLand, int harvest, int bushelsUsedAsSeeds, int totalAcresLand) {
        // print summary template
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
                scanner.next(); //consumes and discards non-integer input so that when it loops again, scanner.nextInt() will retrieve the next input
            }
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        System.out.print("How many acres would you like to buy? "); // ask user
        int acresToBuy = askNumber(); // store input to a variable after verifying input
        while(acresToBuy < 0 || acresToBuy > bushels/price) { //if input is less than 0 or is greater than the amount of acres user can buy
            System.out.println("O Great Hammurabi, surely you jest! We only have " + bushels + " bushels left.");
            acresToBuy = askNumber(); //update variable and run through loop again
        }
        return acresToBuy;

    }

    int askHowManyAcresToSell(int acresOwned) {
        System.out.print("How many acres would you like to sell? "); // ask user
        int acresToSell = askNumber(); // store input to a variable after verifying input
        // if input is less than 0 or if it is greater than total acre
        while(acresToSell < 0 || acresToSell > acresOwned) { //if input is less than 0 or is greater than the amount of acres user can sell
            System.out.println("O Great Hammurabi, surely you jest! We only have " + acresOwned + " acres of land.");
            acresToSell = askNumber(); //update variable and run through loop again
        }
        return acresToSell;

    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        System.out.print("How much grain would you like to feed your people? "); //ask user
        int grainToFeedPeople = askNumber(); //store input to a variable after verifying input
        // if input is less than 0 or if it is greater than the number of total bushels
        while(grainToFeedPeople < 0 || grainToFeedPeople > bushels) {
            System.out.println("O Great Hammurabi, surely you jest! We only have " + bushels + " bushels left.");
            grainToFeedPeople = askNumber(); //update variable and run through loop again
        }
        return grainToFeedPeople;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        System.out.print("How many acres would you like to plant with grain? "); //ask user
        int acresToPlant = askNumber(); //store input to a variable after verifying input
        // if input is greater than total acres, and the number of people needed to farm is greater than the population, and is greater than the amount of bushels needed to farm
        while(acresToPlant > acresOwned && acresToPlant/10 > population && acresToPlant > (bushels/2)) {
            System.out.println("O Great Hammurabi, surely you jest! We only have " + acresOwned + " acres of land, with " + population +
                    " people and " + bushels + " bushels left.");
            acresToPlant = askNumber(); // update variable and run through input loop again
        }
        return acresToPlant;
    }

    int plagueDeaths(int population) {
        int getPlagueDeaths = 0;
        if(rand.nextInt(100) < 15) { // 15% chance a plague will strike
            getPlagueDeaths = population / 2; // half population will die
        }
        return getPlagueDeaths;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        if(bushelsFedToPeople/20 < population) { // prevent negative deaths, if total bushels/20 (bushels needed for survival) is less than total population
            starvationDeaths = population - (bushelsFedToPeople / 20);
        }
        return starvationDeaths;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        if(howManyPeopleStarved > ((population*45)/100)) {
            return true; // return true if more than 45% of population is starving
        }
        return false;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        int incomingImmigrants = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
        return incomingImmigrants;
    }

    int harvest(int acres, int bushelsUsedAsSeed) {
        // return product of number of acres to plant and bushels used as seed (random int between 1-6)
        int harvestedBushels = (acres * bushelsUsedAsSeed);
        return harvestedBushels;
    }

    int grainEatenByRats(int bushels) {
        int grainsRatsAte = 0;
        if(rand.nextInt(100) < 40) { //there is a 40% chance of rat infestation
            // multiply total bushels by 10%-30% to return the amount of bushels eaten by rats
            grainsRatsAte = ((bushels * (rand.nextInt(21) + 10)) / 100);
        }
        return grainsRatsAte;
    }

    int newCostOfLand() {
        int priceOfLand = rand.nextInt(7) + 17; //update price per acre with a price range between 17-23 bushels/acre
        return priceOfLand;
    }
}

