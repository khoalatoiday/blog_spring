package com.learn.blog.service;

import com.learn.blog.entity.Post;
import com.learn.blog.payload.CommonBody;
import com.learn.blog.payload.PostDto;
import com.learn.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePostById(Long id, PostDto body);

    Boolean deleteById(Long id);

    List<Post> getListPostByCategoryId(Long categoryId, CommonBody commonBody);
}
