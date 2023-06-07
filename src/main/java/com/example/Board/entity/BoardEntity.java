package com.example.Board.entity;

import com.example.Board.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="board_table")
@Entity
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String username;
    @Column
    private String boardTitle;
    @Column(length = 500)
    private String boardContents;
    @Column
    private int boardHits;
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CommentEntity> commentEntityList = new ArrayList<>();
    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setUsername(boardDTO.getUsername());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }
    public static BoardEntity toUpdateEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }
}
