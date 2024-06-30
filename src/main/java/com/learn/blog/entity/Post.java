package com.learn.blog.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Schema(
        name = "post entity"
)
public class Post {
    @Schema(
            name = "posts id"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            name = "posts title"
    )
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(
            name = "posts description"
    )
    @Column(name = "description", nullable = false)
    private String description;

    @Schema(
            name = "posts content"
    )
    @Column(name = "content", nullable = false)
    private String content;

    // một post có nhiều comments.
    @OneToMany(mappedBy = "post", cascade = CascadeType.MERGE, orphanRemoval = true) // post là field kết nối giữa entity Post và Comment (được khai báo bên Comment)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
