package ru.sultanyarov.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sultanyarov.models.User;

public interface UsersRepository extends MongoRepository<User, ObjectId> {
}
