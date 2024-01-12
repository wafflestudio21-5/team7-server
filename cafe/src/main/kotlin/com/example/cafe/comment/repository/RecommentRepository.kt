package com.example.cafe.comment.repository

import org.springframework.data.jpa.repository.JpaRepository

interface RecommentRepository : JpaRepository<RecommentEntity, Long> {
}
