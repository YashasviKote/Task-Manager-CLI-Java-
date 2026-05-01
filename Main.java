import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        FileService fileService = new FileService();
        List<Task> tasks = fileService.loadTasks();
        TaskService service = new TaskService(tasks);

        int idCounter = tasks.size() + 1;

        try (Scanner sc = new Scanner(System.in)) {

            while (true) {

                System.out.println("\n1. Add Task");
                System.out.println("2. View Tasks");
                System.out.println("3. Mark Task Completed");
                System.out.println("4. Delete Task");
                System.out.println("5. Search Task");
                System.out.println("6. View Completed Tasks");
                System.out.println("7. Exit");

                System.out.print("Choose option: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Invalid input. Enter a number.");
                    continue;
                }

                switch (choice) {

                    case 1:
                        System.out.print("Enter task title: ");
                        String title = sc.nextLine();

                        if (title.trim().isEmpty()) {
                            System.out.println("Task title cannot be empty.");
                            break;
                        }

                        service.addTask(new Task(idCounter++, title));
                        fileService.saveTasks(service.getAllTasks());
                        System.out.println("Task added successfully.");
                        break;

                    case 2:
                        if (service.getAllTasks().isEmpty()) {
                            System.out.println("No tasks available.");
                        } else {
                            service.getAllTasks()
                                    .forEach(System.out::println);
                        }
                        break;

                    case 3:
                        System.out.print("Enter task id: ");
                        int id = Integer.parseInt(sc.nextLine());

                        if (service.getTaskById(id).isPresent()) {
                            service.markTaskCompleted(id);
                            fileService.saveTasks(service.getAllTasks());
                            System.out.println("Task marked as completed.");
                        } else {
                            System.out.println("Task not found.");
                        }
                        break;

                    case 4:
                        System.out.print("Enter task id to delete: ");
                        int deleteId = Integer.parseInt(sc.nextLine());

                        if (service.getTaskById(deleteId).isPresent()) {
                            service.deleteTask(deleteId);
                            fileService.saveTasks(service.getAllTasks());
                            System.out.println("Task deleted.");
                        } else {
                            System.out.println("Task not found.");
                        }
                        break;

                    case 5:
                        System.out.print("Enter keyword: ");
                        String keyword = sc.nextLine();

                        List<Task> results = service.searchTasks(keyword);

                        if (results.isEmpty()) {
                            System.out.println("No matching tasks found.");
                        } else {
                            results.forEach(System.out::println);
                        }
                        break;

                    case 6:
                        List<Task> completed = service.getCompletedTasks();

                        if (completed.isEmpty()) {
                            System.out.println("No completed tasks.");
                        } else {
                            completed.forEach(System.out::println);
                        }
                        break;

                    case 7:
                        fileService.saveTasks(service.getAllTasks());
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid option");
                }
            }
        }
    }
}