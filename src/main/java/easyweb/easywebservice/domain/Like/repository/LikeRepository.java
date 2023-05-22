package easyweb.easywebservice.domain.Like.repository;

import easyweb.easywebservice.domain.Like.model.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Liked, Long> {
    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
