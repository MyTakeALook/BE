package com.takealook.takealook.repository;

import com.takealook.takealook.entity.User;
import com.takealook.takealook.entity.Board;
import com.takealook.takealook.entity.Comment;
import com.takealook.takealook.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserAndBoard(User user, Board board);
    List<Liked> findAllByBoard(Board board);
}
