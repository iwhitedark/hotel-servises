@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomRepository repo;

    public RoomController(RoomRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/recommend")
    public List<Room> recommend() {
        return repo.findAllByOrderByTimesBookedAscIdAsc();
    }

    @PostMapping("/{id}/confirm-availability")
    public void confirm(@PathVariable Long id) {}

    @PostMapping("/{id}/release")
    public void release(@PathVariable Long id) {}
}
