package com.example.cafe.cafe.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CafeRepository : JpaRepository<CafeEntity, Long>{
    @Modifying
    @Transactional
    @Query(
        """
        UPDATE cafe c SET c.memberCnt = c.memberCnt + 1 WHERE c.id = :cafeId
    """
    )
    fun incrementMemberCnt(cafeId: Long)
}
