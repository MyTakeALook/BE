package com.takealook.takealook.repository;

import com.takealook.takealook.entity.Board;
import com.takealook.takealook.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardOrderByModifiedAtAsc(Board board); //
}
