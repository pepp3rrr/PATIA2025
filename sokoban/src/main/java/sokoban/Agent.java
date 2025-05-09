package sokoban;

import fr.uga.pddl4j.heuristics.state.StateHeuristic;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.InvalidConfigurationException;
import fr.uga.pddl4j.planners.LogLevel;
import fr.uga.pddl4j.planners.statespace.HSP;
import fr.uga.pddl4j.problem.operator.Action;

public class Agent {
    public static void main(String[] args) {
        String domainPath = "pddl/sokoban.pddl";
        String problemPath = "pddl/current.pddl";

        HSP planner = new HSP();
        // Sets the domain of the problem to solve
        planner.setDomain(domainPath);
        // Sets the problem to solve
        planner.setProblem(problemPath);
        // Sets the timeout of the search in seconds
        planner.setTimeout(1000);
        // Sets log level
        planner.setLogLevel(LogLevel.OFF);
        // Selects the heuristic to use
        planner.setHeuristic(StateHeuristic.Name.MAX);
        // Sets the weight of the heuristic
        planner.setHeuristicWeight(1.2);

        // Solve and print the result
        Plan plan;
        try {
            plan = planner.solve();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }

        for (Action action : plan.actions()) {
            String name = action.getName();
            if (name.equals("moveright") || name.equals("pushright")) {
                System.out.println("R");
            } else if (name.equals("moveleft") || name.equals("pushleft")) {
                System.out.println("L");
            } else if (name.equals("movedown") || name.equals("pushdown")) {
                System.out.println("D");
            } else if (name.equals("moveup") || name.equals("pushup")) {
                System.out.println("U");
            }
        }
    }
}
