package api.util.mapper;

import api.dto.post.RoomCreateDto;
import api.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomCreateMapper {

    RoomCreateMapper MAPPER = Mappers.getMapper(RoomCreateMapper.class);

    Room toRoom (RoomCreateDto roomCreateDto);

    RoomCreateDto toPostRoomDto(Room room);

    List<Room> toRooms (List<RoomCreateDto> roomCreateDtos);

    List<RoomCreateDto> fromRooms(List<Room> rooms);

}
