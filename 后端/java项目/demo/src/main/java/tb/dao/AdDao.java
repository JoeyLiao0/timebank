package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Ad;

import java.sql.Date;
import java.util.List;

//单个/多个查询
//精准/模糊查询.默认是精准查询，如果是模糊查询就在方法名后加Fuzzy
//在Dao里不涉及业务逻辑，假设参数是正确有效的

public interface AdDao {

    /**
     * 根据编号查询单个Ad
     */
    public Ad SelectAdById(Integer id);

    /**
     * 根据账号查询单个Ad
     */
    public Ad SelectAdByName(String name);

    /**
     * 根据手机号查询单个Ad
     */
    public Ad SelectAdByTel(String tel);

    /**
     * 根据上次登录时间区间查询多个Ad
     */
    public List<Ad> SelectAdByLogin(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     * 根据注册时间区间查询多个Ad
     */
    public List<Ad> SelectAdByRegister(@Param("begintime") Date begintime , @Param("endtime") Date endtime);

    /**
     * 根据账号查询多个Ad（模糊）
     */
    public List<Ad> SelectAdByNameFuzzy(String name);

    /**
     * 根据手机号查询多个Ad（模糊）
     */
    public List<Ad> SelectAdByTelFuzzy(String tel);

    /**
     * 增加单个实例
     */
    public void InsertAd(@Param("ad") Ad ad);


    /**
     * 根据编号删除单个Ad
     */
    public void DeleteAdById(Integer id);

    /**
     * 根据账号删除单个Ad
     */
    public void DeleteAdByName(String name);

    /**
     * 根据手机号删除单个Ad
     */
    public void DeleteAdByTel(String tel);

    /**
     * 更新Ad
     */
    public void UpdateAd(@Param("ad") Ad ad);//调用时根据ad 里的 ad_id属性来锁定

}
