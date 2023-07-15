import java.util.Random;         // imports go here
import java.util.Scanner;

public class Temp {

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
    int totalAcresLand = 0;



    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) { // required in every Java program
        new Temp().playGame();
    }

    void playGame() {

        while(year<=10) {
            printSummary(year, starvationDeaths, immigrants, people, bushels, grainsEatenByRats, landValue,
                    harvest, totalAcresLand);

            int acresBuy = askHowManyAcresToBuy(landValue, bushels); // no need to initialize variable b/c we are setting the value as the return value from askHowManyAcresToBuy method
            int acresSell = 0;
            if (acresBuy == 0) {
                acresSell = askHowManyAcresToSell(acres);
            }
            int grainToFeed = askHowMuchGrainToFeedPeople(bushels);
            int acresToPlant = askHowManyAcresToPlant(acres, people, bushels);
            plagueDeaths = plagueDeaths(people);
            starvationDeaths = starvationDeaths(people, grainToFeed);

            boolean uprising = uprising(people, starvationDeaths);
            if(uprising) {break;}

            immigrants = immigrants(people, acres, grainToFeed);
            harvest = harvest(acres, acresToPlant);
            grainsEatenByRats = grainEatenByRats(bushels);
            int newCostOfLand = newCostOfLand();

            year++;
        }


        if(year<11) {
            System.out.println("Good Job protecting your land and people for 10 years!");
        } else {
            System.out.println("DUE TO THIS EXTREME MISMANAGEMENT YOU HAVE NOT ONLY\n" +
                    "BEEN IMPEACHED AND THROWN OUT OF OFFICE BUT YOU HAVE\n" +
                    "ALSO BEEN DECLARED PERSONA NON GRATA!!");
        }

    }

    String printSummary(int year, int starvationDeaths, int immigrants, int people, int bushels, int ratsDestroyed,
                        int priceOfLand, int harvest, int totalAcresLand) {
        System.out.println("O great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year " + starvationDeaths + " people starved to death.\n" +
                "In the previous year " + immigrants + " people entered the kingdom.\n" +
                "The population is now " + people + ".\n" +
                "We harvested " + harvest + " bushels at 3 bushels per acre.\n" +
                "Rats destroyed " + ratsDestroyed + " bushels, leaving " + bushels + " bushels in storage.\n" +
                "The city owns " + totalAcresLand + " acres of land.\n" +
                "Land is currently worth " + priceOfLand + " bushels per acre.");
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        return 0;

    }

    int askHowManyAcresToSell(int acresOwned) {
        return 0;

    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        return 0;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        return 0;
    }

    int plagueDeaths(int population) {
        return 0;
    }

    int starvationDeaths(int population, int bushelsFedToPeople) {
        return 0;
    }

    boolean uprising(int population, int howManyPeopleStarved) {
        return true;
    }

    int immigrants(int population, int acresOwned, int grainInStorage) {
        return 0;
    }

    int harvest(int acres, int bushelsUsedAsSeed) {
        return 0;
    }

    int grainEatenByRats(int bushels) {
        return 0;
    }

    int newCostOfLand() {
        return 0;
    }
}

