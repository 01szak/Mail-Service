package mailService.kninit.Repository;

import mailService.kninit.Entitie.EmailTemplate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, ObjectId> {
}
