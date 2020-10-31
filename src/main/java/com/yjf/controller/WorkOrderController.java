package com.yjf.controller;

import com.github.pagehelper.PageInfo;
import com.yjf.entity.Result;
import com.yjf.entity.WorkOrder;
import com.yjf.services.DetailsService;
import com.yjf.services.TransferService;
import com.yjf.services.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 余俊锋
 * @date 2020/10/28 11:33
 * @Description
 */
@Controller
@RequestMapping("workOrder")
public class WorkOrderController {
    @Autowired
    WorkOrderService workOrderService;
    @Autowired
    DetailsService detailsService;
    @Autowired
    TransferService transferService;

    @RequestMapping(value = "toIndex")
    public String toIndex(){
        return "/work/company/work.html";
    }


    @RequestMapping(value = "selectPage/{pageNum}/{pageSize}",method = RequestMethod.GET)
    @ResponseBody
    public Result selectPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @RequestParam Map<String,String> map){
        Result result = new Result();
        PageInfo<WorkOrder> pageInfo = workOrderService.selectPage(pageNum, pageSize, map);
        result.setObj(pageInfo);
        return result;
    }

    @RequestMapping(value = "toDetail")
    public String toDetail(){
        return "/work/work-detail.html";
    }

    @RequestMapping(value = "toPrint/{id}")
    public String toPrint(@PathVariable Integer id){
        System.out.println(id);
        return "/print.html";
    }

    @RequestMapping(value = "doDetail/{id}")
    @ResponseBody
    public Result doDetail(@PathVariable  Integer id){
        Result result = new Result();
        Map<String, Object> work = workOrderService.selectById(id);
        List<Map<String, String>> details = detailsService.selectDetail(id);
        List<Map<String, String>> transfers = transferService.selectTransfer(id);
        work.put("details",details);
        work.put("transfers",transfers);
        result.setObj(work);
        return result;
    }

}
