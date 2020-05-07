package com.wiserun.file.pojo.controller;

import com.netflix.client.http.HttpResponse;
import com.wiserun.entity.Result;
import com.wiserun.entity.StatusCode;
import com.wiserun.file.pojo.FastDFSFile;
import com.wiserun.file.util.FastDFSClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result uploadFile(MultipartFile file) {

        if (file == null) {
            return new Result(true, StatusCode.ERROR, "上传的文件错误");
        }

        byte[] fileContent = null;//获取文件内容
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);//获取文件后缀名

        if (StringUtils.isEmpty(fileName)) {
            return new Result(true, StatusCode.ERROR, "文件的名称错误");
        }

        try {
            fileContent = file.getBytes();
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, fileContent, extName);
            System.err.println("fastDFSFile=============================" + fastDFSFile);
            String[] uploadPaths = FastDFSClient.upload(fastDFSFile);
            String filepath = "http://192.168.168.129:8080/" + uploadPaths[0] + "/" + uploadPaths[1];
            return new Result(true, StatusCode.OK, "上传成功", filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true, StatusCode.ERROR, "上传失败");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @RequestParam("group_name") String group_name, @RequestParam("filename") String filename) {
        InputStream download = FastDFSClient.download(group_name, filename);
        /*  response.setContentType("application/x-msdownload");*/
        byte[] buf = new byte[1024];
        int len = 0;
        OutputStream out;
        try {
            out = response.getOutputStream();
            while ((len = download.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            download.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestParam("group_name") String group_name, @RequestParam("filename") String filename) {
        Integer integer = FastDFSClient.deleteFile(group_name, filename);
        if (integer!= null && integer > 0) {
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(true, StatusCode.OK, "删除失败，文件不存在");
    }


}
