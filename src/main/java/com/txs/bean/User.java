package com.txs.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

  private long id;
  private String userName;
  private String password;
  private String phone;
  private String email;
  private long isActive;
  private Date createTime;
  private Date updateTime;

}
