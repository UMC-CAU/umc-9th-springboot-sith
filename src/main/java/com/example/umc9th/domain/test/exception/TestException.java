package com.example.umc9th.domain.test.exception;

import com.example.umc9th.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import org.aspectj.weaver.ast.Test;


public class TestException extends GeneralException {
    public TestException(BaseErrorCode code){
        super(code);
    }
}
