package com.yjf.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.SysArea;
import com.yjf.services.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/30 13:21
 * @Description
 */
@Controller
@RequestMapping("sysArea")
public class SysAreaController {
    @Autowired
    SysAreaService sysAreaService;

    @Value("${uploadPath}")
    String uploadPath;

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestParam Map<String, Object> map) {
        Result result = new Result();
        PageInfo<SysArea> pageInfo = sysAreaService.selectPage(pageNum, pageSize, map);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "downExcelFile/{pageNum}/{pageSize}")
    public void downExcelFile(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestParam Map<String, Object> map, HttpServletResponse response) throws IOException {
        PageInfo<SysArea> pageInfo = sysAreaService.selectPage(pageNum, pageSize, map);
        List<SysArea> sysAreaList = pageInfo.getList();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("区域信息表.xlsx", "UTF-8"));
        sysAreaService.downloadExcel(response.getOutputStream(),sysAreaList);


        /*ExportExcel<SysArea> ee = new ExportExcel();
        String[] headers = {"序号", "上级区域id", "父区域id", "区域编码", "名字", "区域类型", "创建人", "创建时间", "修改人", "修改时间", "备注", "删除标记", "图标"};
        String[] headersName = {"id", "parentId", "parentIds", "code", "name",
                "type", "createBy", "createDate", "updateBy", "updateDate", "remarks", "delFlag", "icon"};
        String fileName = "区域信息表";
        ee.exportExcel(headers, headersName, sysAreaList, fileName, response);*/
    }

    @RequestMapping(value = "uploadExcelFile",method = RequestMethod.POST)
    @ResponseBody
    public Result uploadExcel(MultipartFile file) throws IOException {
        Result result = new Result();
        sysAreaService.uploadExcel(file.getInputStream());
        return result;
    }



    /*@RequestMapping(value = "uploadExcelFile")
    public void uploadExcel(MultipartFile excel) {
        String filename = excel.getOriginalFilename();
        File file = new File(uploadPath, filename);
        try {
            excel.transferTo(file);
            System.out.println("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上面将excel存储起来   也可以不存储
        List<SysArea> sysAreaList=new ArrayList<>();
        try {
            FileInputStream is = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet hs = workbook.getSheetAt(0);//获取第一个excel文件

            int first = hs.getFirstRowNum(); //获取首行  行号
            int last = hs.getLastRowNum();  //获取尾行  行号
            for (int i = (first+1); i < last; i++) {
                XSSFRow row = hs.getRow(i);
                int firstCellNum = row.getFirstCellNum();//获取所在行的第一个行号
                int lastCellNum = row.getLastCellNum();//获取所在行的最后一个行号
                SysArea sysArea = new SysArea();
                Map<Integer, String> map=new HashMap<>();
                for (int j = firstCellNum; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    String value = cell.getStringCellValue();
                    map.put(j,value);
                }
                sysArea.setId(null);
                sysArea.setParentId(Integer.parseInt(map.get(++firstCellNum)));
                sysArea.setParentIds(map.get(++firstCellNum));
                sysArea.setCode(map.get(++firstCellNum));
                sysArea.setName(map.get(++firstCellNum));
                sysArea.setType(map.get(++firstCellNum));
                sysArea.setCreateBy(map.get(++firstCellNum));
                sysArea.setCreateDate(DateUtils.parse(map.get(++firstCellNum)));
                sysArea.setUpdateBy(map.get(++firstCellNum));
                sysArea.setUpdateDate(DateUtils.parse(map.get(++firstCellNum)));
                sysArea.setRemarks(map.get(++firstCellNum));
                sysArea.setDelFlag(map.get(++firstCellNum));
                sysArea.setIcon(map.get(++firstCellNum));
                sysAreaList.add(sysArea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sysAreaService.insertForeach(sysAreaList);

    }*/

    @RequestMapping(value = "doDelete")
    @ResponseBody
    public Result doDelete(Integer id){
        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("1");
        sysArea.setId(id);
        int i = sysAreaService.updateByPrimaryKeySelective(sysArea);
        Result result = new Result();
        if (i>0){
            return  result;
        }
        result.setMsg("删除失败");
        result.setSuccess(false);
        return result;
    }

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "/area/area.html";
    }

    @RequestMapping(value = "toUpdate")
    public String toUpdate() {
        return "/area/update.html";
    }

    @RequestMapping(value = "toAdd")
    public String toAdd() {
        return "/area/add.html";
    }

    @RequestMapping(value = "toSelect")
    public String toSelect() {
        return "/area/select.html";
    }

    @RequestMapping(value = "toModule")
    public String toModule() {
        return "/modules/module.html";
    }

    @RequestMapping(value = "toDetail")
    public String toDetail() {
        return "/area/area-detail.html";
    }

    @RequestMapping("selectAll")
    @ResponseBody
    public Result selectAll() {
        Result result = new Result();
        List<SysArea> sysAreas = sysAreaService.selectAll();
        result.setObj(sysAreas);
        return result;
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    @ResponseBody
    public Result doUpdate(@RequestBody Map<String,Object> map) {
        Result result = new Result();
        Object areaObj = map.get("area");
        ObjectMapper mapper = new ObjectMapper();
        SysArea sysArea=new SysArea();
        try {
            String json = mapper.writeValueAsString(areaObj);
             sysArea = mapper.readValue(json, SysArea.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        map.put("area",sysArea);
        int i = sysAreaService.updateByParentId(map);
        if (i > 0) {
            return result;
        }
        result.setSuccess(false);
        result.setMsg("修改失败");
        return result;

    }
}
