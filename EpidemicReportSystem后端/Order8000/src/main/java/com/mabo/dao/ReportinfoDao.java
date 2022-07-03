package com.mabo.dao;

import com.mabo.entity.Reportinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Reportinfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-29 22:54:00
 */
@Repository
public interface ReportinfoDao {
    List<Reportinfo>isReportToday(@Param("reportDate")String reportDate,@Param("uname")String  uname);
    /**
     * 上报历史
     */
    List<Reportinfo> getStudentReportHistory(@Param("id")String id);

    /**
     * 新增数据
     *
     * @param reportinfo 实例对象
     * @return 影响行数
     */
    int insert(Reportinfo reportinfo);


    /**
     * 修改数据
     *
     * @param reportinfo 实例对象
     * @return 影响行数
     */
    int update(Reportinfo reportinfo);

    /**
     * 通过主键删除数据
     * @return 影响行数
     */
    int deleteById(@Param("uameId")String uameId,@Param("reportDate")String reportDate );


}

