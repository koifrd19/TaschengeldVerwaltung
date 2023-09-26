package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BalanceVillagerPerson extends Person{

    private String shortSign;
    private LocalDate dateOfBirth;
    private LocalDate dateOfExit;
    private String note;
    private Double balance;

    public BalanceVillagerPerson(@NonNull Long id, String firstName, String lastName, String titleBefore, String titleAfter, Salutation salutation, String shortSign, LocalDate dateOfBirth, LocalDate dateOfExit, String note, Double balance) {
        super(id, firstName, lastName, titleBefore, titleAfter, salutation);
        this.shortSign = shortSign;
        this.dateOfBirth = dateOfBirth;
        this.dateOfExit = dateOfExit;
        this.note = note;
        this.balance = balance;
    }
}
