package com.example.cafe.comment.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import com.example.cafe.comment.service.CommentService

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    //TODO
}

data class CommentRequest(
    val content: String
    //TODO
)
