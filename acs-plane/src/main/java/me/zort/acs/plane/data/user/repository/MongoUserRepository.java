package me.zort.acs.plane.data.user.repository;

import me.zort.acs.plane.data.user.model.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<UserDocument, Long> {
}
