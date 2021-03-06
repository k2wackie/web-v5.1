package com.ackie.blog.controller;

import com.ackie.blog.config.jwt.JwtProperties;
import com.ackie.blog.model.Board;
import com.ackie.blog.model.Reply;
import com.ackie.blog.service.ReplyService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/board/{id}/reply")
    public Long save(@RequestBody Reply newReply, @PathVariable Long id, HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        return replyService.save(newReply, id, username);
    }

    @PutMapping("/board/{id}/reply")
    public Long edit(@RequestBody Reply newReply) {
        return replyService.edit(newReply);
    }

    @GetMapping("/board/{id}/reply")
    public List<Reply> find(@PathVariable Long id) {
        return replyService.find(id);
    }

    @PutMapping("/reply/delete")
    public int isDeleted(@RequestBody Board newBoard) {
        return replyService.isDeleted(newBoard);
    }


    @DeleteMapping("/reply/delete")
    public void delete(@RequestBody Reply newReply) {
        replyService.delete(newReply);
    }
}
