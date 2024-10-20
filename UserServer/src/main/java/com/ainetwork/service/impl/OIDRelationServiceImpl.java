package com.ainetwork.service.impl;

import com.ainetwork.entity.OIDRelation;
import com.ainetwork.mapper.OIDMapper;
import com.ainetwork.mapper.OIDRelationMapper;
import com.ainetwork.service.OIDRelationService;
import com.ainetwork.service.OIDService;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OIDRelationServiceImpl extends ServiceImpl<OIDRelationMapper, OIDRelation> implements OIDRelationService {
    /**
     * 根据fatherId查询其下所有子OID节点
     * @param fatherId
     * @return
     */
    @Override
    public List<Integer> fromFatherIdQueryOID(Integer fatherId) {

        LambdaQueryWrapper<OIDRelation> oidRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        oidRelationLambdaQueryWrapper.eq(OIDRelation::getFatherId, fatherId);

        List<OIDRelation> oidRelations = baseMapper.selectList(oidRelationLambdaQueryWrapper);
        List<Integer> sonOIDList = oidRelations.stream().map(OIDRelation::getSonId).collect(Collectors.toList());
        return sonOIDList;
    }

    /**
     * 根据子oid删除记录
     * @param sonId
     * @return
     */
    @Override
    public Boolean deleteFromSonID(Integer sonId) {
        LambdaQueryWrapper<OIDRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OIDRelation::getSonId, sonId);

        OIDRelation one = getOne(wrapper);
        if (one == null) return false;

        int delete = baseMapper.delete(wrapper);
        if (! (delete > 0)) return false;
        return true;
    }

    @Override
    public List<Integer> queryOidAllFathter(Integer sonId) {

        List<Integer> result = new ArrayList<>();
        LambdaQueryWrapper<OIDRelation> wr = new LambdaQueryWrapper<>();
        wr.eq(OIDRelation::getSonId, sonId);
        if(sonId == 1) return result;
        OIDRelation oidRelation = baseMapper.selectOne(wr);
        if(!Optional.ofNullable(oidRelation).isPresent()) return result;
        result.add(sonId);
        while (oidRelation.getFatherId() != 1){
            result.add(oidRelation.getFatherId());
            LambdaQueryWrapper<OIDRelation> wr1 = new LambdaQueryWrapper<>();
            wr1.eq(OIDRelation::getSonId, oidRelation.getFatherId());
            oidRelation = baseMapper.selectOne(wr1);
        }
        result.add(1);
//        result = result.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    @Override
    public Integer maxOID(Integer id) {

        QueryWrapper<OIDRelation> oidRelationLambdaQueryWrapper = new QueryWrapper<>();

        oidRelationLambdaQueryWrapper.select("max(son_id) as son_id");
        oidRelationLambdaQueryWrapper.eq("father_id", id);

        OIDRelation one = baseMapper.selectOne(oidRelationLambdaQueryWrapper);
        if (!Optional.ofNullable(one).isPresent()) return 1;
        return one.getSonId();
    }

    @Override
    public Integer queryFatherOID(Integer fatherId) {
        LambdaQueryWrapper<OIDRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OIDRelation::getSonId, fatherId);
        wrapper.select(OIDRelation::getFatherId);

        OIDRelation oidRelation = baseMapper.selectOne(wrapper);
        return oidRelation == null ? 0:oidRelation.getFatherId();
    }

}
