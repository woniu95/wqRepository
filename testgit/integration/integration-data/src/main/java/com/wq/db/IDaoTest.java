package com.wq.db;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

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


    @Insert("<script>"
            +"insert into ${tb}"
            +"<foreach collection=\"fields\" item=\"field\"   separator = \",\" open='(' close=')'> "
            +"`${field}`"
            +"</foreach>"
            +"values"+
            "<foreach collection=\"list\" item=\"mvo\"  separator = \",\" >"+
            "<foreach collection=\"mvo\" item=\"val\" open='(' close=')' separator = \",\">#{val}</foreach>"
            +"</foreach>"
            + "</script>")
    int insertBatch(@Param("list")List<List<Object>> rowList, @Param("fields") List<String> columns, @Param("tb") String table) throws Exception;

}
