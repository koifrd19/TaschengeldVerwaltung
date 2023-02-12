package at.kaindorf.taschengeldverwaltung.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Booking {

    private Long villagerId;
    private LocalDateTime dateTime;
    private String username;
    private Float value;
    private Long receiptNumber;
    private String note;
    private Purpose purpose;

}
