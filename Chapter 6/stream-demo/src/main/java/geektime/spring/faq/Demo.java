package geektime.spring.faq;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        Arrays.asList("Foo", "Bar").stream()
                .filter(s -> s.equalsIgnoreCase("foo"))
                .map(s -> s.toUpperCase())
                .forEach(System.out::println);

        Arrays.stream(new String[] { "s1", "s2", "s3" })
                // map是将str放到了list中
                .map(s -> Arrays.asList(s))
                // flat则是将list中的元素取出然后拍平
                .flatMap(l -> l.stream())
                .forEach(System.out::println);
    }
}
