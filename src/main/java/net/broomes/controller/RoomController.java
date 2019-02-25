package net.broomes.controller;

import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api")
public class RoomController {

    private static Logger logger = LoggerFactory.getLogger(RoomController.class);

    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Room.class)
            .buildSessionFactory();

    @GetMapping("/rooms")
    public List<Room> getRooms(){
        Session session = factory.getCurrentSession();
        try {
            return getRooms(session);
        } catch (Exception e){
            System.out.println(e);
            return null;
        } finally {
            session.close();
        }
    }

    @GetMapping("/room/{roomName}")
    public Room getRoom(@PathVariable String roomName){
        Session session = factory.getCurrentSession();
        try {
            Room room = getRoom(session, roomName);
            System.out.println(room.toString());
            return room;
        } catch (Exception e){
            System.out.println(e);
            return null;
        } finally {
            session.close();
        }
    }

    @PutMapping("/room/{roomName}/{roomDesc}")
    public void saveRoom(@PathVariable String roomName, @PathVariable String roomDesc){
        Session session = factory.getCurrentSession();
        Room room = new Room(roomName, roomDesc);
        try {
            saveRoom(session, room);
        } catch (Exception e){
            System.out.println(e);
        } finally {
            session.close();
        }
    }

    @DeleteMapping("/room/{roomName}")
    public void saveRoom(@PathVariable String roomName){
        Session session = factory.getCurrentSession();
        try {
            deleteRoom(session, roomName);
        } catch (Exception e){
            System.out.println(e);
        } finally {
            session.close();
        }
    }

    public void saveRoom(Session session, Room room){
        session.beginTransaction();
        session.save(room);
        session.getTransaction().commit();
    }

    public Room getRoom(Session session, String roomName){
        session.beginTransaction();
        Room theRoom = session.get(Room.class, roomName);
        session.getTransaction().commit();
        return theRoom;
    }

    public List<Room> getRooms(Session session){
        session.beginTransaction();
        List<Room> rooms = session.createQuery("from Room").getResultList();
        session.getTransaction().commit();
        return rooms;
    }

    public void deleteRoom(Session session, String roomName){
        session.beginTransaction();
        Query query = session.createQuery("delete from Room where roomName= :roomName");
        query.setParameter("roomName", roomName);
        query.executeUpdate();
        session.getTransaction().commit();
    }

}
