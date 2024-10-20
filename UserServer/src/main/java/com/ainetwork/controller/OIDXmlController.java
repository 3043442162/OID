package com.ainetwork.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ainetwork.dto.OIDProperty;
import com.ainetwork.entity.OIDXml;
import com.ainetwork.service.OIDService;
import com.ainetwork.service.OIDXmlService;
import com.ainetwork.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController()
@RequestMapping("/oidXml")
@Api(tags = "oid Xml管理")
public class OIDXmlController {

    @Autowired
    private OIDXmlService oidXmlService;

    @PostMapping("upload")
    @ApiOperation("根据key value上传数据到本地")
    public Result<?> uploadXml(@RequestBody List<OIDProperty> oidPropertyList, String xmlName, Integer loginId){
//        Integer loginId = StpUtil.getLoginIdAsInt();
        String url = StringUtils.delete(UUID.randomUUID().toString(), "-")+".xml";

        // 从数据库中读取看看有没有同名xml
        List<OIDXml> oidXmls = oidXmlService.queryUserXml(loginId);
        List<OIDXml> oidXmlStream = oidXmls.stream().filter(line -> xmlName.equals(line.getXmlName())).collect(Collectors.toList());

        if ( oidXmlStream.size() != 0) return Result.error("模板文件已经存在");

        oidXmlService.upload(oidPropertyList, url);
        OIDXml oidXml = new OIDXml();
        oidXml.setOidUserId(loginId);
        oidXml.setXmlName(xmlName);
        oidXml.setXmlUrl(url);

        boolean flag = oidXmlService.save(oidXml);
        return flag ? Result.ok("保存成功"):Result.error("保存失败");
    }

    @GetMapping("/queryXml")
    @ApiOperation("查询用户所拥有的oidxml模板链接")
    public Result<?> queryUserOID(Integer userId){
//        int userId = StpUtil.getLoginIdAsInt();
        List<OIDXml> modelLinks = oidXmlService.queryUserXml(userId);
        return modelLinks.size() >0 ? Result.ok(modelLinks):Result.error("用户无模板");
    }

    // 根据文件id删除xml文件
    @GetMapping("/deleteXml")
    @ApiOperation("根据用户id和xmlid删除xml")
    public Result<?> removeXMl(Integer userId, Integer xmlId){
        return oidXmlService.deleteXMLFileFromUserIdAndFileLink(userId, xmlId) ? Result.ok():Result.error();
    }
}
