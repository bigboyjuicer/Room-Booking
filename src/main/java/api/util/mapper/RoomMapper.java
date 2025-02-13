package api.util.mapper;

import api.dto.RoomDto;
import api.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomMapper {

    RoomMapper MAPPER = Mappers.getMapper(RoomMapper.class);

    Room toRoom(RoomDto roomDto);

    RoomDto toRoomDto(Room room);

    List<Room> toRooms(List<RoomDto> roomDtoList);

    List<RoomDto> fromRooms(List<Room> rooms);

}
