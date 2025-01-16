package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.hhplus.be.server.common.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(USER_NOT_FOUND));
    }

    public boolean checkUserExistence(Long userId) {
        return userRepository.findById(userId).isPresent();
    }

}
