package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Cs;
import java.util.List;
import java.util.Map;

public interface CsDao {

    List<Cs> SelectCsByMap(Map<String,Object> map);

    Cs SelectCsById(Integer id);

    Cs SelectCsByName(String name);

    void InsertCs(@Param("cs") Cs cs);

    void DeleteCsById(Integer id);

    void UpdateCs(@Param("cs") Cs cs);//调用时根据cs 里的 cs_id属性来锁定

    List<Cs> getMinFeedbackNumCs();

}
