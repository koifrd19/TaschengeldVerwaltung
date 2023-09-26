package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingsVillagerPerson {

    private String shortSign;
    private LocalDate dateOfBirth;
    private LocalDate dateOfExit;
    private String note;
    private Person trustedPerson;
    private List<Booking> bookings;

}
