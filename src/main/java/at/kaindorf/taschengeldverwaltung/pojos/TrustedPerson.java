package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrustedPerson extends Person {

    private String email;
    private String telNr;
    private String town;
    private String zipCode;
    private String street;
    private String houseNr;
    private Relation relation;
    private TransmissionMethod method;

    @Override
    public String toString() {
        return "TrustedPerson{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", titleBefore='" + getTitleBefore() + '\'' +
                ", titleAfter='" + getTitleAfter() + '\'' +
                ", salutation=" + getSalutation() +
                ", email='" + email + '\'' +
                ", telNr='" + telNr + '\'' +
                ", town='" + town + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", relation=" + relation +
                ", method=" + method +
                '}';
    }

    public TrustedPerson(@NonNull Long id, String firstName, String lastName, String titleBefore, String titleAfter, Salutation salutation, String email, String telNr, String town, String zipCode, String street, String houseNr, Relation relation, TransmissionMethod method) {
        super(id, firstName, lastName, titleBefore, titleAfter, salutation);
        this.email = email;
        this.telNr = telNr;
        this.town = town;
        this.zipCode = zipCode;
        this.street = street;
        this.houseNr = houseNr;
        this.relation = relation;
        this.method = method;
    }


}
