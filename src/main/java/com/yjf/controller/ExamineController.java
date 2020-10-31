package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Examine;
import com.yjf.entity.Result;
import com.yjf.services.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/27 15:41
 * @Description
 */
@Controller
@RequestMapping("examine")
public class ExamineController {
    @Autowired
    ExamineService examineService;

    @RequestMapping("toIndex")
    public String toIndex(){
        return "/examine/index.html";
    }

    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public Result selectPage(@PathVariable  Integer pageNum,@PathVariable Integer pageSize,@RequestParam Map<String,String> map){
        Result result = new Result();
        PageInfo<Examine> pageInfo = examineService.selectPage(pageNum, pageSize, map);
        result.setObj(pageInfo);
        return result;
    }


}
