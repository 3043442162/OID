package com.ainetwork.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ainetwork.dto.OIDProperty;
import com.ainetwork.entity.OID;
import com.ainetwork.entity.OIDXml;
import com.ainetwork.service.OIDService;
import com.ainetwork.service.OIDXmlService;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@CrossOrigin
@RestController()
@RequestMapping("/oidXml")
@Api(tags = "oid Xml管理")
public class OIDXmlController {

    @Autowired
    private OIDXmlService oidXmlService;

    @PostMapping("upload")
    @ApiOperation("根据key value上传数据到本地")
    public Result<?> uploadXml(@RequestBody List<OIDProperty> oidPropertyList, String xmlName){
//        Integer loginId = StpUtil.getLoginIdAsInt();
        Integer loginId = StpUtil.getLoginIdAsInt();
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
    public Result<?> queryUserOID(HttpServletRequest httpServletRequest){
        String satoken = httpServletRequest.getHeader("satoken");
        Integer userId = StpUtil.getLoginIdAsInt();
//        int userId = StpUtil.getLoginIdAsInt();
        List<OIDXml> modelLinks = oidXmlService.queryUserXml(userId);
        return modelLinks.size() >0 ? Result.ok(modelLinks):Result.error("用户无模板");
    }

    @Autowired
    private OIDService oidService;
    // 根据文件id删除xml文件
    @GetMapping("/deleteXml")
    @ApiOperation("根据用户id和xml id删除xml")
    public Result<?> removeXMl(Integer xmlId){
        Integer userId = StpUtil.getLoginIdAsInt();
        // 判断该xml下面有无oid，只有没有的情况才能删除
        BaseMapper<OID> baseMapper = oidService.getBaseMapper();
        LambdaQueryWrapper<OID> oidLambdaQueryWrapper = new LambdaQueryWrapper<>();
        oidLambdaQueryWrapper.eq(OID::getOidXml, xmlId);

        List<OID> oids = baseMapper.selectList(oidLambdaQueryWrapper);

        if(oids != null && oids.size() > 0){
            return Result.error("不能删除该xml，因为如下oid与该xml关联"+ oids);
        }
        return oidXmlService.deleteXMLFileFromUserIdAndFileLink(userId, xmlId) ? Result.ok():Result.error();
    }

    @Value("${store.dir}")
    private String storeDir;
    /**
     * 根据用户id和xml id返回模板内容
     * @param xmlId
     * @return
     */
    @GetMapping
    public Result<?> getXmlContent(Integer xmlId){
        Integer loginId = StpUtil.getLoginIdAsInt();
        // 判断用户是否对这个xml有操作能力
        LambdaQueryWrapper<OIDXml> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OIDXml::getId, xmlId);
        wrapper.eq(OIDXml::getOidUserId, loginId);
        //
        OIDXml one = oidXmlService.getOne(wrapper);
        if(one == null) return Result.error("oid不属于当前用户");
        // 如果one不为null
        StringBuilder builder = new StringBuilder();
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(storeDir+one.getXmlUrl()));
            // 随机行顺序进行数据处理
            //                System.out.println(ele);
            lines.forEach(builder::append);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 执行io操作，返回字符串
        return Result.ok(builder.toString());
    }
}
