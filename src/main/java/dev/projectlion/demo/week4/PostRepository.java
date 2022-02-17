package dev.projectlion.demo.week4;

import java.util.List;

public interface PostRepository {
    List<PostDto> findAll();
    PostDto findById(int id);
    boolean save(PostDto dto);
    boolean delete(int id);
    boolean update(int id, PostDto dto);
}
