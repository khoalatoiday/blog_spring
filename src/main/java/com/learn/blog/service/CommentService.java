package com.learn.blog.service;

import com.learn.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> findByPostId(Long postId);

    CommentDto findByPostIdAndId(Long postId, Long id);

    CommentDto updateByPostIdAndId(Long postId, Long id, CommentDto request);

    void deleleById(Long postId, Long commentId);
}
