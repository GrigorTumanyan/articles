package com.articles.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArticleResponse {

    private Long id;

    private String title;

    private String author;

    private String content;

    private LocalDateTime publishDate;
}
