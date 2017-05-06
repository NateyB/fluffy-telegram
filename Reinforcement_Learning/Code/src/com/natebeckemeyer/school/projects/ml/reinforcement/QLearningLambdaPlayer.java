package com.natebeckemeyer.school.projects.ml.reinforcement;

import com.samvbeckmann.machinelearning.reinforcement.SparseQTable;
import com.samvbeckmann.machinelearning.reinforcement.players.TicTacToePlayer;
import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;
import com.samvbeckmann.machinelearning.reinforcement.simulation.PlayerToken;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created for Reinforcement Learning by @author Nate Beckemeyer on 2017-04-17.
 * <p>
 * Q learning
 */
public class QLearningLambdaPlayer implements TicTacToePlayer
{
    private SparseQTable qTable = new SparseQTable();

    private Integer prevAction = null;
    private Integer actionToTake = -1;

    private Board prevBoard = null;

    double eps = 1;

    int getBestAction(Board board)
    {
        return Collections.max(board.getAvailableActions(),
                Comparator.comparingDouble(a -> qTable.getQValue(board, a)));
    }

    int getNextAction(Board board)
    {
        return board.getAvailableActions().size() == 0 ? -1 : (Math.random() < eps ? board.getAvailableActions().get(
                new Random().nextInt(board.getAvailableActions().size())) : getBestAction(board));
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
        qTable.incrementAlpha(prevBoard = new Board(board), actionToTake);
        return prevAction = actionToTake;
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
        actionToTake = getNextAction(board);
        if (prevBoard == null)
            return;

        int best = board.getAvailableActions().size() > 0 ? getBestAction(board) : -1;

        double delta = reward + 0.9 * qTable.getQValue(board, best) - qTable.getQValue(prevBoard, prevAction);
        qTable.setEligibility(prevBoard, prevAction, qTable.getEligibility(prevBoard, prevAction) + 1);

        for (SparseQTable.StateAction tuple : qTable.getAllPairs())
        {
            qTable.setQValue(tuple,
                    qTable.getQValue(tuple) + qTable.alphaCalc(tuple) * delta * qTable.getEligibility(tuple));
            qTable.setEligibility(tuple, best == actionToTake ? .9 * .9 * qTable.getEligibility(tuple) : 0);
        }

        if (terminal)
        {
            prevBoard = null;
            eps *= .999;
            for (SparseQTable.StateAction tuple : qTable.getAllPairs())
                qTable.setEligibility(tuple, 0);
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
