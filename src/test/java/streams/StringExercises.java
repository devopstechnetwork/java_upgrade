package streams;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StringExercises {
    private final List<String> strings = Arrays.asList("this", "is", "a",
            "list", "of", "strings");

    @Test
    public void stringLengthSort_InnerClass() {     // Java 5, 6, 7
        strings.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        System.out.println(strings);
    }

    @Test
    public void stringLengthSort_lambda() {
        // Use lambda for the Comparator (reverse sort)
        strings.sort((s1, s2) -> s2.length() - s1.length());
        System.out.println(strings);

        // Use the "sorted" method on Stream
        strings.stream()
                .sorted((s1, s2) -> s1.length() - s2.length())
                .forEach(System.out::println);
    }

    private static int compareStrings(String s1, String s2) {
        return s1.length() - s2.length();
    }

    @Test  // Use a lambda that calls 'compareStrings' directly
    public void stringLengthSort_methodCall() {
        strings.stream()
                .sorted((s1, s2) -> compareStrings(s1, s2))
                .forEach(System.out::println);
    }

    @Test  // Use a method ref to 'compareStrings'
    public void stringLengthSort_methodRef() {
        strings.stream()
                .sorted(StringExercises::compareStrings)
                .forEach(System.out::println);
    }

    @Test  // Use Comparator.comparingInt
    public void stringLengthSort_comparingInt() {
        strings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);
    }

    @Test
    public void demoCollectors() {
        // Get only strings of even length
        // Add them to a LinkedList
        List<String> evens = strings.stream()
                .filter(s -> s.length() % 2 == 0)
                // .collect(Collectors.toCollection(() -> new LinkedList<>()));
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(evens);
        System.out.println(evens.getClass()
                .getName());

        // Add the strings to a map of string to length
        Map<String, Integer> map = strings.stream()
                // .collect(Collectors.toMap(s -> s, s -> s.length()));
                // .collect(Collectors.toMap(s -> s, String::length));
                .collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(map);

        // Filter out nulls, then print even-length strings
        List<String> stringsWithNulls = Arrays.asList("this", null, "is", "a",
                null, "list", null, null, "of", "strings", null, "with", "nulls", null);

        stringsWithNulls.stream()
                // .filter(s -> s != null && s.length() % 2 == 0)  // short-circuiting logical AND
                // .filter(s -> s != null)
                .filter(Objects::nonNull)
                .filter(s -> s.length() % 2 == 0)
                .forEach(System.out::println);

        // Combine the two predicates and use the result to print non-null, even-length strings

        Predicate<String> nonNull = Objects::nonNull;
        Predicate<String> evenLength = s -> s.length() % 2 == 0;

        Logger logger = Logger.getLogger(StringExercises.class.getName());
        Consumer<String> consolePrint = System.out::println;
        Consumer<String> loggerPrint = logger::info;

        stringsWithNulls.stream()
                .filter(nonNull.and(evenLength)) // Function composition
                .forEach(consolePrint.andThen(loggerPrint));
    }

}
