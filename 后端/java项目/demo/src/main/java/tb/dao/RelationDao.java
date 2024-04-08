package tb.dao;

import org.apache.ibatis.annotations.Param;
import tb.entity.Relation;

import java.util.List;

public interface RelationDao {
    //增删查

    void Insert(@Param("relation")Relation relation);

    void Delete(@Param("id") Integer id);

    List<Relation> Select(@Param("cu_id") Integer cu_id, @Param("cs_id") Integer cs_id);

}
