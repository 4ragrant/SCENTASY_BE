package fouragrant.scentasy.biz.perfume.repository;

import fouragrant.scentasy.biz.perfume.domain.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {
    List<Perfume> findByMemberId(Long memberId);
    int countByMemberId(Long memberId);
    @Query(value = "SELECT recipe_array FROM perfume WHERE perfume_id = :perfumeId", nativeQuery = true)
    String findRecipeArrayByPerfumeId(Long perfumeId);
    @Query(value = """
        SELECT pa.accord AS accordName, COUNT(*) AS count
        FROM perfume_accords pa
        JOIN perfume p ON pa.perfume_id = p.perfume_id
        WHERE p.member_id = :memberId
        GROUP BY pa.accord
        ORDER BY count DESC
    """, nativeQuery = true)
    List<Object[]> findAccordStatisticsByMemberId(@Param("memberId") Long memberId);
}
