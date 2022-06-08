package com.ackie.blog.repository;

import com.ackie.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Transactional
    List<Board> findByIsdeleted(Boolean isdeleted);
}
