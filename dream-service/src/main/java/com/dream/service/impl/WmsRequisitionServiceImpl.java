package com.dream.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.common.entity.WmsRequisition;
import com.dream.common.mapper.WmsRequisitionMapper;
import com.dream.common.vo.WmsRequisitionDetailVo;
import com.dream.common.vo.WmsRequisitionNoVo;
import com.dream.common.vo.WmsRequisitionPageVo;
import com.dream.service.IWmsRequisitionService;
import com.dream.service.IWmsSerialNumberService;
import com.dream.service.utils.ListMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
        List<WmsRequisitionPageVo> pageVoList= pageVos.getRecords();
        if (CollUtil.isNotEmpty(pageVoList)){
            List<String> codeList=  pageVoList.stream().map(WmsRequisitionPageVo::getCode).collect(Collectors.toList());
            QueryWrapper<WmsRequisition> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("code",codeList);
            List<WmsRequisition> wmsRequisitions= wmsRequisitionMapper.selectList(queryWrapper);
            Map<String,List<WmsRequisition>> stringListMap= wmsRequisitions.stream().collect(Collectors.groupingBy(WmsRequisition::getCode));
            Map<String,String> codeStatusMap=new HashMap<>();
            for (String code:stringListMap.keySet()){
                List<WmsRequisition> requisitions=stringListMap.get(code);
                Set<String> statusSet=  requisitions.stream().map(WmsRequisition::getStatus).collect(Collectors.toSet());
                List<String> statusList = new ArrayList<>(statusSet);
                if (statusSet.size()==1){
                    codeStatusMap.put(code,statusList.get(0));
                }else {
                    codeStatusMap.put(code,"部分出库");
                }
            }
            pageVoList.forEach(a->{a.setStatus(codeStatusMap.get(a.getCode()));});
        }
        return pageVos;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveRequisition(Map param) {
        List<WmsRequisition> requisitionList= ListMapUtils.toListBean((List<Map>) param.get("requisitionData"),WmsRequisition.class);
        String code= (String) param.get("code");
        if (StrUtil.isBlank((String) param.get("code"))){
            //新增
            code= iWmsSerialNumberService.generateSerialNumberByModelCode("wms_requisition");
        }else {
            //修改
            QueryWrapper<WmsRequisition> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("code",param.get("code"));
            List<WmsRequisition> requisitions=wmsRequisitionMapper.selectList(queryWrapper);
            //判断状态是否为未入库
            requisitions.forEach(a->{
                if (!"未入库".equals(a.getStatus())){
                    throw new RuntimeException("只能修改未出库的领料单");
                }
            });
            //删除原领料单所有数据然后冲销插入
            wmsRequisitionMapper.delete(queryWrapper);
        }
        for (WmsRequisition a : requisitionList) {
            //校验物料编码是否为空
            if (StrUtil.isBlank(a.getMaterialNo())) {
                throw new RuntimeException("物料编码不能为空");
            }
            //入库数量不能为0校验
            if (a.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                throw new RuntimeException("物料：" + a.getMaterialNo() + "领料数量不能等于0");
            }
            a.setCode(code);
            a.setRequisitionDate(DateUtil.parse((String) param.get("requisitionDate")));
            a.setStatus("未出库");
            a.setDeliverQuantity(BigDecimal.ZERO);
        }
        this.saveBatch(requisitionList);
    }

    @Override
    public List<WmsRequisitionDetailVo> queryRequisitionDetail(Map param) {
        List<WmsRequisitionDetailVo> requisitionDetail=wmsRequisitionMapper.queryRequisitionDetail(param);
        return requisitionDetail;
    }

    @Override
    public List<WmsRequisitionNoVo> queryRequisitionNo(Map param) {
        return wmsRequisitionMapper.queryRequisitionNo(param);
    }
}
