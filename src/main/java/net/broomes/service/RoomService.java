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

    public ResponseEntity<Room> saveRoom(Room room){
        if(doesTheRoomAlreadyExist(room.getRoomName())){
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return roomDao.saveRoom(room);
    }

    public ResponseEntity<String> deleteRoom(String roomName) {
        if(doesTheRoomAlreadyExist(roomName)){
            return roomDao.deleteRoom(roomName);
        }
        else {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
