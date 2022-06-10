package com.ackie.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder // 빌더패턴
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    private boolean enabled;
    private String role;
    private Boolean isdeleted = false;
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();

}
