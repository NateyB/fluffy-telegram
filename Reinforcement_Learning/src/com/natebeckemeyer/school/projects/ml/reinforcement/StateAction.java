import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;

/**
 * Created for Reinforcement Learning by @author Nate Beckemeyer on 2017-04-17.
 */
class StateAction
{
    private Board state;
    private int action;

    StateAction(Board s, int a)
    {
        state = s;
        action = a;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateAction that = (StateAction) o;

        return action == that.action && state == that.state;
    }

    @Override
    public int hashCode()
    {
        return 31 * state.hashCode() + action;
    }


    @Override
    public String toString()
    {
        return state.toString() + " " + action;
    }
}