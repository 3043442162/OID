package com.ainetwork.service.impl;

import com.ainetwork.dto.OIDProperty;
import com.ainetwork.entity.OIDXml;
import com.ainetwork.mapper.OIDXmlMapper;
import com.ainetwork.service.OIDXmlService;
import com.ainetwork.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OIDXmlServiceImpl extends ServiceImpl<OIDXmlMapper, OIDXml> implements OIDXmlService {


    /**
     * 返回用户持有的xml文件模版列表
     * @param userId
     * @return
     */
    @Override
    public List<OIDXml> queryUserXml(Integer userId) {
        LambdaQueryWrapper<OIDXml> oidXmlLambdaQueryWrapper = new LambdaQueryWrapper<>();
        oidXmlLambdaQueryWrapper.eq(OIDXml::getOidUserId, userId);
//        oidXmlLambdaQueryWrapper.select(OIDXml::getXmlUrl);
        return  baseMapper.selectList(oidXmlLambdaQueryWrapper);
    }

    @Override
    public boolean deleteXMLFileFromUserIdAndFileLink(Integer userId, Integer xmlId) {
        OIDXml byId = getById(xmlId);
        if (!userId.equals(byId.getOidUserId())){
            return false;
        }
        File file = new File("D:\\task\\OID\\xml\\" + byId.getXmlUrl());

        if(file.delete()){
            removeById(xmlId);
            return true;
        }
        return false;
    }

    /**
     * 上传文件到minio
     * @param oidPropertyList xml文件属性列表
     * @return
     */
    @Override
    public boolean upload(List<OIDProperty> oidPropertyList,String  url) {
        try {
            // 创建document对象
            Document document = DocumentHelper.createDocument();
            // 创建根节点bookRoot
            Element root = document.addElement("OID");
            // 向根节点中添加第一个节点
//            Element book1 = StudentRoot.addElement("node");
            // 向子节点中添加属性
//            book1.addAttribute("id","1");
            // 向节点中添加子节点
//            Element name = book1.addElement("key");
//            // 向子节点赋值
//            name.setText("小乔1");
//            Element price = book1.addElement("value");
//            price.setText("1");
//            // 向根节点中添加第二个节点
//            Element book2 = StudentRoot.addElement("node");
//            book2.addElement("key").setText("大桥2");
//            book2.addElement("value").setText("2");
//            MinioClient minioClient =
//                    MinioClient.builder()
//                            .endpoint("http://192.168.10.130:9000")
//                            .credentials("minio", "minio123")
//                            .build();
            for (OIDProperty oidPro:oidPropertyList
                 ) {
                root.addElement("key").setText(oidPro.getKey());
                root.addElement("value").setText(oidPro.getValue());
            }
//            String s = document.asXML();
//            System.out.println(s);
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.asXML().getBytes(StandardCharsets.UTF_8));
//            ObjectWriteResponse oid = minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket("oid")
//                            .object(url)
//                            .stream(byteArrayInputStream, document.asXML().getBytes().length, -1)
//                            .build()
//            );


//            System.out.println(oid);
//            OIDXml oidXml = new OIDXml();
            File file = new File("D:\\task\\OID\\xml\\"+url);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(document.asXML().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();

            fileOutputStream.close();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据id返回xml文件
     * @param id
     * @return
     */
    @Override
    public File queryXmlFromId(Integer id) {

        OIDXml oidXml = getById(id);
        String url = oidXml.getXmlUrl();
        File file = new File("D:\\task\\OID\\xml\\"+url);
        return file;
    }
}
