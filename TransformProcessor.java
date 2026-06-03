import java.util.stream.Stream;

public class TransformProcessor {
    @DataProcessor
    public Stream<String> toUpperCase(Stream<String> data) {
        return data.map(String::toUpperCase);
    }
}
