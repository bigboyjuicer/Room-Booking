package api.util.comparator;

import api.entity.Room;

import java.util.Comparator;

public class RoomCapacityComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        return Integer.compare(o1.getCapacity(), o2.getCapacity());
    }
}
