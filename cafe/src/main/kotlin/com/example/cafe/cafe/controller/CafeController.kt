package com.example.cafe.cafe.controller

import com.example.cafe.cafe.service.Cafe
import com.example.cafe.cafe.service.CafeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CafeController(
    private val cafeService: CafeService
) {
    @GetMapping("/api/v1/cafe-info")
    fun getCafeInfo(
    ): Cafe {
        return cafeService.get()
    }
}
