package com.ackie.blog.service;

import com.ackie.blog.model.Board;
import com.ackie.blog.model.User;
import com.ackie.blog.repository.BoardRepository;
import com.ackie.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Board> findAll() {
        return boardRepository.findByIsdeleted(false);
    }

    public Board save(Board board, String username) {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

    public Board fineOne(@PathVariable Long id) {
        return boardRepository.findById(id)
                .orElse(null);
    }

    public Board editBoard(Board newBoard, Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return boardRepository.save(newBoard);
                });
    }

    public Board isDeletedBoard(Board newBoard, Long id) {
        newBoard.setId(id);
        newBoard.setIsdeleted(true);
        return boardRepository.save(newBoard);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
