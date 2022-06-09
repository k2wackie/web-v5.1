package com.ackie.blog.repository;

import com.ackie.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByIsdeleted(Boolean isdeleted);
}
