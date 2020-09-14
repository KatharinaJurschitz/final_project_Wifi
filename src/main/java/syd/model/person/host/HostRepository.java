package syd.model.person.host;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface HostRepository extends CrudRepository <Host, Long> {
    public abstract List<Host> findByName(String name);
    public abstract Host findByid(long id);
}
