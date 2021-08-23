package com.finalproject.salasreuniao.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.salasreuniao.dto.RoomDTO;
import com.finalproject.salasreuniao.exceptions.ResourceNotFoundException;
import com.finalproject.salasreuniao.mappers.RoomMapper;
import com.finalproject.salasreuniao.model.Room;
import com.finalproject.salasreuniao.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Api(value = "Sala")
@RequestMapping("/api/v1/room")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {

	private RoomService roomService;

	private RoomMapper roomMapper;

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca sala por id.")
	public ResponseEntity<Serializable> findById(@PathVariable("id") Long id)  {
		try {

			return ResponseEntity.ok().body(roomMapper.mapToDTO(roomService.findById(id)));

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping
	@ApiOperation(value = "Busca todo as salas.")
	public ResponseEntity<Object> findAll() {
		try {

			return ResponseEntity.status(HttpStatus.OK)
					.body(roomService.listAll());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um erro ocorreu ao processar a solicitação");
		}
	}
	
	@GetMapping("/pesquisa")
	@ApiOperation(value = "Lista com filtros e com paginação.")
	public Page<RoomDTO> findRoom(@RequestBody RoomDTO roomExample,
			@RequestParam(name = "page" , defaultValue = "0") Integer page,
			@RequestParam(name = "size" , defaultValue = "10") Integer size,
			@RequestParam(name = "sortDirection" , defaultValue = "ASC") String sortDirection){

		return roomService.findByExample(roomExample,
				PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), "id"));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Cria uma sala.")
	public ResponseEntity<Serializable> save(@RequestBody @Valid RoomDTO room) {

		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(roomMapper.mapToDTO(roomService.createRoom(roomMapper.mapToRoom(room))));

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("A sala já se encontra cadastrado na base");

		}  catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um erro ocorreu ao processar a solicitação");
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Atualiza uma sala.")
	public ResponseEntity<Serializable> update(@RequestBody @Valid RoomDTO roomDTO)  {

		try {

			Room room = roomService.findById(roomDTO.getId());

			roomMapper.castToRoom(room, roomDTO);

			roomMapper.mapToDTO(roomService.createRoom(room));

			return ResponseEntity.status(HttpStatus.OK)
					.body(room);

		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Um erro ocorreu ao processar a solicitação");
		}
	}

	@DeleteMapping ("/{id}")
	@ApiOperation(value = "Deleta uma sala.")
	public ResponseEntity<Serializable> delete(@PathVariable("id") Long id) {

		try {

			roomService.deleteById(id);

			return ResponseEntity.status(HttpStatus.OK).build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Sala não encontrada");
		}
	}
}
