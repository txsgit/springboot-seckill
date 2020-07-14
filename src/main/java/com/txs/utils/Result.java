package com.txs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 结果定义
 */
@Data
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object obj;


}
