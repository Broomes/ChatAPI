package net.broomes.service;

import net.broomes.model.Room;
import net.broomes.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public ResponseEntity<Room> getRoom(String roomName){
        if(roomRepository.findById(roomName).isPresent()) {
            return new ResponseEntity<>(roomRepository.findById(roomName).get(), new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List> getRooms(){
        return new ResponseEntity<>(roomRepository.findAll(),  new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<String> saveRoom(Room room){
        String roomName = room.getRoomName();
        if(roomRepository.findById(roomName).isPresent()){
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
        roomRepository.saveAndFlush(room);
        return new ResponseEntity<>(roomName + " saved successfully", new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteRoom(String roomName) {
        if(roomRepository.findById(roomName).isPresent()){
            roomRepository.deleteById(roomName);
            return new ResponseEntity<>(roomName + " Deleted", new HttpHeaders(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Room does not exist", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }
}
