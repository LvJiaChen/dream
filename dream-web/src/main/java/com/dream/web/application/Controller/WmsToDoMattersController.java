package com.dream.web.application.Controller;


import com.dream.common.entity.WmsToDoMatters;
import com.dream.service.IWmsToDoMattersService;
import com.dream.web.application.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-21
 */
@Controller
@RequestMapping("/wms-to-do-matters")
@ResponseBody
public class WmsToDoMattersController {
    @Autowired
    private IWmsToDoMattersService toDoMattersService;


    /**
     * 查询代办事项
     *
     * @param
     * @return
     */
    @PostMapping("/queryToDoMattersList")
    public Result queryToDoMattersList(@RequestBody Map param) {
        List<WmsToDoMatters> toDoMattersList= toDoMattersService.queryToDoMattersList(param);
        return Result.ok(toDoMattersList);
    }

    /**
     * 修改代办事项状态
     *
     * @param
     * @return
     */
    @PostMapping("/queryToDoMattersStatus")
    public Result queryToDoMattersStatus(@RequestBody Map param) {
        toDoMattersService.queryToDoMattersStatus(param);
        return Result.ok();
    }


    /**
     * 保存代办事项状态
     *
     * @param
     * @return
     */
    @PostMapping("/saveMatterPost")
    public Result saveMatterPost(@RequestBody Map param) {
        toDoMattersService.saveMatterPost(param);
        return Result.ok();
    }

    /**
     * 查询今日入库出库金额及当前库存
     *
     * @param
     * @return
     */
    @PostMapping("/queryEntryDeliverStockToDate")
    public Result queryEntryDeliverStockToDate(@RequestBody Map param) {
        Map resMap= toDoMattersService.queryEntryDeliverStockToDate(param);
        return Result.ok(resMap);
    }

    /**
     * 查询出入库报表数据
     *
     * @param
     * @return
     */
    @PostMapping("/queryEntryDeliverSchart")
    public Result queryEntryDeliverSchart(@RequestBody Map param) {
        Map resMap= toDoMattersService.queryEntryDeliverSchart(param);
        return Result.ok(resMap);
    }
}
