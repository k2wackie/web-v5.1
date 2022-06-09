package com.ackie.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "board")
@Data
@DynamicInsert
@DynamicUpdate
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Boolean isdeleted = false;

    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


//    @JsonIgnoreProperties({"board"})
//    @OrderBy("id desc")

    @ToString.Exclude
    @OneToMany(mappedBy = "board", cascade=CascadeType.REMOVE)
//    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();
}
