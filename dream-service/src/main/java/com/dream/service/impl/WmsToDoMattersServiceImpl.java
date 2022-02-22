package com.dream.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateField;
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
import com.dream.common.vo.WmsDeliverWeekVo;
import com.dream.common.vo.WmsEntryWeekVo;
import com.dream.service.IWmsToDoMattersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        entryQueryWrapper.ge("entry_date",DateUtil.beginOfDay(new Date()));
        entryQueryWrapper.eq("status","入库");
        List<WmsEntry> entryList=entryMapper.selectList(entryQueryWrapper);
        for (WmsEntry a : entryList) {
            entryMoney = entryMoney.add(a.getMoney());
        }
        QueryWrapper<WmsDeliver> deliverQueryWrapper=new QueryWrapper<>();
        deliverQueryWrapper.ge("deliver_date",DateUtil.beginOfDay(new Date()));
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

    @Override
    public Map queryEntryDeliverSchart(Map param) {
        Map resMap=new HashMap();

        String[] weekDays = { "星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        //获得连续日期
        List<String> weekDayList=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Date date = DateUtil.offsetDay(DateUtil.lastWeek(), i);
            weekDayList.add(weekDays[DateUtil.dayOfWeek(date)-1]);
        }
        List<BigDecimal> entryMoneyList=new ArrayList<>();
        //查询近一周出入库数据
        QueryWrapper<WmsEntry> entryQueryWrapper=new QueryWrapper<>();
        entryQueryWrapper.ge("entry_date",DateUtil.lastWeek());
        entryQueryWrapper.lt("entry_date",DateUtil.beginOfDay(new Date()));
        entryQueryWrapper.eq("status","入库");
        List<WmsEntry> entryListWeek=entryMapper.selectList(entryQueryWrapper);
        List<WmsEntryWeekVo> wmsEntryWeekVoList=new ArrayList<>();
        entryListWeek.forEach(a->{
            WmsEntryWeekVo entryWeekVo=new WmsEntryWeekVo();
            entryWeekVo.setEntryDateStr(DateUtil.format(a.getEntryDate(),"yyyy-MM-dd"));
            entryWeekVo.setEntryDate(DateUtil.parse(entryWeekVo.getEntryDateStr()));
            entryWeekVo.setWeek(weekDays[DateUtil.dayOfWeek(a.getEntryDate())-1]);
            entryWeekVo.setMoney(a.getMoney());
            wmsEntryWeekVoList.add(entryWeekVo);
        });
        Map<String,List<WmsEntryWeekVo>> entryDateStrMap= wmsEntryWeekVoList.stream().collect(Collectors.groupingBy(WmsEntryWeekVo::getEntryDateStr));
        //结果
        Map<String,BigDecimal> weekMoneyEntryMap=new HashMap<>();

        for (String key:entryDateStrMap.keySet()){
            List<WmsEntryWeekVo> weekVos=entryDateStrMap.get(key);
            BigDecimal moneyAll =weekVos.stream().map(WmsEntryWeekVo::getMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
            weekMoneyEntryMap.put(weekVos.get(0).getWeek(),moneyAll);
        }

        //结果为空补齐
        weekDayList.forEach(a->{
            entryMoneyList.add(weekMoneyEntryMap.get(a)==null?BigDecimal.ZERO:weekMoneyEntryMap.get(a));
        });

        List<BigDecimal> deliverMoneyList=new ArrayList<>();
        QueryWrapper<WmsDeliver> deliverQueryWrapper=new QueryWrapper<>();
        deliverQueryWrapper.ge("deliver_date",DateUtil.lastWeek());
        entryQueryWrapper.lt("deliver_date",DateUtil.beginOfDay(new Date()));
        deliverQueryWrapper.eq("status","出库");
        List<WmsDeliver> deliverListWeek=deliverMapper.selectList(deliverQueryWrapper);

        List<WmsDeliverWeekVo> wmsDeliverWeekVoList=new ArrayList<>();
        deliverListWeek.forEach(a->{
            WmsDeliverWeekVo deliverWeekVo=new WmsDeliverWeekVo();
            deliverWeekVo.setDeliverDateStr(DateUtil.format(a.getDeliverDate(),"yyyy-MM-dd"));
            deliverWeekVo.setDeliverDate(DateUtil.parse(deliverWeekVo.getDeliverDateStr()));
            deliverWeekVo.setWeek(weekDays[DateUtil.dayOfWeek(a.getDeliverDate())-1]);
            deliverWeekVo.setMoney(a.getMoney());
            wmsDeliverWeekVoList.add(deliverWeekVo);
        });
        Map<String,List<WmsDeliverWeekVo>> deliverDateStrMap= wmsDeliverWeekVoList.stream().collect(Collectors.groupingBy(WmsDeliverWeekVo::getDeliverDateStr));
        //结果
        Map<String,BigDecimal> weekMoneyDeliverMap=new HashMap<>();

        for (String key:deliverDateStrMap.keySet()){
            List<WmsDeliverWeekVo> weekVos=deliverDateStrMap.get(key);
            BigDecimal moneyAll =weekVos.stream().map(WmsDeliverWeekVo::getMoney).reduce(BigDecimal.ZERO,BigDecimal::add);
            weekMoneyDeliverMap.put(weekVos.get(0).getWeek(),moneyAll);
        }

        //结果为空补齐
        weekDayList.forEach(a->{
            deliverMoneyList.add(weekMoneyDeliverMap.get(a)==null?BigDecimal.ZERO:weekMoneyDeliverMap.get(a));
        });

        resMap.put("week",weekDayList);
        resMap.put("weekEntryData",entryMoneyList);
        resMap.put("weekDeliverData",deliverMoneyList);
        return resMap;
    }
}
