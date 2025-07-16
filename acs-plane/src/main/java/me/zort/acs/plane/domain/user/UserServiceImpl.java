package me.zort.acs.plane.domain.user;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.data.user.model.UserDocument;
import me.zort.acs.plane.data.user.repository.MongoUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final MongoUserRepository userRepository;
    private final DomainModelMapper<User, UserDocument> userMapper;

    @Override
    public Optional<? extends User> getUserById(long id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}
