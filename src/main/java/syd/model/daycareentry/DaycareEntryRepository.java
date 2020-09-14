package syd.model.daycareentry;

import org.springframework.data.repository.CrudRepository;
import syd.model.guest.Guest;
import syd.model.person.host.Host;
import syd.model.person.owner.Owner;
import java.util.List;

public interface DaycareEntryRepository extends CrudRepository <DaycareEntry, Long> {
    public abstract DaycareEntry findByid(long id);
    public abstract List<DaycareEntry> findByOwner(Owner owner);
    public abstract List<DaycareEntry> findByHost(Host host);
    public abstract List<DaycareEntry> findByGuest(Guest guest);
    public abstract List<DaycareEntry> findByStatus(String status);
}
