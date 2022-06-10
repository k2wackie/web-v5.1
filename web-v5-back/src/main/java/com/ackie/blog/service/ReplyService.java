package com.ackie.blog.service;

import com.ackie.blog.model.Board;
import com.ackie.blog.model.Reply;
import com.ackie.blog.model.User;
import com.ackie.blog.repository.BoardRepository;
import com.ackie.blog.repository.ReplyRepository;
import com.ackie.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    public Long save(Reply newReply, Long boardId, String username) {
        User user = userRepository.findByUsername(username);
        Long userId = user.getId();
        String content = newReply.getContent();

        return Long.valueOf(replyRepository.replySave(userId, boardId, content));
    }

    public Long edit(Reply newReply) {
        Long id = newReply.getId();
        String content = newReply.getContent();

        return Long.valueOf(replyRepository.replyEdit(id, content));
    }

    public List<Reply> find(Long board_id) {
        Boolean isdeleted = false;

        return replyRepository.findByIsdeletedAndBoard_id(isdeleted, board_id);
    }


    public int isDeleted(Board newBoard) {
        Long id = newBoard.getId();
        return replyRepository.isdeleted(id);

    }

    public void delete(Reply newReply) {
        Integer id = Math.toIntExact(newReply.getId());
        replyRepository.deleteById(id);
    }
}
