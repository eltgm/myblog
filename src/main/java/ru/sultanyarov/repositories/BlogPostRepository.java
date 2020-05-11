package ru.sultanyarov.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sultanyarov.models.BlogPost;

public interface BlogPostRepository extends MongoRepository<BlogPost, ObjectId> {
}
