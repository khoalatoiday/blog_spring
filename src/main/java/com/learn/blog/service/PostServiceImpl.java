package com.learn.blog.service;

import com.learn.blog.entity.Category;
import com.learn.blog.entity.Post;
import com.learn.blog.exception.ResourceNotFoundException;
import com.learn.blog.payload.CategoryDto;
import com.learn.blog.payload.CommonBody;
import com.learn.blog.payload.PostDto;
import com.learn.blog.payload.PostResponse;
import com.learn.blog.repository.CategoryRepository;
import com.learn.blog.repository.PostRepository;
import com.learn.blog.utils.CommonFn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    // dependency injection
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        CategoryDto categoryDto = categoryService.getCategoryById(postDto.getCategoryId());
        Category category = modelMapper.map(categoryDto, Category.class);
        Post newPost = mapToPostEntity(postDto);
        newPost.setCategory(category);
        postRepository.save(newPost);
        PostDto postResponse = mapToPostDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Sort sort = CommonFn.commonSort(sortBy, sortDir);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> postsPage = postRepository.findAll(pageable);

        List<Post> listOfPosts = postsPage.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postsPage.getNumber());
        postResponse.setPageSize(postsPage.getSize());
        postResponse.setTotalElements(postsPage.getTotalElements());
        postResponse.setTotalPages(postsPage.getTotalPages());
        postResponse.setLast(postsPage.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
            "Post", "id", id
        ));
        return mapToPostDto(post);
    }

    @Override
    public PostDto updatePostById(Long id, PostDto body){
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "id", id
        ));

        if(body.getCategoryId() != null){
            CategoryDto categoryDto = categoryService.getCategoryById(body.getCategoryId());
            Category category = modelMapper.map(categoryDto, Category.class);
            post.setCategory(category);
        }


        if(body.getContent() != null) {
            post.setContent(body.getContent());
        }

        if(body.getDescription() != null) {
            post.setDescription(body.getDescription());
        }

        if(body.getTitle() != null){
            post.setTitle(body.getTitle());
        }
        postRepository.save(post);

        return mapToPostDto(post);
    }

    @Override
    public Boolean deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Post", "id", id
        ));
        postRepository.delete(post);
        return true;
    }

    @Override
    public List<Post> getListPostByCategoryId(Long categoryId, CommonBody commonBody) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        Sort sort = CommonFn.commonSort(commonBody.getSortBy(), commonBody.getSortDir());
        Pageable pageable = PageRequest.of(commonBody.getPageNumber(), commonBody.getPageSize(), sort);
        List<Post> posts = postRepository.findAllByCategoryId(categoryId, pageable);
        return posts;
    }

    private PostDto mapToPostDto(Post post){
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToPostEntity(PostDto postDto){
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        post.setId(postDto.getId());
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}
