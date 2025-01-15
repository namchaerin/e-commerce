package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User validateUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("등록되지 않은 사용자입니다."));
    }

}
