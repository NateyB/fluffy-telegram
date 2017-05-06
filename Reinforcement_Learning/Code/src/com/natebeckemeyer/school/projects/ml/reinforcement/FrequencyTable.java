package com.natebeckemeyer.school.projects.ml.reinforcement;

import com.samvbeckmann.machinelearning.reinforcement.simulation.Board;

import java.util.HashMap;

/**
 * Created by Nate on 4/17/17.
 */
public class FrequencyTable
{
    private HashMap<StateAction, Integer> freqMap = new HashMap<>();

    private HashMap<StateAction, Double> eligibilityMap = new HashMap<>();

    public FrequencyTable()
    {
    }

    public void incrementFrequency(Board state, int action)
    {
        freqMap.merge(new StateAction(state, action), 1, (k, x) -> x + 1);
    }

    public int getFrequency(Board state, int action)
    {
        return freqMap.getOrDefault(new StateAction(state, action), 0);
    }

    public void incrementEligibility(Board state, int action)
    {
        eligibilityMap.merge(new StateAction(state, action), 1.0, (k, x) -> x + 1);
    }

    /**
     * Multiplicatively updates the eligibilities in the eligibility mapping by {@code firstScalar} if {@code condition}
     * is true, otherwise multiplies them by the {@code secondScalar}.
     *
     * @param condition    The condition to check which scalar to multiply by
     * @param firstScalar  The scalar to use if the condition is true
     * @param secondScalar The scalar to use if the condition is false
     */
    public void updatAllEligibilities(boolean condition, double firstScalar, double secondScalar)
    {
        if (condition)
            eligibilityMap.replaceAll((k, v) -> v * firstScalar);
        else
            eligibilityMap.replaceAll((k, v) -> v * secondScalar);
    }

    public double getEligibility(StateAction entry)
    {
        return eligibilityMap.getOrDefault(entry, 0.0);
    }

}
