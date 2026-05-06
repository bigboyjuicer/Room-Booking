package api.util.mapper;

import api.dto.get.RoomProfileDto;
import api.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomProfileMapper {

    RoomProfileMapper MAPPER = Mappers.getMapper(RoomProfileMapper.class);

    @Mapping(source = "address", target = "address")
    RoomProfileDto toRoomProfileDto(Room room);

    @Mapping(source = "address", target = "address")
    Room toRoom(RoomProfileDto roomProfileDto);
}
