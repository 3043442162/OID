package com.ainetwork.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ainetwork.config.StpInterfaceImpl;
import com.ainetwork.entity.OID;
import com.ainetwork.entity.OIDRelation;
import com.ainetwork.entity.OIDXml;
import com.ainetwork.enums.PermissionEnum;
import com.ainetwork.enums.RoleEnum;
import com.ainetwork.service.*;
import com.ainetwork.util.Result;
import com.ainetwork.vo.OIDVo;
import com.ainetwork.vo.TreeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/OID")
@Api(tags = "OID管理模块")
public class OIDController {


    @Autowired
    private OIDService oidService;


    @Autowired
    private OIDXmlService oidXmlService;

    @Autowired
    private OIDRelationService oidRelationService;

//    @Autowired
//    private OIDUserService oidUserService;

    @Autowired
    private UserOIDRoleService userOIDRoleService;
    @Autowired
    private StpInterfaceImpl stpInterface;

    /**
     * oid信息查询
     */
    @ApiOperation("/根据id查询oid信息")
    @GetMapping
    public Result<?> queryOID(int id){
        stpInterface.setNodeId(id);
        StpUtil.checkPermission(PermissionEnum.QUERY_NODE.getDesc());
        OID byId = oidService.getById(id);
        return byId== null ? Result.error("您查询的oid不存在"):Result.ok(byId);
    }

    /**
     * oid注册
     */
    @ApiOperation("在父节点下注册子节点oid")
    @PostMapping("/register")
    public Result<?> register(@Validated  @RequestBody OID oid, Integer fatherId){
        stpInterface.setNodeId(fatherId);
        StpUtil.checkPermission(PermissionEnum.UPDATE_NODE.getDesc());
        OID byId = oidService.getById(fatherId);
        if (!Optional.ofNullable(byId).isPresent()) return Result.error("父节点不存在");

        List<Integer> sonOIDs = oidRelationService.fromFatherIdQueryOID(fatherId);

        for (Integer id :
                sonOIDs) {
            OID sonOID = oidService.getById(id);
            if(oid.getOid().equals(sonOID.getOid())) return Result.error("oid 节点重复");
        }

        int insert = oidService.getBaseMapper().insert(oid);
        if (insert==0) return Result.error("保存失败oid失败");
        OIDRelation oidRelation = new OIDRelation();
        oidRelation.setFatherId(fatherId);

        oidRelation.setSonId(oid.getId());
        boolean save = oidRelationService.save(oidRelation);
        Integer loginId = StpUtil.getLoginIdAsInt();
        userOIDRoleService.saveUserOidRole(loginId, oid.getId(), RoleEnum.OTHER.getRoleId());
        return save ? Result.ok(oid.getId()):Result.error("保存oid及其父节点失败");
    }

    /**
     * oid删除
     */
    @ApiOperation("根据id删除oid")
    @DeleteMapping
    public Result<?> deleteOID(Integer id){
        stpInterface.setNodeId(id);
        StpUtil.checkPermission(PermissionEnum.DELETE_NODE.getDesc());
        OID byId = oidService.getById(id);
        if (byId == null) return Result.error("该oid不存在");

        List<Integer> sonOIDs = oidRelationService.fromFatherIdQueryOID(id);
        if ( sonOIDs.size() !=0 ) return Result.error("删除失败，该节点为拥有子节点的父节点，请先删除完子节点，然后才能删除父节点");
        boolean b = oidService.removeById(byId.getId());
        Boolean aBoolean = oidRelationService.deleteFromSonID(id);
        if (!b) return Result.error("删除oid失败");
        if(!aBoolean) Result.error("删除oid和父oid关系失败");
        return  Result.ok("删除oid成功");
    }

//    @ApiOperation("默认返回当前用户所拥有的OID子树")
//    @GetMapping("/default")
//    public Result<?> queryOIDPage(Integer oid){
//        List<UserOID> userOIDS = userOIDService.queryUserOwnerOID(oid);
//        List<Integer> oids = userOIDS.stream().map(UserOID::getOid).collect(Collectors.toList());
//        return Result.ok(oids);
//    }

