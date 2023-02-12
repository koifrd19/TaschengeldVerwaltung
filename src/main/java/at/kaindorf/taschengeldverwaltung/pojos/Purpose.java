package at.kaindorf.taschengeldverwaltung.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purpose {

    private Long id;
    private String text;
    private short multiplier;
    private boolean isActive;

}
