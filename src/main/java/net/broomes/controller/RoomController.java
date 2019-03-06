package net.broomes.controller;

import net.broomes.dao.RoomDao;
import net.broomes.model.Room;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class RoomController {

    private RoomDao roomDao;

    @Autowired
    public RoomController(RoomDao roomDao){
        this.roomDao = roomDao;
    }

    @GetMapping(path="/rooms")
    public ResponseEntity<List> getRooms(){
        return roomDao.getRooms();
    }

    @GetMapping(path="/room/{roomName}")
    public ResponseEntity<Room> getRoom(@PathVariable String roomName){
        return roomDao.getRoom(roomName);
    }

    @PostMapping(path="/room")
    public ResponseEntity<Room> saveRoom(@RequestBody Room room){
        return roomDao.saveRoom(room);
    }

    @DeleteMapping(path="/room")
    public ResponseEntity<String> deleteRoom(@RequestBody String roomName){

//        Converts @RequestBody JSON String into a string containing only roomName
        roomName = new JSONObject(roomName).getString("roomName");
        return roomDao.deleteRoom(roomName);
    }
}
