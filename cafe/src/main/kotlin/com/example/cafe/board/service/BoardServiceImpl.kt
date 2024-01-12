package com.example.cafe.board.service

import com.example.cafe.board.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl (
    private val boardRepository: BoardRepository,
) : BoardService {

}