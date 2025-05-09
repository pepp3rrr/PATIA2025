package sokoban;

import com.codingame.gameengine.runner.SoloGameRunner;

public class SokobanMain {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the test case path as an argument. (relative to the config folder)");
            return;
        }

        String testCasePath = args[0];

        Converter.convert("config/" + testCasePath, "pddl/current.pddl");

        SoloGameRunner gameRunner = new SoloGameRunner();
        gameRunner.setAgent(Agent.class);
        gameRunner.setTestCase(testCasePath);

        gameRunner.start();
    }
}
