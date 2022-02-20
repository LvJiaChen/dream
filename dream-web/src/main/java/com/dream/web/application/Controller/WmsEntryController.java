package com.dream.web.application.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dream.common.vo.WmsEntryDetailVo;
import com.dream.common.vo.WmsEntryPageVo;
import com.dream.service.IWmsEntryService;
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
@RequestMapping("/wms-entry")
@ResponseBody
public class WmsEntryController {
    @Autowired
    private IWmsEntryService entryService;


    /**
     * 查询入库表数据
     *
     * @param
     * @return
     */
    @PostMapping("/queryEntryListPage")
    public Result queryEntryListPage(@RequestBody Map param) {
        IPage<WmsEntryPageVo> entryPageVoIPage= entryService.queryEntryListPage(param);
        return Result.ok(entryPageVoIPage);
    }

    /**
     * 保存入库单
     *
     * @param
     * @return
     */
    @PostMapping("/saveEntry")
    public Result saveEntry(@RequestBody Map param){
        entryService.saveEntry(param);
        return Result.ok();
    }

    /**
     * 查询入库单物料明细
     *
     * @param
     * @return
     */
    @PostMapping("/queryEntryDetail")
    public Result queryEntryDetail(@RequestBody Map param) {
        List<WmsEntryDetailVo> entryDetailVos= entryService.queryEntryDetail(param);
        return Result.ok(entryDetailVos);
    }
}
