package com.dream.web.application.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsFoodCalorie;
import com.dream.common.entity.WmsMaterial;
import com.dream.service.IWmsFoodCalorieService;
import com.dream.service.IWmsMaterialService;
import com.dream.web.application.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangcaichao
 * @since 2022-02-10
 */
@Controller
@RequestMapping("/wms-food-calorie")
@ResponseBody
public class WmsFoodCalorieController {

    @Autowired
    private IWmsFoodCalorieService iWmsFoodCalorieService;

    /**
     * 查询物料表数据
     *
     * @param
     * @return
     */
    @PostMapping("/queryFoodList")
    public Result queryMaterialList(@RequestBody Map param) {
        IPage<WmsFoodCalorie> foodCalorieIPage = iWmsFoodCalorieService.queryFoodCalorieList(param);
        return Result.ok(foodCalorieIPage);
    }

    /**
     * 保存物料
     *
     * @param
     * @return
     */
    @PostMapping("/saveFood")
    public Result saveMaterial(@RequestBody Map param) {
        iWmsFoodCalorieService.saveFoodCalorie(param);
        return Result.ok();
    }

    /**
     * 删除物料
     *
     * @param
     * @return
     */
    @PostMapping("/deleteFood")
    public Result deleteMaterial(@RequestBody Map param) {
        iWmsFoodCalorieService.deleteFoodCalorie(param);
        return Result.ok();
    }



}
