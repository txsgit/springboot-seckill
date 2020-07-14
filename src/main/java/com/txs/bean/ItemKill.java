package com.txs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemKill {

  private long id;
  private long itemId;
  private long total;
  private Date startTime;
  private Date endTime;
  private long isActive;
  private Date createTime;
  private Integer version;
  //采用服务器时间控制是否可以进行抢购
  private Integer canKill;
  private String itemName;

}
