package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class TaskManager {
    static final String File_Name = "tasks.csv";
    static final String[] options = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadData(File_Name);
        printOptions(options);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            switch (line) {
                case "add":
                    addTask();
                    break;
                    case "remove":
                        removeTask(tasks, getNumber());
                        break;
                        case "list":
                            printTab(tasks);
                            break;
                            case "exit":
                                saveTabToFile(File_Name, tasks);
                                System.out.println(ConsoleColors.RED + "Bye, bye");
                                System.exit(0);
                                break;
                default:
                    System.out.println("Please enter a valid option");
            }
            printOptions(options);
        }
    }
    public static String[][] loadData(String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.out.println("File does not exist");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an option:" + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }
    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a task description");
        String description = scanner.nextLine();
        System.out.println("Please enter a task date");
        String date = scanner.nextLine();
        System.out.println("Please enter a task priority");
        String priority = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = date;
        tasks[tasks.length-1][2] = priority;
    }
    public static boolean isNumberGreaterEqualZero(String number) {
        if (NumberUtils.isDigits(number)) {
            return Integer.parseInt(number) >= 0;
        }
        return false;
    }
    public static int getNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a number");

        String number = scanner.nextLine();
        while (!isNumberGreaterEqualZero(number)) {
            System.out.println("Incorrect input. Please enter valid number");
            number = scanner.nextLine();
        }
        return Integer.parseInt(number);
    }
    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tasks.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incorrect input. Please enter valid number");
        }
    }
    public static void saveTabToFile(String fileName, String[][] tab) {
        Path path = Paths.get(fileName);

        String[] lines = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(path, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
