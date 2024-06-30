package com.learn.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products"
    , schema = "learn_spring_boot_my_blog",
        uniqueConstraints = {
            @UniqueConstraint(name = "SKU_UNIQUE", columnNames = "SKU"),
                @UniqueConstraint(name = "NAME_UNIQUE", columnNames = "NAME")
        }
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "")
    private Long id;

    @Column(name = "SKU", unique = true)
    private String sku;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "DATE_CREATED")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(name = "DATE_UPDATED")
    @UpdateTimestamp
    private LocalDateTime dateUpdated;

}
