package com.xinzuo.competitive.exception;


import lombok.Getter;

@Getter
public class CompetitiveException extends RuntimeException {

    private Integer code;




    public CompetitiveException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CompetitiveException(String message) {

        super(message);
    }




}
