package com.example.reditexampleprojectbe.model;

import com.example.reditexampleprojectbe.model.enums.VoteType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
