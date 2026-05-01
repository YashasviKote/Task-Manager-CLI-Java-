import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private static final String FILE_NAME = "tasks.txt";

    public void saveTasks(List<Task> tasks) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Task task : tasks) {
                writer.write(task.getId() + "," +
                        task.getTitle() + "," +
                        task.isCompleted());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public List<Task> loadTasks() {

        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                boolean completed = Boolean.parseBoolean(parts[2]);

                Task task = new Task(id, title);

                if (completed) {
                    task.markCompleted();
                }

                tasks.add(task);
            }

        } catch (IOException e) {
            System.out.println("No existing tasks found.");
        }

        return tasks;
    }
}