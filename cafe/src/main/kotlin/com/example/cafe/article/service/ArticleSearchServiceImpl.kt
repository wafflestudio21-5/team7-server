package com.example.cafe.article.service

import com.example.cafe.article.repository.ArticleEntity
import com.example.cafe.article.repository.ArticleRepository
import com.example.cafe.board.service.Board
import com.example.cafe.comment.repository.CommentRepository
import com.example.cafe.user.repository.UserEntity
import com.example.cafe.user.service.User
import org.jsoup.Jsoup
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.min

@Service
class ArticleSearchServiceImpl(
    val articleRepository : ArticleRepository,
    val commentRepository: CommentRepository
    ) : ArticleSearchService {
    override fun search(
        item: String,
        boardId: Long?,
        searchCategory: Long,
        startDate: String,
        endDate: String,
        wordInclude: String,
        wordExclude: String,
        pageable: Pageable
    ): Page<ArticleBrief> {
        val startDateInDate = LocalDateTime.parse(startDate)
        val endDateInDate = LocalDateTime.parse(endDate)

        val wordList = item.split(',',' ').map{it.trim()}.filter{it.isNotEmpty()}
        val wordIncludeList = wordInclude.split(',',' ').map{it.trim()}.filter{it.isNotEmpty()}
        val wordExcludeList = wordExclude.split(',',' ').map{it.trim()}.filter{it.isNotEmpty()}

        val articleFound = wordList.map{word -> findItem(word,searchCategory)}.flatten().toSet()
        val articleExclude = wordExcludeList.map{word -> findItem(word,searchCategory)}.flatten().toSet()
        val articleFoundFiltered = if(wordInclude == ""){
            articleFound.filter{article->
                !articleExclude.contains(article)
            }.toList()
        }else{
            val articleInclude = wordIncludeList.map{word -> findItem(word,searchCategory).toSet()}
            val articleIncludeIntersection = articleInclude.reduce{acc, set -> acc.intersect(set)}.toList()
            (articleIncludeIntersection.intersect(articleFound) - articleExclude).toList()
        }
        val result = articleFoundFiltered.filter {article ->
            article.createdAt.isAfter(startDateInDate) && article.createdAt.isBefore(endDateInDate) && (boardId?.equals(article.board.id)?:true)
        }.toMutableList()
        result.sortByDescending{it.createdAt}
        val startIndex = pageable.pageNumber * pageable.pageSize
        val endIndex = min(startIndex + pageable.pageSize, result.size)
        return PageImpl(result.subList(startIndex, endIndex), pageable, result.size.toLong())
    }
    private fun findItem(item: String, searchCategory: Long): List<ArticleBrief>{
        return when(searchCategory){
            1L -> findArticlesByTitleOrContentOrComment(item)
            2L -> findArticlesByTitle(item)
            3L -> findArticlesByAuthor(item)
            4L -> findArticlesByComment(item)
            5L -> findArticlesByCommentWriter(item)
            else -> throw BadCategoryException()
        }
    }
    private fun findArticlesByTitleOrContentOrComment(item: String): List<ArticleBrief> {
        val titleMatches = articleRepository.findByTitleContaining(item)
        val contentMatches = articleRepository.findByContentContaining(item).filter{article->
            val document = Jsoup.parse(article.content)
            val textContent = document.text()
            textContent.contains(item)
        }
        val commentMatches = commentRepository.findByContentContaining(item).map{comment -> comment.article}.distinct()
        return  (titleMatches+contentMatches+commentMatches).distinct().map{article ->
            ArticleBrief(article)
        }
    }
    private fun findArticlesByTitle(item: String): List<ArticleBrief>{
        return articleRepository.findByTitleContaining(item).map{article ->
            ArticleBrief(article)
        }
    }
    private fun findArticlesByAuthor(item: String): List<ArticleBrief>{
        return articleRepository.findByUser_NicknameContaining(item).map{article ->
            ArticleBrief(article)
        }
    }
    private fun findArticlesByComment(item: String): List<ArticleBrief> {
        return commentRepository.findByContentContaining(item).map{comment ->
            ArticleBrief(comment.article)
        }.distinct()
    }
    private fun findArticlesByCommentWriter(item: String): List<ArticleBrief>{
        return commentRepository.findByUser_NicknameContaining(item).map{comment ->
            ArticleBrief(comment.article)
        }.distinct()
    }
    fun ArticleBrief(entity: ArticleEntity) = ArticleBrief(
        id = entity.id,
        title = entity.title,
        createdAt = entity.createdAt,
        viewCount = entity.viewCnt,
        likeCount = entity.likeCnt,
        commentCount = entity.commentCnt,
        author = User(entity.user),
        board = Board(id = entity.board.id, name = entity.board.name),
        isNotification = entity.isNotification,
    )
    fun User(entity: UserEntity) = User(
        id = entity.id,
        nickname = entity.nickname,
        registerDate = entity.registerDate,
        email = entity.email,
        rank = entity.rank,
        visitCount = entity.visitCount,
        articlesCount = entity.articlesCount,
        commentsCount = entity.commentsCount
    )

}