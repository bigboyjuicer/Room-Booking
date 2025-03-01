package api.util.mapper;

import api.dto.get.BookingProfileDto;
import api.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookingProfileMapper {

    BookingProfileMapper MAPPER = Mappers.getMapper(BookingProfileMapper.class);

    @Mapping(source = "room", target = "room")
    BookingProfileDto toBookingProfileDto(Booking booking);

    @Mapping(source = "room", target = "room")
    Booking toBooking(BookingProfileDto bookingDto);

    @Mapping(source = "room", target = "room")
    List<BookingProfileDto> fromBookings(List<Booking> bookings);

}
