package api.util.mapper;

import api.dto.get.BookingDto;
import api.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookingMapper {

    BookingMapper MAPPER = Mappers.getMapper(BookingMapper.class);

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "time", target = "time")
    BookingDto toBookingDto(Booking booking);

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "time", target = "time")
    List<BookingDto> toBookingDtoList(List<Booking> bookings);

}
