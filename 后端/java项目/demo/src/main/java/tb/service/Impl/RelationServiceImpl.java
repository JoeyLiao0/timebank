package tb.service.Impl;

import org.apache.ibatis.session.SqlSession;

import tb.dao.RelationDao;

import tb.entity.Relation;
import tb.service.RelationService;
import tb.util.mySqlSession;

import java.util.ArrayList;
import java.util.List;

public class RelationServiceImpl implements RelationService {

    public List<Integer> getCu_id(Integer cs_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            RelationDao relationDao = session.getMapper(RelationDao.class);


            List<Relation> relations = relationDao.Select(null, cs_id);

            List<Integer> cu_ids = new ArrayList<>();
            for (Relation relation : relations) {
                cu_ids.add(relation.getCu_id());
            }

            return cu_ids;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Integer getCs_id(Integer cu_id) {
        try (SqlSession session = mySqlSession.getSqSession()) {
            RelationDao relationDao = session.getMapper(RelationDao.class);

            Relation relation = (Relation) relationDao.Select(cu_id, null);

            if (relation == null) {
                //没有记录的话，为他分配当前关联量最小的且有效的客服
                //所以客服表要多一个字段为feedbackNum 表示其目前处理有多个对话

                Integer cs_id = new CsServiceImpl().getCsIdToFeedback();

                if (cs_id != null) {

                    Relation re = new Relation();
                    re.setCs_id(cs_id);
                    re.setCu_id(cu_id);
                    relationDao.Insert(re);
                    session.commit();

                    return cs_id;
                }

                return null;
            }

            return relation.getCs_id();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
