package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc(); //findAll/ByOrderBy/ModifiedAt순으로 정렬할거고/Desc 내림차순 정렬

//    List<Board> findByIdAndPwd(String Id, String Pwd);
}
