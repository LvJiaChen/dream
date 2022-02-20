package com.dream.web.application.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.entity.WmsDeliver;
import com.dream.common.vo.WmsDeliverDetailVo;
import com.dream.common.vo.WmsDeliverPageVo;
import com.dream.common.vo.WmsEntryDetailVo;
import com.dream.service.IWmsDeliverService;
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
 * @since 2022-01-25
 */
@Controller
@RequestMapping("/wms-deliver")
@ResponseBody
public class WmsDeliverController {
    @Autowired
    private IWmsDeliverService deliverService;

    /**
     * 查询入库表数据
     *
     * @param
     * @return
     */
    @PostMapping("/queryDeliverListPage")
    public Result queryDeliverListPage(@RequestBody Map param) {
        IPage<WmsDeliverPageVo> deliverPageVoIPage= deliverService.queryDeliverListPage(param);
        return Result.ok(deliverPageVoIPage);
    }

    /**
     * 保存出库单
     *
     * @param
     * @return
     */
    @PostMapping("/saveDeliver")
    public Result saveDeliver(@RequestBody Map param){
        deliverService.saveDeliver(param);
        return Result.ok();
    }


    /**
     * 查询入库单物料明细
     *
     * @param
     * @return
     */
    @PostMapping("/queryDeliverDetail")
    public Result queryDeliverDetail(@RequestBody Map param) {
        List<WmsDeliverDetailVo> deliverDetailVos= deliverService.queryDeliverDetail(param);
        return Result.ok(deliverDetailVos);
    }
}
