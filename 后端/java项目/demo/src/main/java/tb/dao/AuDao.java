package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Au;
import java.util.List;
import java.util.Map;

public interface AuDao {

    List<Au> SelectAuByMap(Map<String,Object> map);

    Au SelectAuById(Integer id);

    Au SelectAuByName(String name);

    void InsertAu(@Param("au") Au au);

    void DeleteAuById(Integer id);

    void UpdateAu(@Param("au") Au au);//调用时根据au 里的 au_id属性来锁定

}
