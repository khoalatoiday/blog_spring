package com.learn.blog.controller;

import com.learn.blog.entity.Post;
import com.learn.blog.payload.CommonBody;
import com.learn.blog.payload.PostDto;
import com.learn.blog.payload.PostResponse;
import com.learn.blog.service.PostService;
import com.learn.blog.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(
        name = "Bearer Authorization"
)
@Tag(
        name = "CRUD API FOR POSTS"
)
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(
            summary = "Create Post Api",
            description = "create post then save in db",
            method = "post"
    )
    @ApiResponse(
            description = "create success",
            responseCode = "201"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PostResponse> findAll(@RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_RECORD_PER_PAGE, required = false) int pageSize, @RequestParam(name = "pageNo", defaultValue = Constants.DEFAULT_CURRENT_PAGE, required = false) int pageNo, @RequestParam(name = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(name = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir) {
        return new ResponseEntity<PostResponse>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<PostDto>(postService.getPostById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateById(@Valid @RequestBody PostDto body, @PathVariable("id") Long id) {
        PostDto postResponse = postService.updatePostById(id, body);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        if (postService.deleteById(id)) {
            return new ResponseEntity<String>(String.format("Delete post with %s succesfully", id), HttpStatus.OK);
        }
        return new ResponseEntity<String>(String.format("Delete post with %s failed", id), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all/{categoryId}")
    public ResponseEntity<List<PostDto>> getListPostByCategoryId(@PathVariable(name = "categoryId") Long categoryId, CommonBody form) {
        List<Post> posts = postService.getListPostByCategoryId(categoryId, form);
        List<PostDto> res = posts.stream().map(post -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
