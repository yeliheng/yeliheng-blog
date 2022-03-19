package com.yeliheng.blogcommon.utils;

import com.yeliheng.blogcommon.config.LocalStorageConfig;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;

@Component
public class ExcelUtils<T> {

    private final String FILE_PATH = "export_excels";

    /**
     * 导出Excel表格工具
     * @param exportParams 导出参数
     * @param data 数据
     * @param clz 实体类
     * @return 文件下载路径
     */
    public String exportExcel(ExportParams exportParams, T data, Class<T> clz) {
        String finalPath = "";
        //获取工作簿
        HSSFWorkbook workbook = (HSSFWorkbook) ExcelExportUtil.exportExcel(exportParams, clz, (Collection<?>) data);
        //文件流处理
        OutputStream outputStream;
        try{
            String filename = encodeFilename(exportParams.getTitle() + ".xlsx");
            outputStream = new FileOutputStream(getFilePath(filename,FILE_PATH));
            workbook.write(outputStream);
            finalPath = filename;
        }catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }

        return "/" + FILE_PATH + "/" + finalPath;
    }

    /**
     * 将文件名重新编码
     *
     * @param originalFileExt 原始的文件扩展名
     * @return 编码后的文件名
     */
    public String encodeFilename(String originalFileExt) {
        return DateUtils.getUnsignedLocalDateTimeStr() +  "-" + UUIDUtils.generateSimpleUUID() + "-" + originalFileExt;
    }

    /**
     * 获取最终路径
     *
     * @param filename 文件名
     */
    public String getFilePath(String filename,String filePath)
    {
        String path = LocalStorageConfig.getFilePath() + "/" + filePath + "/" + filename;
        File finalFile = new File(path);
        if (!finalFile.getParentFile().exists())
        {
            finalFile.getParentFile().mkdirs();
        }
        return path;
    }

}
