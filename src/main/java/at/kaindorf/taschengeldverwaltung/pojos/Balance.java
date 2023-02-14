package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Balance {

    private Long villagerId;
    private String firstname;
    private String lastname;
    private Double balance;

    private Long receiptNumber;
    private LocalDate dateOfExecution;
    private String purpose;

    public Balance(Long villagerId, String firstname, String lastname, Double balance) {
        this.villagerId = villagerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
    }
}
