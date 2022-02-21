package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dream.common.entity.WmsDeliver;
import com.dream.common.entity.WmsEntry;
import com.dream.common.entity.WmsStock;
import com.dream.common.entity.WmsToDoMatters;
import com.dream.common.mapper.WmsDeliverMapper;
import com.dream.common.mapper.WmsEntryMapper;
import com.dream.common.mapper.WmsStockMapper;
import com.dream.common.mapper.WmsToDoMattersMapper;
import com.dream.service.IWmsToDoMattersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvxiaozuo
 * @since 2022-02-21
 */
@Service
public class WmsToDoMattersServiceImpl extends ServiceImpl<WmsToDoMattersMapper, WmsToDoMatters> implements IWmsToDoMattersService {
    @Autowired
    private WmsToDoMattersMapper wmsToDoMattersMapper;
    @Autowired
    private WmsEntryMapper entryMapper;
    @Autowired
    private WmsDeliverMapper deliverMapper;
    @Autowired
    private WmsStockMapper stockMapper;
    @Override
    public List<WmsToDoMatters> queryToDoMattersList(Map param) {
        //先查询今天添加的
        QueryWrapper<WmsToDoMatters> queryWrapper=new QueryWrapper<>();
        queryWrapper.ge("create_time", DateUtil.beginOfDay(new Date()));
        queryWrapper.eq("type","artificial");
        queryWrapper.orderByAsc("id");
        List<WmsToDoMatters> toDoMattersToDateList= wmsToDoMattersMapper.selectList(queryWrapper);
        //前几天完成的
        queryWrapper.clear();
        queryWrapper.le("create_time", DateUtil.beginOfDay(new Date()));
        queryWrapper.eq("type","artificial");
        queryWrapper.eq("status",false);
        queryWrapper.orderByAsc("id");
        List<WmsToDoMatters> toDoMattersListBefore= wmsToDoMattersMapper.selectList(queryWrapper);
        //系统添加的
        queryWrapper.clear();
        queryWrapper.eq("type","system");
        queryWrapper.eq("status",false);
        queryWrapper.orderByAsc("id");
        List<WmsToDoMatters> toDoMattersListSystem= wmsToDoMattersMapper.selectList(queryWrapper);
        toDoMattersListSystem.addAll(toDoMattersListBefore);
        toDoMattersListSystem.addAll(toDoMattersToDateList);
        return toDoMattersListSystem;
    }

    @Override
    public void queryToDoMattersStatus(Map param) {
        WmsToDoMatters toDoMatters= BeanUtil.toBean(param,WmsToDoMatters.class, CopyOptions.create());
        wmsToDoMattersMapper.updateById(toDoMatters);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveMatterPost(Map param) {
        WmsToDoMatters toDoMatters= BeanUtil.toBean(param,WmsToDoMatters.class, CopyOptions.create());
        toDoMatters.setType("artificial");
        toDoMatters.setStatus(false);
        wmsToDoMattersMapper.insert(toDoMatters);
    }

    @Override
    public Map queryEntryDeliverStockToDate(Map param) {
        BigDecimal entryMoney=BigDecimal.ZERO;
        BigDecimal deliverMoney=BigDecimal.ZERO;
        BigDecimal stockMoney=BigDecimal.ZERO;
        Map resMap=new HashMap();
        QueryWrapper<WmsEntry> entryQueryWrapper=new QueryWrapper<>();
        entryQueryWrapper.ge("create_time",DateUtil.beginOfDay(new Date()));
        entryQueryWrapper.eq("status","入库");
        List<WmsEntry> entryList=entryMapper.selectList(entryQueryWrapper);
        for (WmsEntry a : entryList) {
            entryMoney = entryMoney.add(a.getMoney());
        }
        QueryWrapper<WmsDeliver> deliverQueryWrapper=new QueryWrapper<>();
        deliverQueryWrapper.ge("create_time",DateUtil.beginOfDay(new Date()));
        deliverQueryWrapper.eq("status","出库");
        List<WmsDeliver> deliverList=deliverMapper.selectList(deliverQueryWrapper);
        for (WmsDeliver a : deliverList) {
            deliverMoney = deliverMoney.add(a.getMoney());
        }
        QueryWrapper<WmsStock> stockQueryWrapper=new QueryWrapper<>();
        stockQueryWrapper.gt("money",0);
        List<WmsStock> stockList=stockMapper.selectList(stockQueryWrapper);
        for (WmsStock a : stockList) {
            stockMoney = stockMoney.add(a.getMoney());
        }
        resMap.put("entryMoney",entryMoney);
        resMap.put("deliverMoney",deliverMoney);
        resMap.put("stockMoney",stockMoney);
        return resMap;
    }
}
