package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {

    @NonNull
    private Long id;
    private String firstName;
    private String lastName;
    private String titleBefore;
    private String titleAfter;
    private Salutation salutation;


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", titleBefore='" + titleBefore + '\'' +
                ", titleAfter='" + titleAfter + '\'' +
                ", salutation=" + salutation +
                '}';
    }
}
