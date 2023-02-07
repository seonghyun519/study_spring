package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.BoardRequestDTO;
import com.sparta.hanghaeboard.service.BoardService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor //파라미터 없는 기본생성자 생성
public class Board extends Timestamped{
    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String pwd;

    public Board(BoardRequestDTO requestDTO){
        logger.info("Board Entity Method(1) 실행");
        this.username = requestDTO.getUsername();
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.pwd = requestDTO.getPwd();
    }
    public void update(BoardRequestDTO boardRequestDTO){
        logger.info("Board Entity Method(2)업데이트 실행");
        this.username = boardRequestDTO.getUsername();
        this.title = boardRequestDTO.getTitle();
        this.content = boardRequestDTO.getContent();
        this.pwd = boardRequestDTO.getPwd();
    }
}
