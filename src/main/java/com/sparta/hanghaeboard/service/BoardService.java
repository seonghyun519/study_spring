package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.BoardRequestDTO;
import com.sparta.hanghaeboard.dto.BoardResponseDTO;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository; //이렇게 하면 일단 메모레포지토리를 사용할 수 있다고 이해? //객체 생성?
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    //게시글 작성 서비스
    @Transactional
    public Board createBoard(BoardRequestDTO boardRequestDTO) { //데이터 베이스에 연결하여 저장하려면 Entity어노테이션이 걸려있는 Memo클래스를 인스턴스로 만들어서 그값을 사용해서 저장해야함
        logger.info("BoardService createBoard method 동작");
        Board board = new Board(boardRequestDTO); //받아온값 객체 생성해서 넣어 주고
        boardRepository.save(board); //레포지토리 save 함수를 통해 데이터 베이스에 저장
        return board;
    }
    //게시글 리스트에 담아서 뷰에 뿌리는 서비스
    @Transactional(readOnly = true) //import org.springframework.transaction.annotation.Transactional; 이전 import에서 적용안되서 바꿈..
    public List<BoardResponseDTO> getBoards() { //반환타입 체크
        List<Board> list = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDTO> boardResponseDTO = new ArrayList<>();
        for (Board board: list){
            boardResponseDTO.add(new BoardResponseDTO(board));
        }
        logger.info("데이터베이스에 데이터를 뷰에 뿌려주기 위해 리스트에 담아 뿌려주는 서비스");
//        return memoRepository.findAll(); //가장 최근에 저장된순으로 보여줘야하기 때문에 findAll() 사용하지 않고 다른방식으로 불러와야함 레포지토리에서 설정해줘야함.
        //list에 담는거는 레포지토리에서 함수 구성
        return boardResponseDTO;
        //레포지토리에 생성한 함수?? 불러옴
    }
    
    //상세페이지
    @Transactional(readOnly = true) //import org.springframework.transaction.annotation.Transactional; 이전 import에서 적용안되서 바꿈..
    public BoardResponseDTO getDetailBoard(Long id) { //반환타입 체크
        Board board = boardRepository.findById(id).orElseThrow(
                () ->new IllegalArgumentException("해당 아이디의 게시글이 존재하지 않습니다.")
        );
        logger.info("상세페이지 board값: "+board.getId() + "/     " + board.getTitle());
        logger.info("상세보기");
        BoardResponseDTO boardResponseDTO =new BoardResponseDTO(board);
        return boardResponseDTO;
    }

    //비밀번호 확인 후 수정 서비스
    @Transactional
    public BoardResponseDTO update(Long id,BoardRequestDTO boardRequestDTO) {
        logger.info("메모서비스 id:" + id+"requestDto"+ boardRequestDTO);
        Board board = boardRepository.findById(id).orElseThrow(
                () ->new IllegalArgumentException("아이디가 존재하지 않습니다.")
        ); //확인절차 후 오류가 발생하지 않으면 아래 동작
        if (board.getPwd().equals(boardRequestDTO.getPwd())){
            logger.info("비밀번호 확인 완료");
            board.update(boardRequestDTO);
        }else {
            logger.info("비밀번호 틀림");
            new IllegalArgumentException("비밀번호가 틀렸습니다.");
            logger.info(board.getPwd());
            logger.info(boardRequestDTO.getPwd());
        }
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board);
        return boardResponseDTO;
    }
    //삭제 서비스
    @Transactional
    public Long deleteBoard(Long id, BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.findById(id).orElseThrow(
                () ->new IllegalArgumentException("아이디가 존재하지 않습니다.")
        ); //확인절차 후 오류가 발생하지 않으면 아래 동작
        if (board.getPwd().equals(boardRequestDTO.getPwd())) {
            logger.info("비밀번호 확인 완료");
            boardRepository.deleteById(id); //클라이언트에서 받아온 id 매개변수 넣어줌
        }else {
            logger.info("비밀번호 틀림");
            new IllegalArgumentException("비밀번호가 틀렸습니다.");
            logger.info(board.getPwd());
            logger.info(boardRequestDTO.getPwd());
        }
        return id; //받아온 id를 그대로 리턴
    }
}