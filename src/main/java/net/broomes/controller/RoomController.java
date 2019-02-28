package net.broomes.controller;

import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("api")
public class RoomController {

    private static Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private SessionFactory factory;

    /*
    >>>>>>>>>>>>>>>>>  Controller methods below <<<<<<<<<<<<<<<<<<
     */

    @GetMapping("/rooms")
    public List<Room> getRooms(){
        Session session = factory.getCurrentSession();
        try {
            List<Room> rooms = getRooms(session);
            session.close();
            return rooms;
        } catch (Exception e){
            log.error(e.toString());
            session.close();
            return null;
        }
    }

    @GetMapping("/room/{roomName}")
    public Room getRoom(@PathVariable String roomName){
        Session session = factory.getCurrentSession();
        try {
            Room room = getRoom(session, roomName);
            session.close();
            return room;
        } catch (Exception e){
            log.error(e.toString());
            session.close();
            return null;
        }
    }

    @PutMapping(path="/room", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> saveRoom(@RequestBody Room newRoom){
        Session session = factory.getCurrentSession();
        Room room = new Room(newRoom.getRoomName(), newRoom.getRoomDesc());
        try {
            saveRoom(session, room);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/room", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteRoom(@RequestBody String roomName){

//        Converts @RequestBody JSON String into a string containing only roomName
        roomName = new JSONObject(roomName).getString("roomName");

        Session session = factory.getCurrentSession();
        try {
            deleteRoom(session, roomName);
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.toString());
            session.close();
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    >>>>>>>>>>>>>>>>>  Helper methods below <<<<<<<<<<<<<<<<<<
     */

    private void saveRoom(Session session, Room room){
        session.beginTransaction();
        session.save(room);
        session.getTransaction().commit();
    }

    private Room getRoom(Session session, String roomName){
        session.beginTransaction();
        Room theRoom = session.get(Room.class, roomName);
        session.getTransaction().commit();
        return theRoom;
    }

    private List<Room> getRooms(Session session){
        session.beginTransaction();
        List<Room> rooms = session.createQuery("from Room").getResultList();
        session.getTransaction().commit();
        return rooms;
    }

    private void deleteRoom(Session session, String roomName){
        session.beginTransaction();
        Query query = session.createQuery("delete from Room where roomName= :roomName");
        query.setParameter("roomName", roomName);
        query.executeUpdate();
        session.getTransaction().commit();
    }

}
