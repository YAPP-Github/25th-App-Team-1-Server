package co.yapp.orbit.user.adapter.out;

import co.yapp.orbit.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
