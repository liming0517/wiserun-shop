package com.wiserun.file.util;

import com.wiserun.file.pojo.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {
    //slf4j日志记录器
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    /***
     * 初始化加载FastDFS的TrackerServer配置
     */
    static {
        try {
            String filePath = ClassLoader.getSystemClassLoader().getResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            logger.error("读取fastDFS配置文件失败",e);
        }
    }

    /*文件上传*/
    public static String[] upload(FastDFSFile file) {

        //获取文件的作者
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());
        //创建tracker客户端的连接工具
        TrackerClient trackerClient = new TrackerClient();
        //得到的tracker服务器信息
        TrackerServer trackerServer = null;

        //返回的上传文件路径
        String uploadPath[] = null;
        try {
            trackerServer = trackerClient.getConnection();//建立tracker的客户端连接
            StorageClient storageClient = new StorageClient(trackerServer, null);  //得到Storage客户端信息
            System.err.println("file============================="+file);

            uploadPath = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);//上传文件
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uploadPath;

    }

    /*文件下载*/
    public static InputStream download(String group_name,String filename){
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer=null;
        byte[] bytes = new byte[1024];
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StorageClient storageClient = new StorageClient(trackerServer,null);
        try {
            System.err.println("group_name========"+group_name+"  filename==================="+filename);
            bytes = storageClient.download_file(group_name, filename);
            System.err.println("byte==========="+bytes);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer deleteFile(String group_name,String filename){
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,null);

            return storageClient.delete_file(group_name,filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;

    }


}
