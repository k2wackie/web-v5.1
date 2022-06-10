package com.ackie.blog.repository;

import com.ackie.blog.model.Board;
import com.ackie.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO blog.reply(user_id, board_id, content, create_date_time) VALUES(?1,?2,?3,now())", nativeQuery = true)
    Integer replySave(Long userId, Long boardId, String content);


    List<Reply> findByIsdeletedAndBoard_id(Boolean isdeleted, Long board_id);

    @Modifying
    @Transactional
    @Query(value="UPDATE blog.reply SET content = ?2 WHERE (id = ?1);", nativeQuery = true)
    Integer replyEdit(Long id, String content);

    void findById(Long id);

    @Modifying
    @Transactional
    @Query(value="UPDATE blog.reply SET isdeleted = true WHERE (id = ?1);", nativeQuery = true)
    Integer isdeleted(Long id);
}
