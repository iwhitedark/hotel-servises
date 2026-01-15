@Entity
public class Room {
    @Id @GeneratedValue
    public Long id;
    public Long hotelId;
    public String number;
    public int timesBooked;
}
