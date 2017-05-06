package com.natebeckemeyer.school.projects.ml.reinforcement;

import com.samvbeckmann.machinelearning.reinforcement.SparseQTable;
import com.samvbeckmann.machinelearning.reinforcement.players.TicTacToePlayer;
import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;
import com.samvbeckmann.machinelearning.reinforcement.simulation.PlayerToken;

import java.util.*;

/**
 * Created for Reinforcement Learning by @author Nate Beckemeyer on 2017-04-17.
 * <p>
 * Q learning
 */
public class QLearningPlayer implements TicTacToePlayer
{
    private SparseQTable qTable = new SparseQTable();

    private Integer prevAction = null;

    private Board prevBoard = null;

    double temperature = 1;

    int getBestAction(Board board)
    {
        return Collections.max(board.getAvailableActions(),
                Comparator.comparingDouble(a -> qTable.getQValue(board, a)));
    }

    /**
     * Uses Boltzmann exploration to identify the next move on the given board.
     * Uses log-likelihoods.
     *
     * @param board
     * @return The next action to take
     */
    int boltzmannExploration(Board board)
    {
        List<Integer> actions = board.getAvailableActions();

        ArrayList<Double> probabilities = new ArrayList<>(actions.size());
        for (int i = 0; i < actions.size(); i++)
            probabilities.add(Math.exp(qTable.getQValue(board, i) / temperature));

        double sum = probabilities.stream().reduce(Double::sum).orElseThrow(
                () -> new RuntimeException("Empty action stream in TicTacToe player!"));
        for (int i = 0; i < actions.size(); i++)
            probabilities.set(i, probabilities.get(i) / sum);

        double probSum = 0;
        for (int i = 0; i < actions.size(); i++)
        {
            probSum += probabilities.get(i);
            probabilities.set(i, probSum);
        }


        double choice = Math.random();
        for (int action = 0; action < probabilities.size(); action++)
            if (choice < probabilities.get(action))
                return action;

        throw new RuntimeException("Could not select action in Boltzmann exploration.");
    }

    int epsilonGreedy(Board board)
    {
        if (Math.random() < temperature)
            return board.getAvailableActions().get(new Random().nextInt(board.getAvailableActions().size()));
        else
            return getBestAction(board);
    }

    int getNextAction(Board board)
    {
        return epsilonGreedy(board);
    }


    /**
     * Method for an agent to interact with the Tic-Tac-Toe board.
     * This method is called precisely one time on each turn. An agent can determine where the other player moved by
     * storing the previous board state and comparing it to the current board state.
     *
     * @param board Current board state
     * @return Location that the agent wishes to move this turn.
     */
    @Override public int interact(Board board)
    {
        prevBoard = new Board(board);
        prevAction = getNextAction(board);
        qTable.incrementAlpha(prevBoard, prevAction);
        return prevAction;
    }

    /**
     * Gives a reward value to the agent for its most recent move. This method will be called a maximum of one time per
     * turn, although it may not be called at all. The reward is always in respect to the most recently made movement.
     *
     * @param board
     * @param reward Value of the reward for the agent's last move. Higher rewards are better.
     */
    @Override public void giveReward(Board board, double reward, boolean terminal)
    {
        if (prevBoard == null)
            return;

        // If there exists possible actions, get the highest value one; else, assume 0 (it's probably terminal)
        double maxNext = board.getAvailableActions().size() > 0 ? -Double.MAX_VALUE : 0;
        for (int a : board.getAvailableActions())
            maxNext = Math.max(qTable.getQValue(board, a), maxNext);

        double deltaQ = qTable.alphaCalc(prevBoard, prevAction) * (reward + 0.9 * maxNext - qTable.getQValue(prevBoard,
                prevAction));
        qTable.setQValue(prevBoard, prevAction, qTable.getQValue(prevBoard, prevAction) + deltaQ);

        if (terminal) {
            prevBoard = null;
            temperature*= .999;
        }



    }

    /**
     * Update the token that represents the player.
     * This method is called at the initialization of each player, and again if the player's token changes.
     * For most players, this method is only called once.
     * However, in special cases, such as an agent designed to give out rewards or test certain moves, this
     * method may
     * be called multiple times.
     * Agents should be designed to accommodate this method being called multiple times over their lifetimes.
     *
     * @param playerID The token that represents this player. Either {@link PlayerToken#X_PLAYER} or
     *                 {@link PlayerToken#O_PLAYER}.
     */
    @Override public void setPlayer(PlayerToken playerID)
    {

    }
}
