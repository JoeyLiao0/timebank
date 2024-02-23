package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Cu;

import java.sql.Date;
import java.util.List;

public interface CuDao {

    /**
     *
     * 查询全部Cu
     *
     */
    public List<Cu> SelectAllCu();

    /**
     *
     * 根据编号查询单个Cu
     *
     */
    public Cu SelectCuById(Integer id);

    /**
     *
     * 根据账号查询单个Cu
     *
     */
    public Cu SelectCuByName(String name);

    /**
     *
     * 根据手机号查询单个Cu
     *
     */
    public Cu SelectCuByTel(String tel);

    /**
     *
     * 根据上次登录时间区间查询多个Cu
     *
     */
    public List<Cu> SelectCuByLogin(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     *
     * 根据注册时间区间查询多个Cu
     *
     */
    public List<Cu> SelectCuByRegister(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     *
     * 根据发布任务个数区间查询多个Cu
     *
     */
    public List<Cu> SelectCuByRelease(@Param("begin") Integer begin,@Param("end") Integer end);//TODO

    /**
     *
     * 根据发布任务个数区间查询多个Cu
     *
     */
    public List<Cu> SelectCuByAccept(@Param("begin") Integer begin,@Param("end") Integer end);//TODO

    /**
     *
     * 根据时间币个数区间查询多个Cu
     *
     */
    public List<Cu> SelectCuByIcon(@Param("begin") Integer begin,@Param("end") Integer end);//TODO

    /**
     *
     * 根据账号查询多个Cu（模糊）
     *
     */
    public List<Cu> SelectCuByNameFuzzy(String name);

    /**
     *
     * 根据手机号查询多个Cu（模糊）
     *
     */
    public List<Cu> SelectCuByTelFuzzy(String tel);

    /**
     *
     * 增加单个实例
     *
     */
    public void InsertCu(@Param("cu") Cu cu);


    /**
     *
     * 根据编号删除单个Cu
     *
     */
    public void DeleteCuById(Integer id);

    /**
     *
     * 根据账号删除单个Cu
     *
     */
    public void DeleteCuByName(String name);

    /**
     *
     * 根据手机号删除单个Cu
     *
     */
    public void DeleteCuByTel(String tel);

    /**
     *
     * 更新Cu
     *
     */
    public void UpdateCu(@Param("cu") Cu cu);//调用时根据cu 里的 cu_id属性来锁定
}
