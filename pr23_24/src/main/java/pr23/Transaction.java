package pr23;

import com.opencsv.bean.CsvBindByName;

public class Transaction {
    @CsvBindByName
    private String accountType;
    @CsvBindByName
    private String accountNumber;
    @CsvBindByName
    private String currency;
    @CsvBindByName
    private String operationDate;
    @CsvBindByName
    private String transactionReference;
    @CsvBindByName
    private String transactionDescription;
    @CsvBindByName
    private String income;
    @CsvBindByName
    private String expense;

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public Double getIncome() {
        return Double.parseDouble(income);
    }

    public Double getExpense() {
        return Double.parseDouble(expense);
    }
}
