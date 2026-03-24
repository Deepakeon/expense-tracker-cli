package service;

import helpers.CsvMapper;
import helpers.Utils;
import model.Expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import repository.ExpenseRepository;

public class ExpenseService {
    final private ExpenseRepository expenseRepository;
    private int latestExpenseId = 0;

    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    public ArrayList<Expense> getAllExpenses(){
        return expenseRepository.getAllExpenses();
    }

    private int getLatestExpenseId(){
        ArrayList<Expense> expenses = getAllExpenses();
        if (!expenses.isEmpty()){
            latestExpenseId = expenses.get(expenses.size() -1).getId();
        }
        return latestExpenseId;
    }

    public void addExpense(String description, BigDecimal amount, String category){
        int id = getLatestExpenseId() + 1;
        long now = System.currentTimeMillis();
        Expense expense = new Expense(amount, category, now, description, id, now);
        expenseRepository.addExpense(expense);
    }

    public void updateExpense(int id, Map<String, Object> updates){
        expenseRepository.updateExpense(id, expense -> {
            updates.forEach((key, value) -> {
                switch (key){
                    case "description" -> expense.setDescription((String) value);
                    case "category" -> expense.setCategory((String) value);
                    case "amount" -> expense.setAmount((BigDecimal) value);
                }
            });
        });
    }

    public void deleteExpense(int id){
        expenseRepository.deleteExpense(id);
    }

    public BigDecimal calculateSummary(ArrayList<Expense> expenses){
        BigDecimal totalExpenseAmount = BigDecimal.ZERO;

        for(Expense expense: expenses){
            totalExpenseAmount = totalExpenseAmount.add(expense.getAmount());
        }

        return totalExpenseAmount;
    }

    public BigDecimal getSummary(){
        ArrayList<Expense> expenses = getAllExpenses();
        return calculateSummary(expenses);
    }

    public ArrayList<Expense> getExpensesByMonth(int month){
        ArrayList<Expense> expenses = getAllExpenses();
        return new ArrayList<Expense>(expenses.stream().filter(expense -> {
            long createdAt = expense.getCreatedAt();
            return Utils.getMonthValue(createdAt) == month;
        }).toList());
    }

    public ArrayList<Expense> getExpensesByCategory(String category){
        ArrayList<Expense> expenses = getAllExpenses();
        return new ArrayList<Expense>(expenses.stream().filter(expense -> expense.getCategory().equals(category)).toList());
    }

    public BigDecimal getSummaryByMonth(int month){
        ArrayList<Expense> expenses = getExpensesByMonth(month);
        return calculateSummary(expenses);
    }

    public void exportToCSV(){
        expenseRepository.exportToCSV();
    }
}
