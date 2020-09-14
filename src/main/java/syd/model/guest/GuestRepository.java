package syd.model.guest;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GuestRepository extends CrudRepository <Guest, Long> {
    //public abstract List<Guest> findByType(String type);
    public abstract Guest findByid(long id);
    public abstract List<Guest> findByName(String name);
}
