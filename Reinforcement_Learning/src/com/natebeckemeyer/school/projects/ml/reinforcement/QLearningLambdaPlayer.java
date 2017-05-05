import com.samvbeckmann.machinelearning.reinforcement.players.TicTacToePlayer;
import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;
import com.samvbeckmann.machinelearning.reinforcement.simulation.PlayerToken;

import java.util.Random;

/**
 * Created for Reinforcement Learning by @author Nate Beckemeyer on 2017-04-17.
 * <p>
 * Q learning
 */
public class QLearningLambdaPlayer implements TicTacToePlayer
{
    private SparseQTable qTable = new SparseQTable();

    private FrequencyTable fTable = new FrequencyTable();

    private Integer prevAction = null;

    private Board prevBoard = null;

    private PlayerToken player = null;

    double alpha = 1;
    double gamma = 1;
    double lambda = .8;

    double eps = 1;

    private void reset()
    {
        fTable.updatAllEligibilities(true, 0, 0); // Reset to 0
        prevBoard = null;
        prevAction = null;
    }

    int getBestAction(Board board)
    {
        if (board.getAvailableActions().size() <= 0)
            return -1;
        int best = board.getAvailableActions().get(0);

        for (int choice : board.getAvailableActions())
            if (qTable.getQValue(board, choice) > qTable.getQValue(board, best))
                best = choice;

        return best;
    }

    int getNextAction(Board board)
    {
        eps *= .995;
        if (Math.random() < eps)
            return board.getAvailableActions().get(new Random().nextInt(board.getAvailableActions().size()));
        else
            return getBestAction(board);
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
        int bestAction = getBestAction(board);

        fTable.updatAllEligibilities(prevAction == bestAction, gamma * lambda, 0);
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

        double delta = reward + gamma * qTable.getQValue(board, getBestAction(board)) - qTable.getQValue(prevBoard,
                prevAction);
        fTable.incrementEligibility(prevBoard, prevAction); // Accumulating traces
        for (StateAction entry : qTable.getAllPairs())
            qTable.setQValue(entry, qTable.getQValue(entry) + alpha * delta * fTable.getEligibility(entry));

        if (terminal)
            reset();
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
        this.player = playerID;
    }
}
