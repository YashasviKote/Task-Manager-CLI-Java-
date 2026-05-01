import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskService {

    private final List<Task> tasks;

    public TaskService(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Optional<Task> getTaskById(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    public void markTaskCompleted(int id) {
        getTaskById(id).ifPresent(Task::markCompleted);
    }

    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }

    public List<Task> searchTasks(String keyword) {
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }
}