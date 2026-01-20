package assignments.Ex3;

import exe.ex3.game.PacManAlgo;

public class GameInfo {
    public static final String MY_ID = "331104323";
    public static final int CASE_SCENARIO = 3;
    public static final long RANDOM_SEED = 31;
    public static final boolean CYCLIC_MODE = true;
    public static final int DT = 50;
    public static final double RESOLUTION_NORM = 1.2;

    private static PacManAlgo _manualAlgo = new ManualAlgo();
    private static PacManAlgo _myAlgo = new Ex3Algo();

    public static final PacManAlgo ALGO = _myAlgo;
}