package com.ainetwork.service.impl;

import com.ainetwork.entity.OIDRelation;
import com.ainetwork.mapper.OIDMapper;
import com.ainetwork.mapper.OIDRelationMapper;
import com.ainetwork.service.OIDRelationService;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