    /**
     * 带父节点
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询当前OID节点以及其子OID")
    @GetMapping("/userOwner")
    public Result<?> queryUserOIDPage(Integer id, Integer page, Integer pageSize){
        Page<OIDRelation> pageInfo = new Page<>(page, pageSize);
//        pageInfo  = oidService.page(pageInfo);
        LambdaQueryWrapper<OIDRelation> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(OIDRelation::getFatherId, id);
//        OIDRelation one = oidRelationService.getOne(wrapper);
        pageInfo = oidRelationService.page(pageInfo, wrapper);
        OID byId = oidService.getById(id);
        TreeVo treeVo = new TreeVo();
        treeVo.setId(byId.getId());
        OIDXml oidXml = oidXmlService.getById(byId.getOidXml());
        treeVo.setLabel(byId.getDescribe()+(oidXml == null ? null:oidXml.getXmlUrl()));
        List<OIDRelation> records = pageInfo.getRecords();
        List<TreeVo> treeVos = records.stream().map(
                line -> {
                    TreeVo treeVo1 = new TreeVo();
                    treeVo1.setId(line.getSonId());
                    OID oid  = oidService.getById(line.getSonId());
                    treeVo1.setLabel(oid.getOid()+":" + oid.getDescribe());
                    return treeVo1;
                }
        ).collect(Collectors.toList());

        treeVo.setChildren(treeVos);
        return Result.ok(treeVo);
    }

    /**
     * 不带父节点
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation("查询当前OID节点的子OID")
    @GetMapping("/userOwner1")
    public Result<?> queryUserOIDPage1(Integer id, Integer page, Integer pageSize){
        Page<OIDRelation> pageInfo = new Page<>(page, pageSize);
//        pageInfo  = oidService.page(pageInfo);
        LambdaQueryWrapper<OIDRelation> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(OIDRelation::getFatherId, id);
//        OIDRelation one = oidRelationService.getOne(wrapper);
        pageInfo = oidRelationService.page(pageInfo, wrapper);
//        OID byId = oidService.getById(id);
//        TreeVo treeVo = new TreeVo();
//        treeVo.setId(byId.getId());
//        treeVo.setLabel(byId.getDescribe()+byId.getIpAddress());
        List<OIDRelation> records = pageInfo.getRecords();
        if(records.size() ==0) {
//            OIDVo oidVo = new OIDVo();
//            oidVo.setDescribe("该节点没有子节点");
            return Result.ok();
        }
        List<OIDVo> treeVos = records.stream().map(
                line -> {
//                    TreeVo treeVo1 = new TreeVo();
//                    treeVo1.setId(line.getSonId());
                    //                    treeVo1.setLabel(oid.getDescribe() + oid.getIpAddress());
                    OID byId = oidService.getById(line.getSonId());
                    OIDVo oidVo = new OIDVo();
                    oidVo.setId(byId.getId());
                    oidVo.setOid(byId.getOid());
                    oidVo.setDescribe(byId.getDescribe());

                    OIDXml byId1 = oidXmlService.getById(byId.getOidXml());
                    oidVo.setIpAddress(byId1.getXmlUrl());
                    return oidVo;
                }
        ).collect(Collectors.toList());

//        treeVo.setChildren(treeVos);
        return Result.ok(treeVos);
    }


    @ApiOperation("oid解析功能")
    @GetMapping("/oidRelation")
    public Result<?> oidRelation( @NotBlank String oidStr){
        List<Integer> collect;
        try{
            String[] split = oidStr.split("\\.");
            List<String> strs = Arrays.asList(split);
            collect = strs.stream().map(Integer::parseInt).collect(Collectors.toList());
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

        if(collect.size() <= 0) return Result.error("oid格式不正确，请检查oid格式");
//        for (Integer oid: collect
//             ) {
//            // 递归查询直到oid等于
//            oidService.getOne()
//        }
        int id = 1;
        int i;
        for(i = 1;i<collect.size();i++){
            // 从初始节点开始查找
            OID byId = oidService.getById(id);
            OID temp = null;
            List<Integer> integers = oidRelationService.fromFatherIdQueryOID(byId.getId());
            for (Integer intger:
                 integers) {
                OID byId1 = oidService.getById(intger);
                if (byId1.getOid().equals( collect.get(i))) {
                    id = byId1.getId();
                    temp = byId1;
                    break;
                }
            }
            if(temp == null){
                return Result.error("请检查您的oid以.分割的第"+(i+1)+"位置上的数，该位置上的数在数据库中没有找到");
            }
        }
        if(i == collect.size()){
            // 说明找到了，返回对应信息
            return Result.ok(oidService.getById(id).getDescribe());
        }
        return Result.error("oid未能成功解析");
    }

    /**
     * OID修改功能
     */
    @ApiOperation("OID修改功能")
    @PostMapping("/update")
    public Result<?> updateOID(@RequestBody OID oid){
        stpInterface.setNodeId(oid.getId());
        int loginIdAsInt = StpUtil.getLoginIdAsInt();
        StpUtil.checkPermission(PermissionEnum.UPDATE_NODE.getDesc());
        if(!Optional.ofNullable(oid.getId()).isPresent()){
            return Result.error("id不能为null");
        }
        OID byId = oidService.getById(oid.getId());
        BeanUtils.copyProperties(oid, byId);
        boolean b = oidService.updateById(byId);
        return b ? Result.ok():Result.error();
    }

