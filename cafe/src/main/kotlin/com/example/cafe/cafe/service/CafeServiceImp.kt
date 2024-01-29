package com.example.cafe.cafe.service

import com.example.cafe.cafe.repository.CafeRepository
import org.springframework.stereotype.Service

@Service
class CafeServiceImpl (
    private val cafeRepository: CafeRepository
) : CafeService {
    override fun get(): Cafe {
        val cafe = cafeRepository.findById(1).get()

        return Cafe(
            cafeName = cafe.name,
            createdAt = cafe.createdAt.toString(),
            memberCnt = cafe.memberCnt
        )
    }
}
