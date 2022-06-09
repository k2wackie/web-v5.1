package com.ackie.blog.repository;

import com.ackie.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO blog.reply(user_id, board_id, content, create_date_time) VALUES(?1,?2,?3,now())", nativeQuery = true)
    Integer replySave(Long userId, Long boardId, String content);


}
