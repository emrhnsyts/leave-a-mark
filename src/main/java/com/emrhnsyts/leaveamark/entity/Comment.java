package com.emrhnsyts.leaveamark.entity;

import com.emrhnsyts.leaveamark.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Lob
    @Column(columnDefinition = "text")
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private AppUser user;
    @OneToMany(mappedBy = "comment")
    private List<Like> likes;
}
