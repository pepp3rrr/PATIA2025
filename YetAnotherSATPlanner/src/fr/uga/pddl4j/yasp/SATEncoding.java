package fr.uga.pddl4j.yasp;

import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.problem.Fluent;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.util.BitVector;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements a planning problem/domain encoding into DIMACS
 *
 * @author H. Fiorino
 * @version 0.1 - 30.03.2024
 */
public final class SATEncoding {
    /*
     * A SAT problem in dimacs format is a list of int list a.k.a clauses
     */

    /*
     * Initial state
     */
    private List<Integer> initList;
    /*
     * Goal
     */
    private List<Integer> goalList;

    /*
     * Actions related info
     */

    // all action codes
    private List<Integer> actionList;
    // maps to encode action implications by replacing (->) with a distributed OR
    private HashMap<Integer, List<Integer>>  Action2Precon; // action and its preconditions
    private HashMap<Integer, List<Integer>>  Action2PosEff; // action and its positive effects
    private HashMap<Integer, List<Integer>>  Action2NegEff; // action and its negative effects
    // maps to encode state transitions
    private HashMap<Integer, List<Integer>>  PosEff2Action; // positive effect belonging to multiple actions
    private HashMap<Integer, List<Integer>>  NegEff2Action; // negative effect belonging to multiple actions
    /*
     * Current formula excluding goal
     */
    public List<List<Integer>> currentDimacs;

    /*
    *  Total number of fluents of the problem
    */
    private int nb_fluents = 0;
    /*
     * Current step of dimacs
     */
    private int currentStep = 1;

    public SATEncoding(Problem problem, int steps) {
       
        // initialize all ingredients to use them in encode()
        if(currentStep == 1){
            // initialize all structures
            initList = new ArrayList<Integer>();
            goalList = new ArrayList<Integer>();
            currentDimacs = new ArrayList<List<Integer>>();
            Action2Precon = new HashMap<Integer, List<Integer>>();
            Action2PosEff = new HashMap<Integer, List<Integer>>();
            Action2NegEff = new HashMap<Integer, List<Integer>>();
            PosEff2Action = new HashMap<Integer, List<Integer>>();
            NegEff2Action = new HashMap<Integer, List<Integer>>();
            actionList = new ArrayList<>();

            // Encoding of init
            // Each fact is a unit clause
            // Init state step is 1
            // We get the initial state from the planning problem
            // State is a bit vector where the ith bit at 1 corresponds to the ith fluent being true

            // get initial state
            nb_fluents = problem.getFluents().size();
            final BitVector init = problem.getInitialState().getPositiveFluents();

            // Encoding init:
            for(int i = 0; i < nb_fluents; i++){
                if(init.get(i) == true){
                    initList.add(pair(i + 1, 1)); // indexes start with 1

                }
                else{
                    initList.add(-1 * pair(i + 1, 1)); // indexes start with 1
                }
            }
            // add init to final formula
            currentDimacs.add(initList);

            // offset for actions
            int offset = nb_fluents + 1;


            final List<Action> actions = problem.getActions();
            // go through actions to fill maps
            for (int j = 0; j < actions.size(); j++) {
                // associate an integer code to an action
                Integer actionCode = (offset++);
                actionList.add(actionCode);

                Action act = actions.get(j);
                // get all the preconditions, negative and positive effects of an action Aj
                BitVector precondition = act.getPrecondition().getPositiveFluents();
                BitVector positiveEffects = act.getUnconditionalEffect().getPositiveFluents();
                BitVector negativeEffects = act.getUnconditionalEffect().getNegativeFluents();

                // list of preconditions codes associated to an action Aj
                List<Integer> precons = new ArrayList<>();
                // list of pos effects codes associated to an action Aj
                List<Integer> posEffs = new ArrayList<>();
                // list of neg effects codes associated to an action Aj
                List<Integer> negEffs = new ArrayList<>();

                // fill preconditions
                for (int k = 0; k < nb_fluents; k++) {
                    // if action has the precondition:
                    if (precondition.get(k) == true) {
                        int index = k + 1;
                        precons.add(index);
                    }
                }

                // fill posEffs
                for (int k = 0; k < nb_fluents; k++) {
                    // if action has the effect:
                    if (positiveEffects.get(k) == true) {
                        int index = k + 1;
                        posEffs.add(index);

                        // add action to a map of positive effects
                        if(PosEff2Action.containsKey(index)){
                            PosEff2Action.get(index).add(actionCode);
                        }
                        else{
                            // else create the key and add action to it
                            List<Integer> actionDisjunction = new ArrayList<>();
                            actionDisjunction.add(actionCode); 
                            PosEff2Action.put(index, actionDisjunction); 
                        }
                    }
                }
                // fill negEffs
                for (int k = 0; k < nb_fluents; k++) {
                    // if action has the effect:
                    if (negativeEffects.get(k)) {
                        int index = k+1;
                        negEffs.add(index);

                        // add action to a map of negative effects
                        if(NegEff2Action.containsKey(index)){
                            NegEff2Action.get(index).add(actionCode);
                        }
                        // else create the key and add action to it
                        else{
                            List<Integer> actionDisjunction = new ArrayList<>();
                            actionDisjunction.add(actionCode);
                            NegEff2Action.put(index, actionDisjunction); 
                        }
                    }
                }
                // fill action maps
                Action2Precon.put(actionCode, precons);
                Action2NegEff.put(actionCode, negEffs);
                Action2PosEff.put(actionCode, posEffs);
            }
        }
        // run encode to encode every iteration between current step and total steps
        encode(problem, steps);
        // update current state
        currentStep = steps;
    }

