package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VillagerPerson extends Person {

    private String shortSign;
    private LocalDate dateOfBirth;
    private LocalDate dateOfExit;
    private String note;
    private Person trustedPerson;
    private List<Booking> bookings;

    public VillagerPerson(long id, String firstName, String lastName, String titleBefore, String titleAfter, Salutation salutation, String shortSign, LocalDate dateOfBirth, LocalDate dateOfExit, String note, Person trustedPerson, List<Booking> bookings) {
        super(id, firstName, lastName, titleBefore, titleAfter, salutation);
        this.shortSign = shortSign;
        this.dateOfBirth = dateOfBirth;
        this.dateOfExit = dateOfExit;
        this.note = note;
        this.trustedPerson = trustedPerson;
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "VillagerPerson{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", titleBefore='" + getTitleBefore() + '\'' +
                ", titleAfter='" + getTitleAfter() + '\'' +
                ", salutation=" + getSalutation() +
                ", shortSign='" + shortSign + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfExit=" + dateOfExit +
                ", note='" + note + '\'' +
                ", trustedPerson=" + trustedPerson +
                ", bookings=" + bookings +
                '}';
    }

    public static void main(String[] args) {

    }
}
