package repository;

import helpers.CsvMapper;
import helpers.FileManager;
import helpers.JsonMapper;
import model.Expense;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ExpenseRepository {
    private final FileManager fileManager;

    public ExpenseRepository(FileManager fileManager){
        this.fileManager = fileManager;
    }

    public ArrayList<Expense> getAllExpenses(){
        String json = fileManager.readFile();
        return JsonMapper.stringToExpense(json);
    }

    private void saveExpenses(ArrayList<Expense> expenses){
        String updatedJson = JsonMapper.expenseToString(expenses);
        fileManager.writeFile(updatedJson);
    }

    public void addExpense(Expense expense){
        ArrayList<Expense> expenses = getAllExpenses();
        expenses.add(expense);
        saveExpenses(expenses);
    }

    public void updateExpense(int id, Consumer<Expense> updater){
        ArrayList<Expense> expenses = getAllExpenses();
        for (Expense expense: expenses){
            if(expense.getId() == id){
                updater.accept(expense);
                expense.setUpdatedAt(System.currentTimeMillis());
            }
        }
        saveExpenses(expenses);
    }

    public void deleteExpense(int id){
        ArrayList<Expense> expenses = getAllExpenses();
        expenses.removeIf(expense -> expense.getId() == id);
        saveExpenses(expenses);
    }

    public void exportToCSV(){
        FileManager manager = new FileManager("expenses.csv");
        manager.createFile();
        String[] headers = {"id", "description", "category", "amount", "createdAt", "updatedAt"};
        ArrayList<Expense> expenses = getAllExpenses();
        String csv = CsvMapper.expenseToCsv(headers, expenses);
        manager.writeFile(csv);
    }


}
