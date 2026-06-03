import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class LabMain {
    static void main() {
        String sourceFile = "input.txt";
        String destFile = "output.txt";

        try {
            Files.write(Paths.get(sourceFile), Arrays.asList(
                    "java", "c", "python", "1c", "multithreading", "api", "stream", "code"
            ));

            DataManager manager = new DataManager();
            manager.registerDataProcessor(new FilterProcessor());
            manager.registerDataProcessor(new TransformProcessor());

            manager.loadData(sourceFile);
            manager.processData();
            manager.saveData(destFile);

            System.out.println("--- Результат обработки ---");
            Files.readAllLines(Paths.get(destFile)).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
