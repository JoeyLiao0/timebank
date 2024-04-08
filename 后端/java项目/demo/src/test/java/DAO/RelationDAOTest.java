package DAO;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tb.dao.AdDao;
import tb.dao.RelationDao;
import tb.entity.Ad;
import tb.entity.Relation;
import tb.util.mySqlSession;

import java.sql.Timestamp;
import java.util.List;

public class RelationDAOTest {
    @Test
    public void TestInsert(){
        try(SqlSession session = mySqlSession.getSqSession()){
            RelationDao relationDao = session.getMapper(RelationDao.class);

            Relation relation = new Relation();
            relation.setCu_id(3);
            relation.setCs_id(2);
            relationDao.Insert(relation);

            System.out.println(relation.getId());

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestSelect(){
        try(SqlSession session = mySqlSession.getSqSession()){
            RelationDao relationDao = session.getMapper(RelationDao.class);


            List<Relation> relations = relationDao.Select(2,null);

            for(Relation relation : relations){
                System.out.println(relation.getId());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestDelete(){
        try(SqlSession session = mySqlSession.getSqSession()){
            RelationDao relationDao = session.getMapper(RelationDao.class);



            relationDao.Delete(3);

            session.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
