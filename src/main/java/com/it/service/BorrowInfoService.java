package com.it.service;

import com.it.domain.BorrowingInformation;
import com.it.vo.DataVo;

/**
 * @author MrMa
 * @version 1.0
 * @description
 */
public interface BorrowInfoService {

    // 根据
    DataVo<BorrowingInformation> getBorrowInfo(String isbn, String bookName);


}
