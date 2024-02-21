package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Cs;
import tb.entity.Cs;

import java.sql.Date;
import java.util.List;

public interface CsDao {

    /**
     * 根据编号查询单个Cs
     */
    public Cs SelectCsById(Integer id);

    /**
     * 根据账号查询单个Cs
     */
    public Cs SelectCsByName(String name);

    /**
     * 根据手机号查询单个Cs
     */
    public Cs SelectCsByTel(String tel);

    /**
     * 根据上次登录时间区间查询多个Cs
     */
    public List<Cs> SelectCsByLogin(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     * 根据注册时间区间查询多个Cs
     */
    public List<Cs> SelectCsByRegister(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     * 根据账号查询多个Cs（模糊）
     */
    public List<Cs> SelectCsByNameFuzzy(String name);

    /**
     * 根据手机号查询多个Cs（模糊）
     */
    public List<Cs> SelectCsByTelFuzzy(String tel);

    /**
     * 增加单个实例
     */
    public void InsertCs(@Param("cs") Cs cs);


    /**
     * 根据编号删除单个Cs
     */
    public void DeleteCsById(Integer id);

    /**
     * 根据账号删除单个Cs
     */
    public void DeleteCsByName(String name);

    /**
     * 根据手机号删除单个Cs
     */
    public void DeleteCsByTel(String tel);

    /**
     * 更新Cs
     */
    public void UpdateCs(@Param("cs") Cs cs);//调用时根据cs 里的 cs_id属性来锁定
}
