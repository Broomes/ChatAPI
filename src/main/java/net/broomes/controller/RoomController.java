package net.broomes.controller;

import net.broomes.model.Room;
import net.broomes.repository.RoomRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path="/rooms")
    public ResponseEntity<List> getRooms(){
        return new ResponseEntity<>(roomRepository.findAll(),  new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path="/room/{roomName}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomName){
        return new ResponseEntity<>(roomRepository.findById(roomName).get(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path="/room")
    public ResponseEntity<Room> saveRoom(@RequestBody Room room){
        return new ResponseEntity<>(roomRepository.saveAndFlush(room), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(path="/room")
    public ResponseEntity<String> deleteRoom(@RequestBody String roomName){

//        Converts @RequestBody JSON String into a string containing only roomName
        roomName = new JSONObject(roomName).getString("roomName");
        roomRepository.deleteById(roomName);
        return new ResponseEntity<>(roomName + " Deleted", new HttpHeaders(), HttpStatus.OK);
    }
}
