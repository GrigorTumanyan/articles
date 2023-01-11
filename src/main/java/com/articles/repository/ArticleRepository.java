package com.articles.repository;

import com.articles.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT COUNT(id) FROM article.article WHERE publish_date BETWEEN CURDATE() - INTERVAL 7 DAY and now()",
            nativeQuery = true)
    Optional<Long> findArticleCountByWeek();
}
