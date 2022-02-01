package com.dream.web.application.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsWarehouse;
import com.dream.common.mapper.wms.WmsFoodCalorieMapper;
import com.dream.service.IWmsWarehouseService;
import com.dream.web.application.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-24
 */
@Controller
@RequestMapping("/wms-food")
@ResponseBody
public class WmsFoodController {
    @Autowired
    private IWmsWarehouseService iWmsWarehouseService;

    @Autowired
    private WmsFoodCalorieMapper wmsFoodCalorieMapper;

    @PostMapping("/queryWarehouseList")
    public Result queryWarehouseList(@RequestBody Map param) throws Exception {
        Map map = new HashMap();
        int pageIndex = (Integer) param.get("pageIndex");
        if (pageIndex > 1) {
            pageIndex = pageIndex * 10;
        }
        param.put("pageIndex", pageIndex);
        map.put("count", wmsFoodCalorieMapper.countByExample(null));
        map.put("result", iWmsWarehouseService.queryFoodCalorie(param));
        return Result.ok(map);
    }
}
