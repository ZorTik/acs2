package me.zort.acs.plane.domain.user;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.mapper.UserMapper;
import me.zort.acs.plane.api.domain.user.CreateUserArgs;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.data.user.model.UserDocument;
import me.zort.acs.plane.data.user.repository.MongoUserRepository;
import me.zort.acs.plane.domain.user.event.UserCreatedEvent;
import me.zort.acs.plane.domain.user.event.UserDeletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final MongoUserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public User createUser(CreateUserArgs args) throws IllegalArgumentException {
        UserDocument document = userMapper.toPersistence(args);
        document = userRepository.save(document);

        User user = userMapper.toDomain(document);
        eventPublisher.publishEvent(new UserCreatedEvent(user));

        return user;
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);

        eventPublisher.publishEvent(new UserDeletedEvent(id));
    }

    @Override
    public Optional<? extends User> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}
