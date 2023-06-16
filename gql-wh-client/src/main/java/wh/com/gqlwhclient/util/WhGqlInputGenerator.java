package wh.com.gqlwhclient.util;

import ch.qos.logback.core.testUtil.RandomUtil;
import wh.com.gqlwhclient.model.PropertyUnitInput;

import java.util.Random;
import java.util.regex.Pattern;

public class WhGqlInputGenerator {

    private static final String[] UNIT_TYPE_SEEDS = {
            "VILLA_",
            "APA_",
            "ROOM_"
    };

    private static String getRandomUnitType() {
        Random generator = new Random();
        int randomIndex = generator.nextInt(UNIT_TYPE_SEEDS.length);
        return UNIT_TYPE_SEEDS[randomIndex];
    }

    /**
     * min - inclusive
     * max - exclusive.
     *
     * @param min
     * @param max
     * @return
     */
    private static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static String getPostfix() {
        Random random = new Random();
        String generatedString = random.ints(97, 122 + 1)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString.toUpperCase().substring(0, 3);
    }

    public static PropertyUnitInput getRandomPropertyUnitInput() {
        String unitType_ = getRandomUnitType();
        String code = unitType_ + getPostfix();
        String unitType = unitType_.replace("_", "").toLowerCase();
        String name = Pattern.compile("^.").matcher(code.replace("_", " ").toLowerCase()).
                replaceFirst(m -> m.group().toUpperCase());
        return PropertyUnitInput.
                builder().
                unitCode(code).
                unitType(unitType).
                name(name).
                minimumPersons(randomInt(0, 11)).
                maximumPersons(randomInt(0, 11)).
                build();
    }
}
