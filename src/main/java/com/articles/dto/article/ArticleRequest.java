package com.articles.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArticleRequest {

    @Size(max = 100)
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String content;


    private LocalDateTime publishDate;

}
