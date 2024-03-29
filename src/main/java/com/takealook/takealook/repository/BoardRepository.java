package com.takealook.takealook.repository;

import com.takealook.takealook.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtAsc();
    List<Board> findAllByOrderByModifiedAtAsc();
    Optional<Board> findByBoardId(Long boardId); //
}
