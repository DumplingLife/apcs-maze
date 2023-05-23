package student;

import graphics.MainView;

import java.util.*;

public class Main {
	private static String macro1, macro2, macro3, macro4;
	
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	String line = scan.nextLine();
    	while(!line.equals("quit")) { //CP2
    		processLineFinal(line);
    		line = scan.nextLine();
    	}
    	quit();
    }
    
    // L/R/F
    public static void processLineCP1(String line) {
    	while(line.contains(" ")) {
    		String inst = line.substring(0, line.indexOf(" "));
    		line = line.substring(line.indexOf(" ") + 1);
    		processInstructionCP1(inst);
        }
    	processInstructionCP1(line);
    }
    public static void processInstructionCP1(String inst) {
        if(inst.equals("L")) {
        	left();
        }
        else if(inst.equals("R")) {
        	right();
        }
        else if(inst.equals("F")) {
            forward();
        }
    }
    
    // F2
    // F2_50
    // reset
    // set speed
    public static void processLineCP2(String line) {
    	if(line.equals("reset")) {
    		reset();
    	}
    	else if(line.startsWith("set speed")) {
    		int speed = Integer.parseInt(line.substring(10));
    		setSpeed(speed);
    	}
    	else {
    		while(line.contains(" ")) {
        		String inst = line.substring(0, line.indexOf(" "));
        		line = line.substring(line.indexOf(" ") + 1);
        		processInstructionCP2(inst);
            }
        	processInstructionCP2(line);
    	}
    }
    public static void processInstructionCP2(String inst) {
    	if(inst.equals("L")) {
        	left();
        }
        else if(inst.equals("R")) {
        	right();
        }
        else {
        	int staminaUsed = 0;
            String instructionFirstPart;
            if(inst.contains("_")) {
            	instructionFirstPart = inst.substring(0, inst.indexOf("_"));
                staminaUsed = Integer.parseInt(inst.substring(inst.indexOf("_") + 1));
            }
            else {
            	instructionFirstPart = inst;
            }

            int dist = -1;
            if(instructionFirstPart.length() == 1) dist = 1;
            else dist = Integer.parseInt(instructionFirstPart.substring(1));

            for(int i=0; i<dist; i++) {
                forward(staminaUsed);
            }
        }
    }
    
    // F!
    // M1 = F2 L
    // random
    public static void processLineFinal(String line) {
    	if(line.equals("reset")) {
    		//this isn't actually needed, because in the future, macros will be set before used
    		macro1 = null;
    		macro2 = null;
    		macro3 = null;
    		macro4 = null;
    		
    		reset();
    	}
    	else if(line.startsWith("set speed")) {
    		int speed = Integer.parseInt(line.substring(10));
    		setSpeed(speed);
    	}
    	else if(line.contains("=")) {
    		int macroId = Integer.parseInt(line.substring(1,2));
    		String macro = line.substring(5);
    		if(macroId == 1) macro1 = macro;
    		if(macroId == 2) macro2 = macro;
    		if(macroId == 3) macro3 = macro;
    		if(macroId == 4) macro4 = macro;
    	}
    	else {
    		line = line.replaceAll("M1", macro1);
    		line = line.replaceAll("M2", macro2);
    		line = line.replaceAll("M3", macro3);
    		line = line.replaceAll("M4", macro4);
    		while(line.contains(" ")) {
        		String inst = line.substring(0, line.indexOf(" "));
        		line = line.substring(line.indexOf(" ") + 1);
        		processInstructionFinal(inst);
            }
    		processInstructionFinal(line);
    	}
    }
    public static void processInstructionFinal(String inst) {
    	if(inst.equals("L")) {
        	left();
        }
        else if(inst.equals("R")) {
        	right();
        }
        else {
        	if(inst.equals("F!")) {
        		String nextCell = "";
        		while(!nextCell.equals("boulder") && !nextCell.equals("wall")) {
        			nextCell = forward();
        		}
        	}
        	else {
        		int staminaUsed = 0;
                String instructionFirstPart;
                if(inst.contains("_")) {
                	instructionFirstPart = inst.substring(0, inst.indexOf("_"));
                    staminaUsed = Integer.parseInt(inst.substring(inst.indexOf("_") + 1));
                }
                else {
                	instructionFirstPart = inst;
                }

                int dist = -1;
                if(instructionFirstPart.length() == 1) dist = 1;
                else dist = Integer.parseInt(instructionFirstPart.substring(1));

                for(int i=0; i<dist; i++) {
                    forward(staminaUsed);
                }
        	}
        }
    }
    
    // ==================================================================
    // OLD
    // ==================================================================
    // F!
    // M1 = F2 L
    // reset also resets macros
    // random
    public static void oldExampleStudentFinal() {
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
                    			System.out.println(nextCell);
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
    // speed is 1-10, corresponding to 1000 - 100ms (reverse order)
    public static void setSpeed(int speed) {
    	mainView.getGamePage().speed = (11-speed) * 100;
    }
    public static void quit() {
    	mainView.quit();
    }
}

