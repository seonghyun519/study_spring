package com.sparta.hanghaeboard.dto;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class BoardRequestDTO {
//    private Long id;
    private String username;
    private String title;
    private String content;
    private String pwd;
}
