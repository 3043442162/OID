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

    /**
     * 根据子OID删除
     * @param sonId
     * @return
     */
    Boolean deleteFromSonID(Integer sonId);

    /**
     * 查找当前oid的所有父oid
     */
    List<Integer> queryOidAllFathter(Integer sonId);

    /**
     * 查询当前Oid下数字最大的OID
     */
    Integer maxOID(Integer id);

    /**
     * 查找父节点OID
     */
    Integer queryFatherOID(Integer sonId);

}
