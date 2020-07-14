package com.txs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  private long id;
  private String name;
  private String code;
  private long stock;
  private Date purchaseTime;
  private long isActive;
  private Date createTime;
  private Date updateTime;



}
