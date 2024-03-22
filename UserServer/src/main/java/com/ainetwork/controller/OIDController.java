package com.ainetwork.controller;

import com.ainetwork.entity.OID;
import com.ainetwork.entity.OIDRelation;
import com.ainetwork.entity.UserOID;
import com.ainetwork.service.OIDRelationService;
import com.ainetwork.service.OIDService;
import com.ainetwork.service.UserOIDService;
import com.ainetwork.util.Result;
import com.ainetwork.vo.TreeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.ietf.jgss.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/OID")
@Api(tags = "OID管理模块")
public class OIDController {


    @Autowired
    private OIDService oidService;

    @Autowired
    private OIDRelationService oidRelationService;

    @Autowired
    private UserOIDService userOIDService;
    /**
     * oid信息查询
     */
    @ApiOperation("/根据id查询oid信息")
    @GetMapping
    public Result<?> queryOID(Long id){
        OID byId = oidService.getById(id);
        return byId== null ? Result.error("您查询的oid不存在"):Result.ok(byId);
    }

    /**
     * oid注册
     */
    @ApiOperation("在父节点下注册子节点oid")
    @PostMapping("/register")
    public Result<?> register(@Validated  @RequestBody OID oid, Integer fatherId){
        OID byId = oidService.getById(fatherId);
        if (Optional.ofNullable(byId).isPresent()) return Result.error("父节点不存在");

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

        oidRelation.setSonId(insert);

        return oidRelationService.save(oidRelation)? Result.ok("保存oid成功"):Result.error("保存oid及其父节点失败");
    }

    /**
     * oid删除
     */
    @ApiOperation("根据id删除oid")
    @DeleteMapping
    public Result<?> deleteOID(Integer id){
        OID byId = oidService.getById(id);
        if (byId == null) return Result.error("该oid不存在");

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

    @ApiOperation("分页查询当前OID节点的子OID")
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
        treeVo.setLabel(byId.getDescribe()+byId.getIpAddress());
        List<OIDRelation> records = pageInfo.getRecords();
        List<TreeVo> treeVos = records.stream().map(
                line -> {
                    TreeVo treeVo1 = new TreeVo();
                    treeVo1.setId(line.getSonId());
                    OID oid  = oidService.getById(line.getSonId());
                    treeVo1.setLabel(oid.getDescribe() + oid.getIpAddress());
                    return treeVo1;
                }
        ).collect(Collectors.toList());

        treeVo.setChildren(treeVos);
        return Result.ok(treeVo);
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
                return Result.error("请检查您的oid以.分割的第"+i+1+"位置上的数，该位置上的数在数据库中没有找到");
            }
        }
        if(i == collect.size()){
            // 说明找到了，返回对应信息
            return Result.ok(oidService.getById(id).getDescribe());
        }
        return Result.error("oid未能成功解析");
    }

}
