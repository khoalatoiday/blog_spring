package com.learn.blog.controller;

import com.learn.blog.payload.CommentDto;
import com.learn.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> findByPostId(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(commentService.findByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> findById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        return new ResponseEntity<>(commentService.findByPostIdAndId(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentDto requestComment){
        return new ResponseEntity<>(commentService.updateByPostIdAndId(postId, commentId, requestComment), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteById(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        commentService.deleleById(postId, commentId);
        return new ResponseEntity<>("Delete comment succesfully!", HttpStatus.OK);
    }
}