    /**
     * 查询所有父节点OID组成的OID集合
     */
    @ApiOperation("查询OID完整前缀")
    @GetMapping("/getFathers")
    public Result<?> getFathers(Integer id){
        List<Integer> fatherIDs = oidRelationService.queryOidAllFathter(id);
        if (fatherIDs.size() == 0) return Result.error("oid不正确，或查询的节点为顶级OID");

        StringBuilder builder = new StringBuilder();
        for (Integer in:fatherIDs
             ) {
            OID byId = oidService.getById(in);
            builder.append(byId.getOid()).append(".");
        }
        String string = builder.toString();
        return Result.ok(string.substring(0, string.length()-1));
    }
    /**
     * 查询当前OID下，最大的子OID数字
     */
    @ApiOperation("查询当前OID下，最大的子OID数字")
    @GetMapping("/queryMaxOID")
    public Result<?> queryMaxOID(Integer id){
        Integer fatherIDs = oidRelationService.maxOID(id);
        if(fatherIDs == null) return Result.ok(1);
        OID byId = oidService.getById(fatherIDs);

        return Result.ok(byId.getOid());
    }

    @ApiOperation("查找当前节点的父节点")
    @GetMapping("/queryFatherOID")
    public Result<?> getFatherOID(Integer id){
        Integer fatherId = oidRelationService.queryFatherOID(id);
        OID oid = oidService.getById(fatherId);
        return Result.ok(oid);
    }

    @ApiOperation("查找当前的父节点的OID值最大的子节点")
    @GetMapping("/queryFatherMaxOID")
    public Result<?> getFatherMaxOID(Integer id){
        Integer fatherId = oidRelationService.queryFatherOID(id);
        List<Integer> sons = oidRelationService.fromFatherIdQueryOID(fatherId);
        Integer max = 0;
        for (Integer oidId :
                sons) {
            OID sonOID = oidService.getById(oidId);
            max = max > sonOID.getOid()?max:sonOID.getOid();
        }
        // 如果已经为父节点
        if(sons.isEmpty()){
            return Result.ok(oidService.getById(id).getOid());
        }
        return Result.ok(max);
    }

    @ApiOperation("下载当前oid对应的xml文件")
    @GetMapping("/getXml")
    public void downLoadXmlFile(Integer id, HttpServletResponse response)  {
        OID byId = oidService.getById(id);
        @NotNull Integer oidXml = byId.getOidXml();
        File file = oidXmlService.queryXmlFromId(oidXml);
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        ServletOutputStream out = null;
        InputStream stream = null;
        try{
            out = response.getOutputStream();
            stream = new FileInputStream(file);//读取服务器上的文件
                byte buff[] = new byte[1024];
                int length = stream.read(buff);
                while(length > 0) {
                    out.write(buff,0,length);
                    length = stream.read(buff);
                }
            } catch (IOException  e) {
                e.printStackTrace();
            }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null ){
                try {
                    out.flush();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Value("${store.dir}")
    private String storeDir;
    /**
     * 返回oid 完整信息，包括所有oid前缀和具体xml文件字符串
     */
    @GetMapping("/query/oid/information")
    public Result queryOidInformation(Integer id) throws IOException {
        Map<String, Object> map = new HashMap<>();
        OID oid = oidService.getById(id);
        OIDXml oidXml = oidXmlService.getById(oid.getOidXml());

        StringBuilder builder = new StringBuilder();

        List<Integer> fatherIDs = oidRelationService.queryOidAllFathter(id);
        if (fatherIDs == null || fatherIDs.size() == 0)
        {   builder.append(oid.getOid()); }
        else {
            for (Integer in : fatherIDs
            ) {
                OID byId = oidService.getById(in);
                builder.append(byId.getOid()).append(".");
            }
            builder.append(oid.getOid());
        }
        String string = builder.toString();
        map.put("preOID", string);
        map.put("describe", oid.getDescribe());
        if(oidXml.getOidUserId() != StpUtil.getLoginIdAsInt()) return Result.error("没有权限访问该oid");
        map.put("xmlName", oidXml.getXmlName());
        FileInputStream stream = new FileInputStream(storeDir+oidXml.getXmlUrl());
        byte buff[] = new byte[1024];
        int length = stream.read(buff);
        StringBuilder builder1 = new StringBuilder();
        while(length > 0) {
            builder1.append(new String(buff,0,length));
            length = stream.read(buff);
        }
        map.put("xmlContent", builder1.toString());
        return Result.ok(map);
    }
}
