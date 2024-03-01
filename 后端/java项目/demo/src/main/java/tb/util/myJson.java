package tb.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class myJson {
    public Map<String,Object> getMap(HttpServletRequest req) throws IOException {
        // 从请求中获取JSON字符串
        StringBuilder jsonString = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        }

        // 使用fastjson解析JSON字符串为Java对象
        Map<String, Object> dataMap = JSON.parseObject(jsonString.toString(), new TypeReference<Map<String, Object>>() {});

        return dataMap;
    }

}
