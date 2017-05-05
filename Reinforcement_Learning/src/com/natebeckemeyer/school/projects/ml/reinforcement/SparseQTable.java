import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;

import java.util.HashMap;
import java.util.Set;

/**
 * Defines a Q-Table that provides a Q function for use in Q-Learning.
 * Essentially a wrapper around a {@link HashMap}, the SparseQTable hashes a state and an action in a new
 * {@link StateAction}, and maps that to an appropriate Q-value.
 */
public class SparseQTable
{

    private final HashMap<StateAction, Double> table;

    public SparseQTable()
    {
        table = new HashMap<>();
    }

    public Set<StateAction> getAllPairs()
    {
        return table.keySet();
    }

    /**
     * Gets the Q-Value for a given state and action.
     *
     * @param state  State of the q-value.
     * @param action Action of the q-value.
     * @return The q-value for the provided state-action pair.
     */
    public double getQValue(Board state, int action)
    {
        return getQValue(new StateAction(state, action));
    }

    public double getQValue(StateAction sa)
    {
        return table.getOrDefault(sa, 0.);
    }

    /**
     * Takes a given value, and set the q-value of the state-action pair to that value.
     *
     * @param state  State of the q-value.
     * @param action Action of the q-value.
     * @param val    The value to set the state-action pair to.
     */
    public void setQValue(Board state, int action, double val)
    {
        setQValue(new StateAction(state, action), val);
    }

    public void setQValue(StateAction sa, double val)
    {
        table.put(sa, val);
    }
}