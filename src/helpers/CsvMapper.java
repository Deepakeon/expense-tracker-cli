package helpers;

import model.Expense;

import java.util.ArrayList;

public class CsvMapper {
    public static String expenseToCsv(String[] headers, ArrayList<Expense> expenses){
        StringBuilder csv = new StringBuilder();
        for(String header: headers){
            csv.append(header).append(",");
        }
        csv.append("\n");
        expenses.forEach(expense -> {
            csv.append(expense.serializeToCsvString()).append("\n");
        });

        return csv.toString();

    }
}
