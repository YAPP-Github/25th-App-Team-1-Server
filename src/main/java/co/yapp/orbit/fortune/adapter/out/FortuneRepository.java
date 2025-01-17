package co.yapp.orbit.fortune.adapter.out;

import co.yapp.orbit.fortune.domain.Fortune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FortuneRepository extends JpaRepository<Fortune, Long> {

}
