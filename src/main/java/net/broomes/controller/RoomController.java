package net.broomes.controller;

import net.broomes.dao.RoomDao;
import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private SessionFactory factory;

    @GetMapping(path="/rooms")
    public List<Room> getRooms(){
        Session session = factory.getCurrentSession();
        RoomDao roomDao = new RoomDao();
        try {
            List<Room> rooms = roomDao.getRooms(session);
            session.close();
            return rooms;
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @GetMapping(path="/room/{roomName}")
    public Room getRoom(@PathVariable String roomName){
        Session session = factory.getCurrentSession();
        RoomDao roomDao = new RoomDao();
        try {
            Room room = roomDao.getRoom(session, roomName);
            session.close();
            return room;
        } catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @PostMapping(path="/room")
    public ResponseEntity<String> saveRoom(@RequestBody Room newRoom){
        Session session = factory.getCurrentSession();
        Room room = new Room(newRoom.getRoomName(), newRoom.getRoomDesc());
        RoomDao roomDao = new RoomDao();
        try {
            roomDao.saveRoom(session, room);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/room")
    public ResponseEntity<String> deleteRoom(@RequestBody String roomName){
        Session session = factory.getCurrentSession();

//        Converts @RequestBody JSON String into a string containing only roomName
        roomName = new JSONObject(roomName).getString("roomName");

        RoomDao roomDao = new RoomDao();

        try {
            roomDao.deleteRoom(session, roomName);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
