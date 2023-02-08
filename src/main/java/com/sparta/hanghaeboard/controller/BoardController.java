package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.BoardRequestDTO;
import com.sparta.hanghaeboard.dto.BoardResponseDTO;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor //final이 붙거나@NotNull이 붙은 필드의 생성자를 자동생성해주는 어노테이션
public class BoardController {
    private final BoardService boardService;
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @GetMapping("/")
    public ModelAndView home() { //ModelAndView 객체를 생성하여 데이터를 넣을 수 있고 템플리스에 반환할 html 파일 이름도 설정할 수 있음.
        return new ModelAndView("index");
    }
    @GetMapping("/api/board") //게시글 뷰
    public List<BoardResponseDTO> getBoard() {
        logger.info("메모 리스트에 담아 view에 뿌려주는 컨트롤러 동작");
        return boardService.getBoards();
    }
    //게시글 id 상세뷰
    @GetMapping("/api/board/{id}")
    public BoardResponseDTO getDetailBoard(@PathVariable Long id) {
        logger.info("상세페이지");
        return boardService.getDetailBoard(id);
    }
    @PostMapping("/api/board") //게시글 생성 api
    public Board createBoard(@RequestBody BoardRequestDTO boardRequestDTO) { //html 바디에 데이터 받아온다?
        logger.info("메모 생성 api 컨트롤러 동작");
        return boardService.createBoard(boardRequestDTO);//데이터와 연결하는 서비스 부분을 개발/BoardService클래스에 createBoard 메서드 개발
    }
    @PutMapping("/api/board/{id}") //수정 api
    public BoardResponseDTO updateMemo(@PathVariable Long id, @RequestBody BoardRequestDTO boardRequestDTO) {//@PathVariable값을 받아오고 수정 내용을 받기 위해 @RequestBody를 통해 Body부분의 데이터를 가져오고 username과 contents 두개뿐이니 앞서 만든 RequestDto를 그대로 사용
        logger.info("수정 컨트롤러 동작");
        logger.info("컨트롤러 수정 데이터" + String.valueOf(boardRequestDTO));
        return boardService.update(id, boardRequestDTO); // 보드서비스 클래스에 update 메서드에 id 값 필요하며 어떤값으로 변경되는지 알기 위해 requestDto 파라미터 두개(@RequestBody 바디 가져오기)
    }
    @DeleteMapping("/api/board/{id}/{pwd}") //ajax //$표시 ``백틱 없음
    public Long deleteMemo(@PathVariable Long id, @RequestBody BoardRequestDTO boardRequestDTO) {
        logger.info("삭제 컨트롤러 동작");
        logger.info("삭제 데이터" + String.valueOf(id));
        return boardService.deleteBoard(id, boardRequestDTO);
    }
}
