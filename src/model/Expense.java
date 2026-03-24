package model;

import java.math.BigDecimal;
import java.util.HashMap;

public class Expense {
    private int id;
    private String description;
    private BigDecimal amount;
    private String category;
    private long createdAt;
    private long updatedAt;

    public Expense(BigDecimal amount, String category, long createdAt, String description, int id, long updatedAt) {
        this.amount = amount;
        this.category = category;
        this.createdAt = createdAt;
        this.description = description;
        this.id = id;
        this.updatedAt = updatedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Expense fromJson(String json){
        String[] expenseDetails = json.split(",");
        HashMap<String, String> expenseMap = new HashMap<>();

        for (String detail: expenseDetails){
            String[] keyValue = detail.split(":");
            expenseMap.put(keyValue[0].trim(), keyValue[1].trim());
        }

        BigDecimal amount = new BigDecimal(expenseMap.get("amount"));
        String category = expenseMap.get("category");
        long createdAt = Long.parseLong(expenseMap.get("createdAt"));
        String description = expenseMap.get("description");
        int id = Integer.parseInt(expenseMap.get("id"));
        long updatedAt = Long.parseLong(expenseMap.get("updatedAt"));

        return new Expense(amount, category, createdAt, description, id, updatedAt);
    }

    public String serializeToJsonString(){
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"category\":\"" + category + "\"," +
                "\"amount\":\"" + amount + "\"," +
                "\"createdAt\":\"" + createdAt + "\"," +
                "\"updatedAt\":\"" + updatedAt + "\""+
                "}";
    }

    public String serializeToCsvString(){
        return id +
                "," + description +
                "," + category +
                "," + amount +
                "," + createdAt +
                "," + updatedAt;
    }

}
