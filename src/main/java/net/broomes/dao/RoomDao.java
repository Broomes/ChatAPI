package net.broomes.dao;

import net.broomes.model.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao {

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
