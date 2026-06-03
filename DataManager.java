import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {
    private final List<Object> processors = new ArrayList<>();
    private List<String> dataset = new ArrayList<>();

    public void registerDataProcessor(Object processor) {
        processors.add(processor);
    }

    public void loadData(String source) throws IOException {
        dataset = Files.readAllLines(Paths.get(source));
        System.out.println("Данные загружены. Количество записей: " + dataset.size());
    }

    public void saveData(String destination) throws IOException {
        Files.write(Paths.get(destination), dataset);
        System.out.println("Данные сохранены в: " + destination);
    }

    @SuppressWarnings("unchecked")
    public void processData() throws InterruptedException, ExecutionException {
        if (dataset.isEmpty()) return;

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = (int) Math.ceil((double) dataset.size() / numThreads);
        List<Callable<List<String>>> tasks = new ArrayList<>();

        for (int i = 0; i < dataset.size(); i += chunkSize) {
            int start = i;
            int end = Math.min(dataset.size(), i + chunkSize);
            List<String> chunk = dataset.subList(start, end);

            tasks.add(() -> {
                Stream<String> currentStream = chunk.stream();

                for (Object processor : processors) {
                    for (Method method : processor.getClass().getDeclaredMethods()) {
                        if (method.isAnnotationPresent(DataProcessor.class)) {
                            currentStream = (Stream<String>) method.invoke(processor, currentStream);
                        }
                    }
                }
                return currentStream.collect(Collectors.toList());
            });
        }

        List<Future<List<String>>> futures = executor.invokeAll(tasks);
        List<String> resultData = new ArrayList<>();

        for (Future<List<String>> future : futures) {
            resultData.addAll(future.get());
        }

        this.dataset = resultData;
        executor.shutdown();
    }
}
