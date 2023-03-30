package student;

import graphics.MainView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        exampleStudentCheckpoint2();
    }

    public static void exampleStudentCheckpoint1() {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String[] instructions = line.split(" ");
        for(String instruction : instructions) {
            char first = instruction.charAt(0);
            if(first == 'L') left();
            if(first == 'R') right();
            if(first == 'F') {
                int dist = -1;
                if(instruction.length() == 1) dist = 1;
                else dist = Integer.parseInt(instruction.substring(1));
                for(int i=0; i<dist; i++) {
                    forward();
                }
            }
        }
    }

    public static void exampleStudentCheckpoint2() {
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String[] instructions = line.split(" ");
        for(String instruction : instructions) {
            char first = instruction.charAt(0);
            if(first == 'L') left();
            if(first == 'R') right();
            if(first == 'F') {
                int staminaUsed = 0;
                String instructionFirstPart = null;
                if(instruction.contains("_")) {
                    String[] instructionParts = instruction.split("_");
                    staminaUsed = Integer.parseInt(instructionParts[1]);
                    instructionFirstPart = instructionParts[0];
                }
                else instructionFirstPart = instruction;

                int dist = -1;
                if(instructionFirstPart.length() == 1) dist = 1;
                else dist = Integer.parseInt(instructionFirstPart.substring(1));

                for(int i=0; i<dist; i++) {
                    forward(staminaUsed);
                }
            }
        }
    }

    //[insert skull ascii art]
    //DO NOT MODIFY CODE BELOW THIS
    public static MainView mainView = new MainView();

    public static void forward() {
        forward(0);
    }
    public static void forward(int staminaUsed) {
        try {
            if(mainView.getGamePage() == null) {
                throw new RuntimeException("make sure level is set");
            }
            mainView.getGamePage().forward(staminaUsed);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void left() {
        try {
            if(mainView.getGamePage() == null) {
                throw new RuntimeException("make sure level is set");
            }
            mainView.getGamePage().left();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void right() {
        try {
            if(mainView.getGamePage() == null) {
                throw new RuntimeException("make sure level is set");
            }
            mainView.getGamePage().right();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

