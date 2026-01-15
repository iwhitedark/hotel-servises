public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByOrderByTimesBookedAscIdAsc();
}
