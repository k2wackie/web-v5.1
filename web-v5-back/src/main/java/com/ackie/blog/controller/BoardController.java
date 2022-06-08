package com.ackie.blog.controller;

import com.ackie.blog.config.jwt.JwtProperties;
import com.ackie.blog.model.Board;
import com.ackie.blog.model.User;
import com.ackie.blog.repository.BoardRepository;
import com.ackie.blog.service.BoardService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardRepository repository;

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public List<Board> all() {
        return repository.findByIsdeleted(false);
    }

    @PostMapping("/board")
    public Board newBoard(@RequestBody Board newBoard, HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        return boardService.save(newBoard, username);
    }

    @DeleteMapping("/board/{id}")
    public void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/board/{id}")
    public Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @PutMapping("/board/delete/{id}")
    public Board isdeleteBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        newBoard.setId(id);
        newBoard.setIsdeleted(true);
        return repository.save(newBoard);
    }

    @GetMapping("/board/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

}
