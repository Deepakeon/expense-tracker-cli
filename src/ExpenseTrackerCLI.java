import helpers.FileManager;
import repository.ExpenseRepository;
import service.CommandManager;
import service.ExpenseService;

public class ExpenseTrackerCLI {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager(new ExpenseService(new ExpenseRepository(new FileManager("expenses.json"))));
        commandManager.init(args);
    }
}