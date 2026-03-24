package service;

import helpers.Utils;
import model.Expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {
    final private ExpenseService expenseService;

    public CommandManager(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    public HashMap<String, Object> getAllAttributes(String[] args){
        HashMap<String, Object> commandAttributesMap = new HashMap<>();

        for (int i=0; i< args.length -1; i+=2){
            commandAttributesMap.put(args[i], args[i+1]);
        }

        return commandAttributesMap;
    }

    public void printExpenses(ArrayList<Expense> expenses){
        for(Expense expense: expenses){
            System.out.println("\n");
            System.out.println("description: " + expense.getDescription());
            System.out.println("category: " + expense.getCategory());
            System.out.println("amount: " + expense.getAmount());
        }
    }

    private static HashMap<String, Object> getUpdates(String category, String description, Object amountValue) {
        HashMap<String, Object> updates = new HashMap<>();

        if(category != null) updates.put("category", category.trim());
        if(description != null) updates.put("description", description.trim());
        if(amountValue != null) {
            BigDecimal amount = new BigDecimal((String) amountValue);
            updates.put("amount", amount);
        }

        if(updates.isEmpty()){
            throw new IllegalArgumentException("No arguments provided");
        }

        return updates;
    }

    public void init(String[] args){
        if(args.length == 0){
            System.out.println("No command attributes provided");
            return;
        }
        String operation = args[0].toLowerCase();
        HashMap<String, Object> commandAttributes = getAllAttributes(Arrays.copyOfRange(args, 1, args.length));

        try {

            switch (operation) {
                case "add" -> {
                    Object categoryValue = Utils.getOrThrow(commandAttributes, "--category", false);
                    Object descriptionValue = Utils.getOrThrow(commandAttributes, "--description", false);
                    Object amountValue = Utils.getOrThrow(commandAttributes, "--amount", false);

                    String category = ((String) categoryValue).trim();
                    String description = ((String) descriptionValue).trim();
                    BigDecimal amount = new BigDecimal((String) amountValue);

                    expenseService.addExpense(description, amount, category);
                }
                case "update" -> {
                    int id = Integer.parseInt(((String) Utils.getOrThrow(commandAttributes, "--id", false)));
                    String category = ((String) Utils.getOrThrow(commandAttributes, "--category", true));
                    String description = ((String) Utils.getOrThrow(commandAttributes, "--description", true));
                    Object amountValue = Utils.getOrThrow(commandAttributes, "--amount", true);

                    HashMap<String, Object> updates = getUpdates(category, description, amountValue);

                    expenseService.updateExpense(id, updates);
                }
                case "delete" -> {
                    int id = Integer.parseInt(((String) Utils.getOrThrow(commandAttributes, "--id", false)).trim());

                    expenseService.deleteExpense(id);
                }
                case "list" -> {
                    ArrayList<Expense> expenses = expenseService.getAllExpenses();
                    printExpenses(expenses);
                }
                case "summary" -> {
                    BigDecimal summary = expenseService.getSummary();
                    if (args.length > 1) {
                        int month = Integer.parseInt((String) Utils.getOrThrow(commandAttributes, "--month", false));
                        summary = expenseService.getSummaryByMonth(month);
                    }
                    System.out.println("$ " + summary);
                }
                case "filter" -> {
                    String category = ((String) Utils.getOrThrow(commandAttributes, "--category", false));

                    ArrayList<Expense> expenses = expenseService.getExpensesByCategory(category);
                    printExpenses(expenses);
                }
                case "export" -> {
                    expenseService.exportToCSV();
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
