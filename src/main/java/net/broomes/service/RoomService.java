package net.broomes.service;

import net.broomes.dao.RoomDao;
import net.broomes.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao){
        this.roomDao = roomDao;
    }

    private boolean doesTheRoomAlreadyExist(String roomName){
        if(roomDao.findRoomByName(roomName)==null){ return false; }
        else { return true; }
    }

    public ResponseEntity<Room> getRoom(String roomName){
        if(doesTheRoomAlreadyExist(roomName)) {
            return roomDao.getRoom(roomName);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List> getRooms(){
        return roomDao.getRooms();
    }

    public ResponseEntity<String> saveRoom(Room room){
        String roomName = room.getRoomName();
        if(doesTheRoomAlreadyExist(roomName)){
            return new ResponseEntity<>("Room already exists", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        if(!roomName.matches("[a-zA-Z0-9]*")){
            return new ResponseEntity<>("Room name must be only letters and numbers", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        if(roomName.length()<2 || roomName.length()>20){
            return new ResponseEntity<>("Room name must be between 2 - 20 characters.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        if(room.getRoomDesc().length()>=80){
            return new ResponseEntity<>("Room description must be 80 characters or less.", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        room.setRoomName(roomName.substring(0, 1).toUpperCase() + roomName.substring(1));
        return roomDao.saveRoom(room);
    }

    public ResponseEntity<String> deleteRoom(String roomName) {
        if(doesTheRoomAlreadyExist(roomName)){
            return roomDao.deleteRoom(roomName);
        }
        else {
            return new ResponseEntity<>("Room does not exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
