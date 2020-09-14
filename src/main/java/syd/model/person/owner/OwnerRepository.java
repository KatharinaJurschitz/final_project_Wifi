package syd.model.person.owner;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    //public abstract List<Owner> findByName(Owner owner);
    //public abstract Owner findByid(long id);
    public abstract Owner findByUsername (String username);
}
