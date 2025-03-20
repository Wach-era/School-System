package pages;

import java.util.HashMap;
import java.util.Map;

public class ClassProgression {
    private static final Map<String, String> progressionMap = new HashMap<>();

    static {
        progressionMap.put("Form 1", "Form 2");
        progressionMap.put("Form 2", "Form 3");
        progressionMap.put("Form 3", "Form 4");
        progressionMap.put("Form 4", null); // End of schooling
    }

    public static String getNextClass(String currentClass) {
        return progressionMap.get(currentClass);
    }
}
