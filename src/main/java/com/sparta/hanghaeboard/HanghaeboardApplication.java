package com.sparta.hanghaeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //생성일자 수정일자 Configuration 어노테이션을 통해 JPA 에서 auditing 을 가능하게 하는 어노테이션 //'audit'는 심사,감시하다는 뜻
@SpringBootApplication
public class HanghaeboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeboardApplication.class, args);
    }

}
