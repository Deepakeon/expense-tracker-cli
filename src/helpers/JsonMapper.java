package helpers;

import model.Expense;

import java.util.ArrayList;
import java.util.List;

public class JsonMapper {
    public static ArrayList<Expense> stringToExpense(String jsonString){
        ArrayList<Expense> expenses = new ArrayList<>();
        String[] expenseList = jsonString.replace("]", "").replace("[", "").split("},");

        for (String expense: expenseList){
            if(!expense.isEmpty()){
                String json = expense.replace("}", "").replace("{", "").replace("\"", "");
                expenses.add(Expense.fromJson(json));
            }
        }
        return expenses;
    }

    public static String expenseToString(List<Expense> expenses){
        StringBuilder json = new StringBuilder();

        int idx = 0;
        for (Expense expense: expenses){
            if(idx >= 1){
                json.append(",");
            }
            json.append(expense.serializeToJsonString());
            idx+=1;
        }
        return "[" + json.toString() + "]";
    }
}