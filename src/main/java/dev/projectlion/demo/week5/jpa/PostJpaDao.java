package dev.projectlion.demo.week5.jpa;

import dev.projectlion.demo.week5.jpa.entity.PostEntity;
import dev.projectlion.demo.week5.jpa.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class PostJpaDao {
    private static final Logger logger = LoggerFactory.getLogger(PostJpaDao.class);
    private final PostRepository postRepository;

    public  PostJpaDao(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public void createPost(PostJpaDto dto){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setWriter(dto.getWriter());
        postEntity.setBoardEntity(null);
        this.postRepository.save(postEntity);
    }

    public PostEntity readPost(int id){
        Optional<PostEntity> postEntity = this.postRepository.findById((long)id);
        if(postEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return postEntity.get();
    }

    public Iterator<PostEntity> readPostAll(){
        return this.postRepository.findAll().iterator();
    }

    public void updatePost(int id, PostJpaDto dto){
        Optional<PostEntity> targetEntity = this.postRepository.findById((long) id);
        if(targetEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        PostEntity postEntity = targetEntity.get();
        postEntity.setTitle(dto.getTitle() == null ? postEntity.getTitle() : dto.getTitle());
        postEntity.setContent(dto.getContent() == null ? postEntity.getContent() : dto.getContent());
        postEntity.setWriter(dto.getWriter() == null ? postEntity.getWriter() : dto.getWriter());
        this.postRepository.save(postEntity);
    }

    public void deletePost(int id){
        Optional<PostEntity> targetEntity = this.postRepository.findById((long)id);
        if(targetEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        this.postRepository.delete(targetEntity.get());
    }
}
