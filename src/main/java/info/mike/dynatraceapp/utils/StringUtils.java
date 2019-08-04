package info.mike.dynatraceapp.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {

    public static String dotSeparatedToCapitalized(String s) {
        return Arrays.asList(s.split("\\."))
            .stream()
            .map(org.springframework.util.StringUtils::capitalize)
            .collect(Collectors.joining(" "));
    }
}
