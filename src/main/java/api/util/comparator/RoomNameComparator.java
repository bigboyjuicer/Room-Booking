package api.util.comparator;

import api.entity.Room;

import java.util.Comparator;

public class RoomNameComparator implements Comparator<Room> {
    @Override
    public int compare(Room o1, Room o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
    }
}