    /*
     * Private function to encode everything at each step 
     * between current and steps without redundunt recreation of clauses
     */
    private void encode(Problem problem, int steps){
        final List<Action> actions = problem.getActions();

        // create dimacs at a step starting from current step by adding clauses between steps
        for(int i = currentStep; i < steps; i++){
            // encode action implications and action mutex
            for(int j = 0; j < actions.size(); j++){
                int actionCode = actionList.get(j);
                // make a mutual exclusion over other actions
                for(int mutexAction : actionList){
                    // no mutual exclusion with the action itself
                    if (mutexAction != actionCode){
                        List <Integer> mutexClause = new ArrayList<>();
                        mutexClause.add(-1 * pair(actionCode, i));
                        mutexClause.add(-1 * pair(mutexAction, i));
                        // add it to dimacs
                        currentDimacs.add(mutexClause);
                    }
                }
                /* 
                    code action A implication by distributing negation of A
                    over preconditions and effects of the action
                */
                // distribute not action over its preconditions
                for(int precondition: Action2Precon.get(actionCode)){
                    List<Integer> clause = new ArrayList<>();
                    clause.add(-1 * pair(actionCode, i));
                    clause.add(pair(precondition, i));

                    // add it to final formula
                    currentDimacs.add(clause);
                }

                // distribute not action over its pos effects
                for(int posEffect: Action2PosEff.get(actionCode)){
                    List<Integer> clause = new ArrayList<>();
                    // effects are applied to the next step
                    clause.add(-1 * pair(actionCode, i));
                    clause.add(pair(posEffect, i+1));

                    // add it to final formula
                    currentDimacs.add(clause);
                }
                // distribute not action over its negative effects
                for(int negEffect: Action2NegEff.get(actionCode)){
                    List<Integer> clause = new ArrayList<>();
                    // effects are applied to the next step
                    clause.add(-1 * pair(actionCode, i));
                    clause.add(-1 * pair(negEffect, i+1));

                    // add it to final formula
                    currentDimacs.add(clause);
                }
            }

            /* encode state transitions */ 
            // encode positive effects
            for(int posEffect : PosEff2Action.keySet()){
                List<Integer> effectDisjunction = new ArrayList<>();
                // add not effect at current step + effect at a previous one
                effectDisjunction.add(-1 * pair(posEffect, i+1));
                effectDisjunction.add(pair(posEffect, i));
                // add all related actions to the effect
                for(int action : PosEff2Action.get(posEffect)){
                    int encodedAction = pair(action, i);
                    effectDisjunction.add(encodedAction);
                }
                // put it to final formula
                currentDimacs.add(effectDisjunction);
            }
            // encode negative effects
            for(int negEffect : NegEff2Action.keySet()){
                List<Integer> effectDisjunction = new ArrayList<>();
                // add effect at current step + neg effect at a previous one
                effectDisjunction.add(-1 * pair(negEffect, i+1));
                effectDisjunction.add(pair(negEffect, i));
                // add all related actions to the effect
                for(int action : NegEff2Action.get(negEffect)){
                    int encodedAction = pair(action, i);
                    effectDisjunction.add(encodedAction);
                }
                // put it to final formula
                currentDimacs.add(effectDisjunction);
            }
        }

        // code goal at a step steps
        final BitVector goal = problem.getGoal().getPositiveFluents();
        for(int i = 0; i < nb_fluents; i++){
            if(goal.get(i) == true){
                goalList.add(pair(i + 1, steps)); // indexes start with 1
            }
            else{
                goalList.add (-1 * pair(i + 1, steps)); // indexes start with 1
            }  
        }
    }

