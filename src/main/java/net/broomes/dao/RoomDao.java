package net.broomes.dao;

import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.naming.NameNotFoundException;
import java.util.List;

@Repository
public class RoomDao {

    private static Logger log = LoggerFactory.getLogger(RoomDao.class);
    private SessionFactory sessionFactory;

    @Autowired
    public RoomDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public ResponseEntity<Room> getRoom(String roomName){
        //noinspection Duplicates
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Room room = session.get(Room.class, roomName);
            if(room==null){throw new NameNotFoundException();}
            session.getTransaction().commit();
            session.close();
            return new ResponseEntity<>(room, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.debug(">>>>>>>>>>>>> Exception triggered in RoomDao - getRoom(): ", e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List> getRooms(){
        //noinspection Duplicates
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Room> rooms = session.createQuery("from Room").getResultList();
            if(rooms==null){throw new NameNotFoundException();}
            session.getTransaction().commit();
            session.close();
            return new ResponseEntity<>(rooms, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.debug(">>>>>>>>>>>>> Exception triggered in RoomDao - getRooms(): ", e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Room> saveRoom(Room room){
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(room);
            session.getTransaction().commit();
            session.close();
            return new ResponseEntity<>(room, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.debug(">>>>>>>>>>>>> Exception triggered in RoomDao - saveRoom(): ", e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteRoom(String roomName){
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Room room = session.load(Room.class, roomName);
            session.delete(room);
            session.getTransaction().commit();
            session.close();
            return new ResponseEntity<>("Deleted Successfully", new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e){
            log.debug(">>>>>>>>>>>>> Exception triggered in RoomDao - deleteRoom(): ", e);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

    }

}
