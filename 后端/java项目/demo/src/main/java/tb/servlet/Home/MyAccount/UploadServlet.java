package tb.servlet.Home.MyAccount;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tb.service.Impl.CsServiceImpl;
import tb.service.Impl.CuServiceImpl;
import tb.util.myJwt;

@WebServlet("/uploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
        JSONObject jsonObject = new JSONObject();
        Integer id = null;
        try {
            List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            for (FileItem item : multiparts) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    if(name.equals("token")){
                        String token = item.getString();
                        myJwt mj = new myJwt(token);
                        if(mj.judgeToken()){
                            id = (Integer) mj.getValue("id");
                        }else{
                            throw new Exception("token失效");
                        }
                    }
                }
            }
            for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                    String uniquePrefix = UUID.randomUUID().toString() + "_";
                    // 获取项目的static目录路径
                    String staticDir = request.getServletContext().getRealPath("") + File.separator + "static";
                    String fileName = staticDir + File.separator + uniquePrefix +item.getName();

                    item.write(new File(fileName));

                    Map<String,Object> map = new HashMap<>();
                    map.put("cu_id",id);
                    map.put("cu_img",fileName);
                    //成功后，将图片路径和账号对应起来
                    new CuServiceImpl().update(map);

                }
            }
            jsonObject.put("status", true);
            jsonObject.put("msg",null);
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("msg",e.getMessage());
        }

        response.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
        response.setStatus(200);
    }

}