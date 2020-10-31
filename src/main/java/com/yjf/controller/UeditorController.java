package com.yjf.controller;

import com.baidu.ueditor.ActionEnter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjf.entity.Result;
import com.yjf.entity.Statute;
import com.yjf.services.StatuteService;
import com.yjf.utils.FTPUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 余俊锋
 * @date 2020/10/29 14:00
 * @Description
 */
@Controller
@RequestMapping("ueditor")
public class UeditorController {
    @Autowired
    FTPUtil ftpUtil;
    @Value("${staticPath}")
    String staticPath;
    @Value("${uploadPath}")
    String uploadPath;
    @Value("${ftpHost}")
    String ftpHost;
    @Value("${ftpPort}")
    int ftpPort;
    @Value("${ftpUsername}")
    String ftpUsername;
    @Value("${ftpPassword}")
    String ftpPassword;
    @Value("${ftpUploadPath}")
    String ftpUploadPath;

    @Autowired
    StatuteService statuteService;

    @RequestMapping(value = "getUeditor")
    public String getUeditor(){
        return "/statute/test.html";
    }


    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Statute statute){
        int i = statuteService.updateByPrimaryKeySelective(statute);
        Result result = new Result();
        if (i>0){
            return result;
        }else {
            result.setSuccess(false);
            result.setMsg("修改失败");
        }
        return null;
    }


    @RequestMapping(value = "controller.php")
    @ResponseBody
    public String doUeditor(String action, MultipartFile upfile, HttpServletRequest request){
        String exec="";
        if (Objects.equals("config",action)){
             exec = new ActionEnter(request, request.getServletContext().getRealPath("/")).exec();
        }else if (Objects.equals("uploadimage",action)){
            String filename = upfile.getOriginalFilename();
            try {
                File file = new File(uploadPath, filename);
                upfile.transferTo(new File(uploadPath,filename));
                FTPClient ftp = ftpUtil.getFTPClient(ftpHost, ftpPort, ftpUsername,ftpPassword);
                ftpUtil.uploadFile(ftp,file.getAbsolutePath(),ftpUploadPath);
                ftpUtil.closeFTP(ftp);
              //  System.exit(0);
                String type = filename.substring(filename.lastIndexOf("."));
                //type=>.jpg
                exec= new ObjectMapper().writeValueAsString(resultMap("SUCCESS", filename, upfile.getSize(), filename, type, staticPath + filename));
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    exec=  new ObjectMapper().writeValueAsString(resultMap("FAIL", null, 0, filename, null,  null));
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
            }

        }
            return exec;
    }


    private Map<String,Object> resultMap(String state,String original,long size,String title,String type,String url){
        Map<String, Object> map = new HashMap<>();
        map.put("state",state);
        map.put("original",original);
        map.put("size",size);
        map.put("title",title);
        map.put("type",type);
        map.put("url",url);
        return map;
    }
}
