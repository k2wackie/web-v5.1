package com.ackie.blog.controller;

import com.ackie.blog.config.jwt.JwtProperties;
import com.ackie.blog.model.Board;
import com.ackie.blog.service.BoardService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public List<Board> findAll() {
        return boardService.findAll();
    }

    @PostMapping("/board")
    public Board newBoard(@RequestBody Board newBoard, HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        return boardService.save(newBoard, username);
    }

    @GetMapping("/board/{id}")
    public Board findOne(@PathVariable Long id) {
        return boardService.fineOne(id);
    }

    @PutMapping("/board/{id}")
    public Board editBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardService.editBoard(newBoard, id);
    }

    @PutMapping("/board/delete/{id}")
    public Board isDeletedBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return boardService.isDeletedBoard(newBoard, id);
    }

    @DeleteMapping("/board/delete/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }

}
