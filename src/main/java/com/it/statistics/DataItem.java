package com.it.statistics;

import lombok.Data;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
@Data
public class DataItem {
   private Integer number;       // 数量
   private String categoryName;  // 类名

   public DataItem(Integer number, String categoryName){
      this.number = number;
      this.categoryName = categoryName;
   }
}
