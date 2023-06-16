package wh.com.gqlwhclient.integration;

import java.util.Random;

public enum EWhActions {
    ADD,
    DEL;

    private static final Random RANDOM = new Random();

    public static EWhActions randomAction()  {
        EWhActions[] actions = values();
        return actions[RANDOM.nextInt(actions.length)];
    }
}
