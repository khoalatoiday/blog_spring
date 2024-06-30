package com.learn.blog.service;

import com.learn.blog.entity.Comment;
import com.learn.blog.entity.Post;
import com.learn.blog.exception.BlogApiException;
import com.learn.blog.exception.ResourceNotFoundException;
import com.learn.blog.payload.CommentDto;
import com.learn.blog.repository.CommentRepository;
import com.learn.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CommentDto mapToCommentDto(Comment comment) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    public Comment mapToComment(CommentDto commentDto){
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
        return mapToCommentDto(comment);
    }

    @Override
    public List<CommentDto> findByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        List<Comment> listComments = commentRepository.findByPost(post);
        return listComments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto findByPostIdAndId(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post!");
        }

        return mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateByPostIdAndId(Long postId, Long id, CommentDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("comment", "id", id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post");
        }

        if(request.getEmail() != null){
            comment.setEmail(request.getEmail());
        }

        if(request.getName() != null){
            comment.setName(request.getName());
        }

        if(request.getBody() != null){
            comment.setBody(request.getBody());
        }
        commentRepository.save(comment);
        return mapToCommentDto(comment);
    }

    @Override
    public void deleleById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment doesn't belong to post");
        }

        commentRepository.delete(comment);
    }
}
