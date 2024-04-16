package tb.servlet.Home.MyAccount;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


import tb.service.Impl.CuServiceImpl;
import tb.service.Impl.TaskServiceImpl;
import tb.util.myJwt;

@WebServlet("/uploadServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ServletException("Content type is not multipart/form-data");
        }
        JSONObject jsonObject = new JSONObject();
        Integer id = null;
        Integer taskId = null;
        try {

            File temp = new File(request.getSession().getServletContext().getRealPath("/temp"));
            temp.mkdirs();
            System.out.println("建立成功");
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setRepository(temp);

            List<FileItem> multiparts = new ServletFileUpload(diskFileItemFactory).parseRequest(request);


            for (FileItem item : multiparts) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    if (name.equals("token")) {
                        String token = item.getString();
                        myJwt mj = new myJwt(token);
                        if (mj.judgeToken()) {
                            id = Integer.parseInt((String) mj.getValue("id"));
                        } else {
                            throw new Exception("token失效");
                        }
                    } else if (name.equals("taskId")) {
                        taskId = Integer.parseInt(item.getString());
                    }
                }
            }
            for (FileItem item : multiparts) {
                if (!item.isFormField()) {

                    if (item.getFieldName().equals("userImg")) {
                        String uniquePrefix = UUID.randomUUID().toString() + "_";
                        // 获取项目的目录路径
                        String staticDir = request.getServletContext().getRealPath("") + "static";

                        //这个是文件的相对路径，用作数据库存储

                        String FileName = File.separator + "userImg" + File.separator + uniquePrefix + item.getName();

                        //文件的绝对路径，用于文件上传

                        String fileName = staticDir + FileName;

                        System.out.println("文件相对路径 " + FileName);
                        System.out.println("文件绝对路径 " + fileName);


                        item.write(new File(fileName));

                        Map<String, Object> map = new HashMap<>();
                        map.put("cu_id", id);
                        map.put("cu_img", FileName);
                        //成功后，将图片路径和账号对应起来

                        CuServiceImpl cuServiceImpl = new CuServiceImpl();

                        //先查询，查之前的图片路径
                        Map<String, Object> m = cuServiceImpl.selectById(id);

                        String msg = cuServiceImpl.update(map);

                        if (!msg.equals("yes")) {
                            throw new Exception(msg);
                        }

                        String filePath = (String) m.get("userImg");

                        if (!filePath.contains("defaultImg")) {
                            // 转换路径为Path对象
                            Path path = Paths.get(staticDir+filePath);
                            try {
                                // 删除被用户换掉的文件
                                Files.delete(path);
                                System.out.println("文件已成功删除!");
                            } catch (IOException e) {
                                System.err.println("删除文件时发生错误: " + e.getMessage());
                            }
                        }

                    } else if (item.getFieldName().equals("evidenceImg")) {
                        String uniquePrefix = UUID.randomUUID().toString() + "_";
                        // 获取项目的static目录路径
                        String staticDir = request.getServletContext().getRealPath("") + "static" ;

                        String FileName = File.separator + "evidenceImg"+ File.separator + uniquePrefix + item.getName();
                        String fileName = staticDir + FileName;

                        System.out.println("文件相对路径 " + FileName);
                        System.out.println("文件绝对路径 " + fileName);

                        item.write(new File(fileName));

                        //成功后，将图片相对路径和任务对应起来

                        TaskServiceImpl taskServiceImpl = new TaskServiceImpl();

                        String msg = taskServiceImpl.takerFinish(id, taskId, FileName);

                        if (!msg.equals("yes")) {
                            throw new Exception(msg);
                        }

                    }

                }
            }
            jsonObject.put("status", true);
            jsonObject.put("msg", null);
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("msg", e.getMessage());
        }

        response.getWriter().write(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));//这里要注意即使是null值也要返回
        response.setStatus(200);
    }

}