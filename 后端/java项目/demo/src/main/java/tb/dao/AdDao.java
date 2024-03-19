package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Ad;

import java.util.List;
import java.util.Map;

//单个/多个查询
//精准/模糊查询.默认是精准查询，如果是模糊查询就在方法名后加Fuzzy
//在Dao里不涉及业务逻辑，假设参数是正确有效的

public interface AdDao {

    List<Ad> SelectAdByMap(Map<String,Object> map);

    Ad SelectAdById(Integer id);

    Ad SelectAdByName(String name);

    void InsertAd(@Param("ad") Ad ad);


    void DeleteAdById(Integer id);

    void UpdateAd(@Param("ad") Ad ad);//调用时根据ad 里的 ad_id属性来锁定

}
