package com.example.cafe.board.repository

import jakarta.persistence.*
import java.util.Collections.emptyList

@Entity(name = "board_groups")
class BoardGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @OneToMany(mappedBy = "group")
    val boards: List<BoardEntity> = emptyList(),
)
