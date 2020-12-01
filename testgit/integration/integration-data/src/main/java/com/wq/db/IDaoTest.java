package com.wq.db;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-01 11:59
 */
@Repository
public interface IDaoTest {

    @Select("select * from b_sp_good_coupon")
    List<Map<String, String>> getData();
}
