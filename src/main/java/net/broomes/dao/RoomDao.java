package net.broomes.dao;

import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao {

    private SessionFactory sessionFactory;

    @Autowired
    public RoomDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public Room findRoomByName(String roomName){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Room room = session.get(Room.class, roomName);
        session.getTransaction().commit();
        session.close();
        return room;
    }

    public ResponseEntity<Room> getRoom(String roomName){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Room room = session.get(Room.class, roomName);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(room, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<List> getRooms(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Room> rooms = session.createQuery("from Room").getResultList();
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(rooms, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<Room> saveRoom(Room room){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(room);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(room, new HttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteRoom(String roomName){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Room room = session.load(Room.class, roomName);
        session.delete(room);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>("Deleted Successfully", new HttpHeaders(), HttpStatus.OK);
    }

}
