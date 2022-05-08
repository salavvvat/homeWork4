package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 1500;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 180, 450, 100, 220, 150};
    public static int[] heroesDamage = {25, 20, 15, 0, 5, 12, 20, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        round_number++;
        chooseBossDefence();
        bossHitsHeroes();
        thor();
        lucky();
        golem();
        berserk();
        heroesHit();
        medicHeals();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefence);

    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND _____________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }


    public static void bossHitsHeroes() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] <= 0) {
                continue;
            }
            if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0;
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth <= 0 || heroesHealth[i] <= 0) {
                continue;
            }
            if (heroesAttackType[i] == bossDefence) {
                Random random = new Random();
                int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                int criticalDamage = heroesDamage[i] * coeff;
                if (bossHealth - criticalDamage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - criticalDamage;
                }
                System.out.println("Critical damage: " + criticalDamage);
            } else {
                if (bossHealth - heroesDamage[i] < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }

    public static void medicHeals() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }
            if (heroesHealth[i] >= 0 && heroesHealth[i] <= 100 && heroesHealth[3] > 0) {
                heroesHealth[i] += 25;
                System.out.println("Medic health: " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 4) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[4] > 0) {
                heroesHealth[4] -= bossDamage / 5;
                heroesHealth[i] += bossDamage / 5;
            }
        }
        System.out.println("Golem take 1/5 damage");
    }

    public static void lucky() {
        Random random = new Random();
        boolean uklon = random.nextBoolean();
        if (uklon == true){
            heroesHealth[5] += bossDamage - 10;
            System.out.println("lucky evaded");
        }
    }

    public static void berserk() {
        if (heroesHealth[6] > 0) {
            heroesHealth[6] += bossDamage/2;
            bossHealth -= bossDamage/2;
            System.out.println("berserk super damage");
        }
    }
    public static void thor() {
        Random random = new Random();
        boolean stan = random.nextBoolean();
        if (heroesHealth[7] > 0 && stan == true){
            bossDamage -= 50;
            System.out.println("Boss stunned");
        }else {
            bossDamage = 50;
        }
    }
}