package com.finalproject.salasreuniao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.finalproject.salasreuniao.dto.RoomDTO;
import com.finalproject.salasreuniao.exceptions.ResourceNotFoundException;
import com.finalproject.salasreuniao.mappers.RoomMapper;
import com.finalproject.salasreuniao.model.Room;
import com.finalproject.salasreuniao.repository.RoomRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService {

    private RoomRepository roomRepository;
	
	private final RoomMapper roomMapper ;
	
	public Room createRoom(Room room) throws Exception {
		return roomRepository.save(room);
	}

	public List<RoomDTO> listAll() {
		return roomRepository.listAll()
				.stream()
				.map(roomMapper::mapToDTO)
				.collect(Collectors.toList());
	}
	
	public Room findById(Long id) throws Exception{
		return roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Sala não encontrada"));
	}

	public void deleteById(Long id) throws Exception {
		roomRepository.deleteById(id);
	}

	public Room update(RoomDTO roomDTO) throws Exception {
	  if(roomRepository.findById(roomDTO.getId()).isPresent()) 
		  return roomRepository.save(roomMapper.mapToRoom(roomDTO));
	  else
	      return null;
	}
	
	public Page<RoomDTO> findByExample(RoomDTO roomExample, PageRequest pageOptions) {
		return roomRepository.findAll(Example.of(roomMapper.mapToRoom(roomExample)), pageOptions)
				.map(roomMapper::mapToDTO);
	}
}
