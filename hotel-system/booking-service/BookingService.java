@Service
public class BookingService {

    private final BookingRepository repo;
    private final RestTemplate rest = new RestTemplate();

    @Transactional
    public Booking create(Long roomId) {
        Booking b = new Booking();
        b.userId = 1L;
        b.roomId = roomId;
        b.status = "PENDING";
        repo.save(b);

        try {
            rest.postForObject(
                "http://HOTEL-SERVICE/api/rooms/" + roomId + "/confirm-availability",
                null,
                Void.class
            );
            b.status = "CONFIRMED";
        } catch (Exception e) {
            b.status = "CANCELLED";
        }
        return b;
    }
}
