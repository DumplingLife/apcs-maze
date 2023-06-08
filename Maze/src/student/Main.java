package student;

import java.util.*;

import graphics.MainView;

public class Main {
	private static String macro1, macro2, macro3, macro4;
	
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	String line = scan.nextLine();
    	while(!line.equals("quit")) { //CP2
    		processLine(line);
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
    // reset
    // set speed
    public static void processLineCP2(String line) {
    	if(line.equals("reset")) {
    		reset();
    	}
    	else if(line.startsWith("set speed")) {
    		int speed = Integer.parseInt(line.substring(10)); 
    		setSpeed(100*(11-speed));
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
            int dist = -1;
            if(inst.length() == 1) dist = 1;
            else dist = Integer.parseInt(inst.substring(1));

            for(int i=0; i<dist; i++) {
                forward();
            }
        }
    }
    
    // F2_50
    // instructions
    public static void processLineCP3(String line) {
    	if(line.equals("reset")) {
    		reset();
    	}
    	else if(line.startsWith("set speed")) {
    		int speed = Integer.parseInt(line.substring(10));
    		setSpeed(100*(11-speed));
    	}
    	else if(line.equals("instructions")) {
    		System.out.println("blah blah");
    	}
    	else {
    		while(line.contains(" ")) {
        		String inst = line.substring(0, line.indexOf(" "));
        		line = line.substring(line.indexOf(" ") + 1);
        		processInstructionCP3(inst);
            }
    		processInstructionCP3(line);
    	}
    }
    public static void processInstructionCP3(String inst) {
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

    // M1 = F2 L
    // F!
    public static void processLine(String line) {
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
    		setSpeed(100*(11-speed));
    	}
    	else if(line.equals("instructions")) {
    		System.out.println("blah blah");
    	}
    	else if(line.contains("=")) {
    		int macroId = Integer.parseInt(line.substring(1,2));
    		String macro = line.substring(5);
    		if(macroId == 1) macro1 = macro;
    		if(macroId == 2) macro2 = macro;
    		if(macroId == 3) macro3 = macro;
    		if(macroId == 4) macro4 = macro;
    	}
    	else if(line.startsWith("scheme"))
        {
            int scheme = Integer.parseInt(line.substring(7));
            setScheme(scheme);
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
        		while(!getNextCell().equals("boulder") && !getNextCell().equals("wall")) {
        			forward();
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

    //[insert skull ascii art]
    //DO NOT MODIFY CODE BELOW THIS
    //if test was ran, api will not be null
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
    public static void reset() {
    	mainView.setGamePage();
    }
    public static void setSpeed(int delay) {
    	if(mainView.getGamePage() == null) {
            throw new RuntimeException("make sure level is set");
        }
    	mainView.getGamePage().speed = delay;
    }
    public static void quit() {
    	mainView.quit();
    }
    public static String getNextCell() {
    	if(mainView.getGamePage() == null) {
            throw new RuntimeException("make sure level is set");
        }
    	return mainView.getGamePage().getNextCell();
    }
    public static void setScheme(int scheme) {
        try {
            if(mainView.getGamePage() == null) {
                throw new RuntimeException("make sure level is set");
            }
            mainView.getGamePage().setScheme(scheme);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mainView.getGamePage().paint();
    }
}

