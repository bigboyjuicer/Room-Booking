package api.util.mapper;

import api.dto.get.AddressProfileDto;
import api.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressProfileMapper {

    AddressProfileMapper MAPPER = Mappers.getMapper(AddressProfileMapper.class);

    AddressProfileDto toAddressProfileDto(Address address);

    Address toAddress(Address address);

}
