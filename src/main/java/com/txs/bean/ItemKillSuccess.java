package com.txs.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemKillSuccess {

  private String code;
  private long itemId;
  private long killId;
  private String userId;
  private long status;
  private Date createTime;


}
