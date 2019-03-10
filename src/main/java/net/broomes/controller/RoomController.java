package net.broomes.controller;

import net.broomes.model.Room;
import net.broomes.service.RoomService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping(path="/rooms")
    public ResponseEntity<List> getRooms(){
        return roomService.getRooms();
    }

    @GetMapping(path="/room/{roomName}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomName){
        return roomService.getRoom(roomName);
    }

    @PostMapping(path="/room")
    public ResponseEntity<String> saveRoom(@RequestBody Room room){
        return roomService.saveRoom(room);
    }

    @DeleteMapping(path="/room")
    public ResponseEntity<String> deleteRoom(@RequestBody String roomName){

//        Converts @RequestBody JSON String into a string containing only roomName
        roomName = new JSONObject(roomName).getString("roomName");
        return roomService.deleteRoom(roomName);
    }
}
