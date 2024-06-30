package com.learn.blog.payload;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonBody {
    private int pageSize = 10;
    private int pageNumber = 0;
    private String sortBy = "id";
    private String sortDir = "acs";
}
