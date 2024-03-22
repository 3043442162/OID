package com.ainetwork.service;

import com.ainetwork.entity.OIDRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OIDRelationService extends IService<OIDRelation> {
    /**
     * 查找当前oid下面的子oid
     * @param fatherId
     * @return
     */
    List<Integer> fromFatherIdQueryOID(Integer fatherId);
    Boolean deleteFromSonID(Integer sonId);
}