    public String toString(final List<Integer> clause, final Problem problem) {
        final int nb_fluents = problem.getFluents().size();
        List<Integer> dejavu = new ArrayList<Integer>();
        String t = "[";
        String u = "";
        int tmp = 1;
        int [] couple;
        int bitnum;
        int step;
        for (Integer x : clause) {
            if (x > 0) {
                couple = unpair(x);
                bitnum = couple[0];
                step = couple[1];
            } else {
                couple = unpair(- x);
                bitnum = - couple[0];
                step = couple[1];
            }
            t = t + "(" + bitnum + ", " + step + ")";
            t = (tmp == clause.size()) ? t + "]\n" : t + " + ";
            tmp++;
            final int b = Math.abs(bitnum);
            if (!dejavu.contains(b)) {
                dejavu.add(b);
                u = u + b + " >> ";
                if (nb_fluents >= b) {
                    Fluent fluent = problem.getFluents().get(b - 1);
                    u = u + problem.toString(fluent)  + "\n";
                } else {
                    u = u + problem.toShortString(problem.getActions().get(b - nb_fluents - 1)) + "\n";
                }
            }
        }
        return t + u;
    }

    public Plan extractPlan(final List<Integer> solution, final Problem problem) {
        Plan plan = new SequentialPlan();
        HashMap<Integer, Action> sequence = new HashMap<Integer, Action>();
        final int nb_fluents = problem.getFluents().size();
        int[] couple;
        int bitnum;
        int step;
        for (Integer x : solution) {
            if (x > 0) {
                couple = unpair(x);
                bitnum = couple[0];
            } else {
                couple = unpair(-x);
                bitnum = -couple[0];
            }
            step = couple[1];
            // This is a positive (asserted) action
            // fixed an error of incorrect 
            if (Math.abs(bitnum) > nb_fluents) {
                final Action action = problem.getActions().get(Math.abs(bitnum) - nb_fluents - 1);
                sequence.put(step, action);
            }
        }
        for (int s = sequence.keySet().size(); s > 0 ; s--) {
            plan.add(0, sequence.get(s));
        }
        return plan;
    }
    
    // Cantor paring function generates unique numbers
    private static int pair(int num, int step) {
        return (int) (0.5 * (num + step) * (num + step + 1) + step);
    }

    private static int[] unpair(int z) {
        /*
        Cantor unpair function is the reverse of the pairing function. It takes a single input
        and returns the two corespoding values.
        */
        int t = (int) (Math.floor((Math.sqrt(8 * z + 1) - 1) / 2));
        int bitnum = t * (t + 3) / 2 - z;
        int step = z - t * (t + 1) / 2;
        return new int[]{bitnum, step}; // Returning an array containing the two numbers
    }

    public List<List<Integer>> getDimacs(){
        // add goal to currentDimacs and return
        List<List<Integer>>  totalDimacs = currentDimacs;
        return totalDimacs;
    }
}
