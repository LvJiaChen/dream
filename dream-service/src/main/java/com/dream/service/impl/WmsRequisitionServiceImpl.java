package com.dream.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsRequisition;
import com.dream.common.mapper.WmsRequisitionMapper;
import com.dream.common.vo.WmsRequisitionDetailVo;
import com.dream.common.vo.WmsRequisitionPageVo;
import com.dream.service.IWmsRequisitionService;
import com.dream.service.IWmsSerialNumberService;
import com.dream.service.utils.ListMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-01-25
 */
@Service
public class WmsRequisitionServiceImpl extends ServiceImpl<WmsRequisitionMapper, WmsRequisition> implements IWmsRequisitionService {
    @Autowired
    private IWmsSerialNumberService iWmsSerialNumberService;
    @Autowired
    private WmsRequisitionMapper wmsRequisitionMapper;

    @Override
    public IPage<WmsRequisitionPageVo> queryRequisitionListPage(Map param) {
        Page<WmsRequisitionPageVo> entryPageVoPage = new Page<>((Integer)param.get("pageIndex") , (Integer)param.get("pageSize") , false);
        entryPageVoPage.setSearchCount(true);
        IPage<WmsRequisitionPageVo> pageVos= wmsRequisitionMapper.queryRequisitionListPage(entryPageVoPage,(String) param.get("code"),(String) param.get("materialName"));
        return pageVos;
    }

    @Override
    public void saveRequisition(Map param) {
        List<WmsRequisition> requisitionList= ListMapUtils.toListBean((List<Map>) param.get("requisitionData"),WmsRequisition.class);
        String code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_requisition");
        requisitionList.forEach(a->{
            //校验物料编码是否为空
            if (StrUtil.isBlank(a.getMaterialNo())){
                throw new RuntimeException("物料编码不能为空");
            }
            //入库数量不能为0校验
            if (a.getQuantity().compareTo(BigDecimal.ZERO)==0){
                throw new RuntimeException("物料："+a.getMaterialNo()+"领料数量不能等于0");
            }
            a.setCode(code);
            a.setRequisitionDate(DateUtil.parse((String) param.get("requisitionDate")));
        });
        this.saveBatch(requisitionList);
    }

    @Override
    public List<WmsRequisitionDetailVo> queryRequisitionDetail(Map param) {
        List<WmsRequisitionDetailVo> requisitionDetail=wmsRequisitionMapper.queryRequisitionDetail(param);
        return requisitionDetail;
    }
}
