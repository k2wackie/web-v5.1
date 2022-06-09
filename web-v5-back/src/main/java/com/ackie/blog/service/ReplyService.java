package com.ackie.blog.service;

import com.ackie.blog.model.Reply;
import com.ackie.blog.model.User;
import com.ackie.blog.repository.BoardRepository;
import com.ackie.blog.repository.ReplyRepository;
import com.ackie.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
