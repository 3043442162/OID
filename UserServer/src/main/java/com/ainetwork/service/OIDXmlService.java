package com.ainetwork.service;

import com.ainetwork.dto.OIDProperty;
import com.ainetwork.entity.OIDXml;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;
import java.util.List;

public interface OIDXmlService extends IService<OIDXml> {
    /**
     * 返回用户持有文件列表
     */
    List<OIDXml> queryUserXml(Integer userId);
    /**
     * 根据用户id和xml文件id，删除该文件
     */
    boolean deleteXMLFileFromUserIdAndFileLink(Integer userId,Integer xmlId);

    /**
     * 上传文件到本地
     */
    boolean upload(List<OIDProperty> oidPropertyList,String url);

    /**
     * 根据xml id返回xml文件
     */
    File queryXmlFromId(Integer id);
}
