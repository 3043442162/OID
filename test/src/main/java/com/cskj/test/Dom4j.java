package com.cskj.test;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLResult;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Dom4j {
    public static void main(String[] args) {
        try {
            // 创建document对象
            Document document = DocumentHelper.createDocument();
            // 创建根节点bookRoot
            Element StudentRoot = document.addElement("OID");
            // 向根节点中添加第一个节点
            Element book1 = StudentRoot.addElement("node");
            // 向子节点中添加属性
//            book1.addAttribute("id","1");
            // 向节点中添加子节点
            Element name = book1.addElement("key");
            // 向子节点赋值
            name.setText("小乔1");
            Element price = book1.addElement("value");
            price.setText("1");
            // 向根节点中添加第二个节点
            Element book2 = StudentRoot.addElement("node");
            book2.addElement("key").setText("大桥2");
            book2.addElement("value").setText("2");
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://hadoop103:9000")
                            .credentials("xwTEtCO6JomQc9QgJBV6", "hCIAlD4F8z7OqbX3NOZFZcl1Kmm9LMvI2bTW7TZY")
                            .build();

            String s = document.asXML();


            // 设置生成xml的格式
//            OutputFormat of = OutputFormat.createPrettyPrint();
//            // 设置编码格式
//            of.setEncoding("UTF-8");

            // 生成xml文件

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("bucket1")
                            .object("my_test_file1")
                            .stream(byteArrayInputStream, s.getBytes().length, -1)
                            .build()
            );

//            InputStream object = minioClient.getObject(
//                    GetObjectArgs.builder()
//                            .bucket("bucket1")
//                            .object("my_test_file")
//                            .build()
//            );
//            File file = new File();
//            File file = new File(".\\student.xml");
            //创建一个xml文档编辑器
//            XMLWriter writer = new XMLWriter(new FileOutputStream(file), of);
//
//
//            //把刚刚创建的document放到文档编辑器中
//            writer.write(document);
//            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
