import java.util.stream.Stream;

public class FilterProcessor {
    @DataProcessor
    public Stream<String> filterInvalidAndShort(Stream<String> data) {
        return data.filter(s -> s != null && !s.trim().isEmpty() && s.length() > 3);
    }
}
