package mailService.kninit.Repository;

import mailService.kninit.Entitie.Schedule;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, ObjectId> {
}
