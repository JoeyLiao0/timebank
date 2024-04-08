package tb.service;

import java.util.List;

public interface RelationService {

    List<Integer> getCu_id(Integer cs_id);

    Integer getCs_id(Integer cu_id);
}
