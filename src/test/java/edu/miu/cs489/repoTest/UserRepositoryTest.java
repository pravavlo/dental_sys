package edu.miu.cs489.repoTest;

import edu.miu.cs489.model.Role;
import edu.miu.cs489.model.User;
import edu.miu.cs489.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername_shouldReturnUser_whenUsernameExists() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("securePassword123");
        user.setRole(Role.OFFICE_MANAGER); // replace with your actual enum value

        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("john_doe");

        assertThat(result)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u.getUsername()).isEqualTo("john_doe");
                    assertThat(u.getRole()).isEqualTo(Role.OFFICE_MANAGER);
                });
    }

    @Test
    void testFindByUsername_shouldReturnEmpty_whenUsernameDoesNotExist() {
        Optional<User> result = userRepository.findByUsername("not_exist");

        assertThat(result).isNotPresent();
    }

    @Test
    void testExistsByUsername_shouldReturnTrue_whenUsernameExists() {
        User user = new User();
        user.setUsername("unique_user");
        user.setPassword("password");
        user.setRole(Role.PATIENT);

        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("unique_user");

        assertThat(exists).isTrue();
    }

    @Test
    void testExistsByUsername_shouldReturnFalse_whenUsernameDoesNotExist() {
        boolean exists = userRepository.existsByUsername("ghost_user");

        assertThat(exists).isFalse();
    }
}
