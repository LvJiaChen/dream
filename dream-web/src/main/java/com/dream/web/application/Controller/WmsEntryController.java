package com.dream.web.application.Controller;


import com.dream.service.IWmsEntryService;
import com.dream.web.application.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 保存物料
     *
     * @param
     * @return
     */
    @PostMapping("/saveEntry")
    public Result saveEntry(@RequestBody Map param) {
        entryService.saveEntry(param);
        return Result.ok();
    }
}
