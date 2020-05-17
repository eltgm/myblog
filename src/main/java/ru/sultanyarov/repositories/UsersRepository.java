package ru.sultanyarov.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sultanyarov.models.User;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByName(String name);
}
