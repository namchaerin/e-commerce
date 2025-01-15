package kr.hhplus.be.server.domain.User;

import kr.hhplus.be.server.common.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @DisplayName("사용자 등록 여부 확인 테스트 - 성공")
    @Test
    void testValidateUser_success() {

        //given
        User user = new User("사용자1");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        User result = userService.validateUser(1L);

        //then
        assertThat(result.getName()).isEqualTo("사용자1");

    }

    @DisplayName("사용자 등록 여부 확인 테스트 - 실패")
    @Test
    void testValidateUser_notFound() {

        //given
        User user = new User("사용자1");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when


        //then
        assertThrows(ResourceNotFoundException.class,
                () -> userService.validateUser(2L));

    }


}