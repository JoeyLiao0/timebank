package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Au;
import tb.entity.Au;

import javax.swing.event.ListDataEvent;
import java.sql.Date;
import java.util.List;

public interface AuDao {

    /**
     *
     * 查询全部Au
     *
     */
    public List<Au> SelectAllAu();

    /**
     *
     * 根据编号查询单个Au
     *
     */
    public Au SelectAuById(Integer id);

    /**
     *
     * 根据账号查询单个Au
     *
     */
    public Au SelectAuByName(String name);

    /**
     *
     * 根据手机号查询单个Au
     *
     */
    public Au SelectAuByTel(String tel);

    /**
     *
     * 根据上次登录时间区间查询多个Au
     *
     */
    public List<Au> SelectAuByLogin(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     *
     * 根据注册时间区间查询多个Au
     *
     */
    public List<Au> SelectAuByRegister(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     *
     * 根据账号查询多个Au（模糊）
     *
     */
    public List<Au> SelectAuByNameFuzzy(String name);

    /**
     *
     * 根据手机号查询多个Au（模糊）
     *
     */
    public List<Au> SelectAuByTelFuzzy(String tel);

    /**
     *
     * 增加单个实例
     *
     */
    public void InsertAu(@Param("au") Au au);


    /**
     *
     * 根据编号删除单个Au
     *
     */
    public void DeleteAuById(Integer id);

    /**
     *
     * 根据账号删除单个Au
     *
     */
    public void DeleteAuByName(String name);

    /**
     *
     * 根据手机号删除单个Au
     *
     */
    public void DeleteAuByTel(String tel);

    /**
     *
     * 更新Au
     *
     */
    public void UpdateAu(@Param("au") Au au);//调用时根据au 里的 au_id属性来锁定
}
