package com.learn.blog.payload;

import com.learn.blog.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        name = "post dto"
)
public class PostDto {
    @Schema(
            name = "post id"
    )
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post's title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post's description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private Long categoryId;
}
