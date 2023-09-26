package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceOverview {

    private Double sum;
    private Double income;
    private Double expenses;
    private List<Balance> balanceList;

    public BalanceOverview(Double sum, List<Balance> balanceList) {
        this.sum = sum;
        this.balanceList = balanceList;
    }

    public BalanceOverview(Double sum, Double income, Double expenses) {
        this.sum = sum;
        this.income = income;
        this.expenses = expenses;
    }
}
