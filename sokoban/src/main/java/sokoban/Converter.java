package sokoban;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Converter {
    public static void convert(String testCasePath, String outFilePath) {
        String problemName = testCasePath.substring(testCasePath.lastIndexOf("/") + 1, testCasePath.lastIndexOf("."));
        String domainName = "sokoban";

        StringBuilder sb = new StringBuilder();
        sb.append("(define (problem " + problemName + ")\n");
        sb.append("\t(:domain " + domainName + ")\n");

        sb.append("\t(:objects\n");

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(new FileReader(testCasePath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }
        String levelString = (String) jsonObject.get("testIn");
        String[] levelLines = levelString.split("\n");

        ArrayList<String> cells = new ArrayList<>();
        ArrayList<String> targetCells = new ArrayList<>();

        ArrayList<String> rules = new ArrayList<>();

        for (int i = 0; i < levelLines.length; i++) {
            String line = levelLines[i];
            for (int j = 0; j < line.length(); j++) {
                String cellName = "c" + i + "_" + j;
                cells.add(cellName);

                // Grid encoding
                if (j > 0) {
                    String rightCellName = "c" + i + "_" + (j - 1);
                    rules.add("(leftOf " + rightCellName + " " + cellName + ")");
                }
                if (i > 0) {
                    String upCellName = "c" + (i - 1) + "_" + j;
                    if (cells.contains(upCellName)) {
                        rules.add("(below " + cellName + " " + upCellName + ")");
                    }
                }

                char cell = line.charAt(j);
                if (cell == '#') { // Wall
                    // Nothing to do
                } else if (cell == '$') { // Box
                    rules.add("(isCrate " + cellName + ")");
                } else if (cell == '.') { // Target
                    targetCells.add(cellName);
                    rules.add("(isClear " + cellName + ")");
                } else if (cell == '*') { // Target with box
                    rules.add("(isCrate " + cellName + ")");
                    targetCells.add(cellName);
                } else if (cell == '@') { // Player
                    rules.add("(isPlayer " + cellName + ")");
                } else if (cell == '+') { // Player on target
                    rules.add("(isPlayer " + cellName + ")");
                    targetCells.add(cellName);
                } else if (cell == ' ') { // Empty space
                    rules.add("(isClear " + cellName + ")");
                }
            }
        }

        for (String cell : cells) {
            sb.append("\t\t" + cell + "\n");
        }

        sb.append("\t)\n");

        // Add rules to the problem
        sb.append("\t(:init\n");
        for (String rule : rules) {
            sb.append("\t\t" + rule + "\n");
        }
        sb.append("\t)\n\n");

        // Add goal to the problem
        sb.append("\t(:goal (and\n");
        for (String targetCell : targetCells) {
            sb.append("\t\t\t(isCrate " + targetCell + ")\n");
        }
        sb.append("\t\t)\n");
        sb.append("\t)\n");
        sb.append(")\n");

        // Write to file
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(outFilePath)) {
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("PDDL file generated: " + outFilePath);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide the test case path and output file path as arguments.");
            return;
        }

        String testCasePath = args[0];
        String outFilePath = args[1];

        convert(testCasePath, outFilePath);
        System.out.println("Conversion completed.");
    }
}
