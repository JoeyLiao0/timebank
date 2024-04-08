package tb.dao;

import org.apache.ibatis.annotations.Param;

import tb.entity.Cu;

import java.util.List;
import java.util.Map;

public interface CuDao {

    List<Cu> SelectAll();
    List<Cu> SelectCuByMap(Map<String,Object> map);

    Cu SelectCuById(Integer id);

    Cu SelectCuByName(String name);

    void InsertCu(@Param("cu") Cu cu);

    void DeleteCuById(Integer id);

    void UpdateCu(@Param("cu") Cu cu);//调用时根据cu 里的 cu_id属性来锁定

    void IssueCoin(Integer coinNum);
}
