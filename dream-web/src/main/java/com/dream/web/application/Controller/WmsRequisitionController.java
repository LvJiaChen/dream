package com.dream.web.application.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.vo.WmsRequisitionDetailVo;
import com.dream.common.vo.WmsRequisitionNoVo;
import com.dream.common.vo.WmsRequisitionPageVo;
import com.dream.service.IWmsRequisitionService;
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
@RequestMapping("/wms-requisition")
@ResponseBody
public class WmsRequisitionController {
    @Autowired
    private IWmsRequisitionService wmsRequisitionService;


    /**
     * 查询领料单表数据
     *
     * @param
     * @return
     */
    @PostMapping("/queryRequisitionListPage")
    public Result queryRequisitionListPage(@RequestBody Map param) {
        IPage<WmsRequisitionPageVo> requisitionListPage= wmsRequisitionService.queryRequisitionListPage(param);
        return Result.ok(requisitionListPage);
    }

    /**
     * 保存物料
     *
     * @param
     * @return
     */
    @PostMapping("/saveRequisition")
    public Result saveRequisition(@RequestBody Map param) throws Exception {
        wmsRequisitionService.saveRequisition(param);
        return Result.ok();
    }

    /**
     * 查询领料单物料明细
     *
     * @param
     * @return
     */
    @PostMapping("/queryRequisitionDetail")
    public Result queryRequisitionDetail(@RequestBody Map param) {
        List<WmsRequisitionDetailVo> requisitionDetailVos= wmsRequisitionService.queryRequisitionDetail(param);
        return Result.ok(requisitionDetailVos);
    }

    /**
     * 查询领料单号
     *
     * @param
     * @return
     */
    @PostMapping("/queryRequisitionNo")
    public Result queryRequisitionNo(@RequestBody Map param) {
        List<WmsRequisitionNoVo> requisitionDetailVos= wmsRequisitionService.queryRequisitionNo(param);
        return Result.ok(requisitionDetailVos);
    }


    /**
     * 查询领料单号
     *
     * @param
     * @return
     */
    @PostMapping("/queryRequisitionForRequisitionNo")
    public Result queryRequisitionForRequisitionNo(@RequestBody Map param) {
        List<WmsRequisitionDetailVo> requisitionDetailVos= wmsRequisitionService.queryRequisitionForRequisitionNo(param);
        return Result.ok(requisitionDetailVos);
    }
}
