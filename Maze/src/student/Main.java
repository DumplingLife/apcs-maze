package student;

import graphics.MainView;

import java.util.*;

public class Main {
    public static void main(String[] args) {
    	exampleStudentFinal();
    }
    
    // L/R/F
    // single line
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
    
    // F2
    // F2_50
    // multi-line
    // reset
    // set speed
    public static void exampleStudentCheckpoint2() {
        Scanner scan = new Scanner(System.in);
        while(true) {
        	String line = scan.nextLine();
        	if(line.equals("reset")) {
        		reset();
        	}
        	else if(line.matches("^set speed \\d+$")) {
        		int speed = Integer.parseInt(line.substring(10));
        		setSpeed(speed);
        	}
        	else {
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
        }
    }
    
    // F!
    // M1 = F2 L
    // reset also resets macros
    // random
    public static void exampleStudentFinal() {
        Scanner scan = new Scanner(System.in);
        String[] macros = new String[10]; //macros go from M1 to M9
        Arrays.fill(macros, "");
        while(true) {
        	String line = scan.nextLine();
        	if(line.equals("reset")) {
        		macros = new String[10];
        		Arrays.fill(macros, "");
        		reset();
        	}
        	else if(line.matches("^set speed \\d+$")) {
        		int speed = Integer.parseInt(line.substring(10));
        		setSpeed(speed);
        	}
        	else if(line.matches("^M.*=.*")) {
        		int macroId = Integer.parseInt(line.substring(1,2));
        		macros[macroId] = line.substring(5);
        	}
        	else if(line.equals("random")) {
        		//50 is not the cap; should be large enough to finish but not infinite
        		for(int i=0; i<50; i++) {
            		double r = Math.random();
            		if(r < 0.1) left();
            		else if(r < 0.2) right();
            		else forward();
            	}
        	}
        	else {
        		for(int macroId=1; macroId<=9; macroId++) {
        			line = line.replaceAll("M" + macroId, macros[macroId]);
        		}
        		String[] instructions = line.split(" ");
                for(String instruction : instructions) {
                    char first = instruction.charAt(0);
                    if(first == 'L') left();
                    if(first == 'R') right();
                    if(first == 'F') {
                    	if(instruction.equals("F!")) {
                    		String nextCell = "";
                    		while(!nextCell.equals("boulder") && !nextCell.equals("wall")) {
                    			nextCell = forward();
                    		}
                    	}
                    	else {
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
        	}
        }
    }

    //[insert skull ascii art]
    //DO NOT MODIFY CODE BELOW THIS
    public static MainView mainView = new MainView();

    public static String forward() {
        return forward(0);
    }
    public static String forward(int staminaUsed) {
        try {
            if(mainView.getGamePage() == null) {
                throw new RuntimeException("make sure level is set");
            }
            return mainView.getGamePage().forward(staminaUsed);
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
    public static void reset() {
    	mainView.resetGamePage();
    }
    public static void setSpeed(int speed) {
    	mainView.getGamePage().speed = speed;
    }
}

