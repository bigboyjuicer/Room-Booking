package api.dto;

import api.entity.Weekday;

import java.util.List;

public class RoomDto {

    private int id;

    private int capacity;

    private String name;

    private List<Weekday> weekdays;

    private boolean isActive;

    private String image;

}
