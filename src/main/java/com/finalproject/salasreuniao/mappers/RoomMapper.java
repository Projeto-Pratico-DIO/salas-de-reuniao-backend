package com.finalproject.salasreuniao.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.finalproject.salasreuniao.dto.RoomDTO;
import com.finalproject.salasreuniao.model.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
		@Mapping(source = "date", target = "date",  dateFormat="dd-MM-yyyy HH:mm:ss"),
		@Mapping(source = "startHour", target = "startHour", dateFormat="dd-MM-yyyy HH:mm:ss"),
		@Mapping(source = "endHour", target = "endHour",  dateFormat="dd-MM-yyyy HH:mm:ss")
	})
	RoomDTO mapToDTO(Room room);

	List<RoomDTO> mapToListDTO(List<Room> room);

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
		@Mapping(source = "date", target = "date",  dateFormat="dd-MM-yyyy HH:mm:ss"),
		@Mapping(source = "startHour", target = "startHour", dateFormat="dd-MM-yyyy HH:mm:ss"),
		@Mapping(source = "endHour", target = "endHour",  dateFormat="dd-MM-yyyy HH:mm:ss")
	})
	Room mapToRoom(RoomDTO room);
	
	
	List<Room>  mapToListRoom(List<RoomDTO> room);

	
    void castToRoom(@MappingTarget Room room, RoomDTO tagetroomDTO);
}
