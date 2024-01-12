package com.example.cafe.board.controller

import com.example.cafe.board.service.BoardService
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardController(
    private val boardService: BoardService,
) {

}