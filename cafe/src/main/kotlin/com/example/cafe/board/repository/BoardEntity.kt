package com.example.cafe.board.repository

import jakarta.persistence.*

@Entity(name = "boards")
class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: BoardGroupEntity,
    val viewCnt: Long,
    val likeCnt: Long,
)
