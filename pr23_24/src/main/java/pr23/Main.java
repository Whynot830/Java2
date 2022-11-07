package pr23;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

public class Main {

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumnMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Transaction.class);
        String[] columns = new String[]{"accountType", "accountNumber",
                "currency", "operationDate", "transactionReference",
                "transactionDescription", "income", "expense"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

    @SuppressWarnings({"unchecked"})
    public static void main(String[] args) throws Exception {
        final String path = "pr23_24\\src\\main\\java\\pr23\\csv\\file.csv";

        HashMap<String, Double> orgExpenses = new HashMap<>();
        List<Transaction> transactionList = new CsvToBeanBuilder<Transaction>(new FileReader(path))
                .withType(Transaction.class)
                .withEscapeChar('\0')
                .withMappingStrategy(setColumnMapping())
                .build()
                .parse();

        String[] description;
        String organization;
        double totalExpense = 0;
        double totalIncome = 0;

        for (Transaction transaction : transactionList) {
            if (transaction != transactionList.get(0)) {

                if (transaction.getExpense() != 0) {
                    totalExpense += transaction.getExpense();

                    description = transaction.getTransactionDescription().split("( ){3,}")[1].split("(\\\\)|(/)");
                    organization = description[description.length - 1].replaceAll("\\d", "");
                    if (organization.charAt(0) == ' ')
                        organization = organization.substring(1);

                    if (!orgExpenses.containsKey(organization))
                        orgExpenses.put(organization, transaction.getExpense());
                    else
                        orgExpenses.replace(organization, orgExpenses.get(organization),
                                orgExpenses.get(organization) + transaction.getExpense());
                } else
                    totalIncome += transaction.getIncome();
            }
        }
        System.out.println("Expenses by organizations: ");
        for (String org : orgExpenses.keySet())
            System.out.println(org + ": " + orgExpenses.get(org));
        System.out.println("\nTotal income = " + totalIncome);
        System.out.println("Total expense = " + totalExpense);
    }
}