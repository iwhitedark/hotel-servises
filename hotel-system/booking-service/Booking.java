@Entity
public class Booking {
    @Id @GeneratedValue
    public Long id;
    public Long userId;
    public Long roomId;
    public String status;
}
