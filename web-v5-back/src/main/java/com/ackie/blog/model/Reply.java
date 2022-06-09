//package com.ackie.blog.model;
//
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//public class Reply {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(nullable = false, length = 200)
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "boardId")
//    private Board board;
//
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;
//
//    @CreationTimestamp
//    private LocalDateTime createDateTime;
//}
