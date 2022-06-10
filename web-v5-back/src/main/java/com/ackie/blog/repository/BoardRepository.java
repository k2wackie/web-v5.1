package com.ackie.blog.repository;

import com.ackie.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByIsdeleted(Boolean isdeleted);

//    @Modifying
//    @Transactional
//    @Query(value="select * from blog.board a right join blog.reply b on (b.board_id=a.id and a.isdeleted=false and b.isdeleted=false ) where a.isdeleted=false;", nativeQuery = true)
//    List<Board> findNotDeleted();
}
