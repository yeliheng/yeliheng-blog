package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.config.LocalStorageConfig;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.InternalServerException;
import com.yeliheng.blogcommon.exception.NotFoundException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.DateUtils;
import com.yeliheng.blogcommon.utils.ExcelUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogcommon.utils.WordUtils;
import com.yeliheng.blogframework.storage.FileSystem;
import com.yeliheng.blogframework.storage.FileUtils;
import com.yeliheng.blogframework.storage.adapter.KodoStorageAdapter;
import com.yeliheng.blogframework.storage.adapter.LocalStorageAdapter;
import com.yeliheng.blogsystem.domain.AritcleTag;
import com.yeliheng.blogsystem.domain.Article;
import com.yeliheng.blogsystem.mapper.ArticleMapper;
import com.yeliheng.blogsystem.mapper.ArticleTagMapper;
import com.yeliheng.blogsystem.mapper.CategoryMapper;
import com.yeliheng.blogsystem.service.IArticleService;
import com.yeliheng.blogsystem.utils.UserUtils;
import com.yeliheng.blogsystem.vo.ArticleVo;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ArticleServiceImpl implements IArticleService {

    private static final int ARTICLE_URL_LENGTH = 8;

    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private ExcelUtils excelUtils;

    /**
     * 新增文章
     *
     * @param articleVo 文章请求
     */
    @Override
    @Transactional
    public Long addArticle(ArticleVo articleVo) {
        Article article = new Article();
        BeanUtils.copyProperties(articleVo, article);
        article.setUserId(userUtils.getLoginUserId());
        int wordCount = WordUtils.wordCount(articleVo.getContent());
        article.setWords(wordCount);
        article.setReadingTime(WordUtils.calReadingTimeByWords(wordCount));
        article.setUrl(generateUrl());
        if(StringUtils.isNotNull(article.getCategoryId())) {
            if(categoryMapper.existsById(article.getCategoryId()) <= 0) {
                throw new GeneralException("分类不存在，请修改后重新发布！");
            }
        }
        if(StringUtils.isNotEmpty(articleVo.getCreatedAt())) {
            article.setCreatedAt(DateUtils.stringToDateTime(articleVo.getCreatedAt()));
        } else {
            article.setCreatedAt(DateUtils.getLocalDateTime());
        }
        article.setUpdatedAt(DateUtils.getLocalDateTime());

        articleMapper.addArticle(article);
        //插入文章标签
        insertArticleTag(article);
        return article.getId();
    }

    /**
     * 删除文章
     *
     * @param articleId 文章Id
     */
    @Transactional
    @Override
    public void deleteAritcle(Long articleId) {
        if(articleMapper.deleteArticleById(articleId) <= 0)
            throw new GeneralException("删除失败，文章可能不存在");
        //删除文章与标签关联
        deleteArticleAllTags(articleId);
    }

    /**
     * 修改文章
     *
     * @param article   文章实体
     */
    @Transactional
    @Override
    public void updateArticle(Article article) {
        int wordCount = WordUtils.wordCount(article.getContent());
        article.setWords(wordCount);
        article.setReadingTime(WordUtils.calReadingTimeByWords(wordCount));
        if(StringUtils.isNotNull(article.getCategoryId()))
            if(categoryMapper.existsById(article.getCategoryId()) <= 0)
                throw new GeneralException("分类不存在，请修改后重新发布！");
        int rows = articleMapper.updateArticle(article);
        if(rows <= 0) throw new GeneralException("更新失败，文章可能不存在");
        //删除该文章的所有标签
        deleteArticleAllTags(article.getId());
        //插入标签
        insertArticleTag(article);
    }

    /**
     * 获取分页的文章
     *
     * @param page     第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticles(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticles();
        return new PageInfo<>(articleList);
    }

    /**
     * 获取后台分页的文章
     *
     * @param page     第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesBacked(Integer page, Integer pageSize,Article article) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesBacked(article);
        return new PageInfo<>(articleList);
    }

    /**
     * 通过Id获取文章
     *
     * @param articleId 文章Id
     * @return 文章实体
     */
    @Override
    public Article getArticleById(Long articleId) {
        Article article = articleMapper.getArticleById(articleId);
        articleMapper.increaseViewsByArticleId(articleId);
        if(article == null)
            throw new NotFoundException("文章不存在");
        return article;
    }

    /**
     * 通过Url获取文章
     *
     * @param articleUrl 文章持久化Url
     * @return 文章实体
     */
    @Override
    public Article getArticleByUrl(String articleUrl) {
        Article article = articleMapper.getArticleByUrl(articleUrl);
        if(article == null)
            throw new NotFoundException("文章不存在");
        articleMapper.increaseViewsByArticleId(article.getId());
        return article;
    }

    /**
     * 通过Id获取文章-后台
     *
     * @param articleId 文章Id
     * @return 文章实体
     */
    @Override
    public Article getArticleByIdBacked(Long articleId) {
        return articleMapper.getArticleByIdBacked(articleId);
    }

    /**
     * 通过分类获取文章
     *
     * @param categoryId 分类Id
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesByCategory(Long categoryId,Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesByCategoryId(categoryId);
        return new PageInfo<>(articleList);
    }

    /**
     * 通过标签获取文章
     *
     * @param tagId 标签Id
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesByTag(Long tagId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesByTagId(tagId);
        return new PageInfo<>(articleList);
    }

    /**
     * 导出文章
     *
     * @param article 文章筛选条件
     * @return 导出的excel路径
     */
    @Override
    public String exportArticle(Article article) {
        List<Article> articleList =  articleMapper.exportArticlesBacked(article);
        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("文章列表");
        exportParams.setSheetName("文章列表");
        return excelUtils.exportExcel(exportParams,articleList,Article.class);
    }

    @Override
    public String exportAllToMarkdown() {
        List<Article> articleList = articleMapper.exportArticlesBacked(null);
        // 导出到临时目录
        String exportDir = String.format("%s/articles_export_tmp/markdown", LocalStorageConfig.getFilePath());
        if(!FileUtils.mkdirIfNotExists(exportDir)) {
            throw new InternalServerException("创建目录失败!");
        }
        for(Article article : articleList){
            String fileName = String.format("%s_%s.md", DateUtils.dateToUnsignedString(article.getCreatedAt()), article.getTitle());
            String filePath = String.format("%s/%s", exportDir, fileName);
            // 判断文章分类是否存在
            if(article.getCategory() != null) {
                String categoryDir = String.format("%s/%s", exportDir, article.getCategory().getCategoryName());
                if(!FileUtils.mkdirIfNotExists(categoryDir)) {
                    throw new InternalServerException("创建目录失败!");
                }
                filePath = String.format("%s/%s", categoryDir, fileName);
            }

            // 生成md文件
            try {
                PrintWriter writer = new PrintWriter(filePath);
                writer.println(writeArticleMeta(article));
                writer.println(article.getContent());
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new UnexpectedException();
            }
        }

        // 压缩成zip
        String relativePath = "articles_export_tmp/zip";
        String zipFilePath = String.format("%s/%s", LocalStorageConfig.getFilePath(), relativePath);
        if(!FileUtils.mkdirIfNotExists(zipFilePath)) {
            throw new InternalServerException("创建目录失败!");
        }

        String zipName = String.format("%s_articles_archive.zip", DateUtils.dateToUnsignedString(DateUtils.getLocalDateTime()));
        String zipFinalPath = String.format("%s/%s", zipFilePath, zipName);
        try {
            FileOutputStream fos = new FileOutputStream(zipFinalPath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            zipDirectory(exportDir, exportDir, zipOut);
            zipOut.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }

        // 删除临时目录
        LocalStorageAdapter localStorageAdapter = new LocalStorageAdapter();
        FileSystem fileSystem = new FileSystem(localStorageAdapter);
        fileSystem.deleteDirectory(exportDir);

        return String.format("/%s/%s", relativePath, zipName);
    }

    /**
     * 递归压缩目录
     * @param sourceDirPath 源目录
     * @param currentDirPath 当前目录
     * @param zipOut zip输出流
     * @throws IOException IO异常
     */
    private static void zipDirectory(String sourceDirPath, String currentDirPath, ZipOutputStream zipOut) throws IOException {
        File dir = new File(currentDirPath);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                zipDirectory(sourceDirPath, file.getPath(), zipOut);
            } else {
                String entryName = getEntryName(sourceDirPath, file);
                ZipEntry entry = new ZipEntry(entryName);
                zipOut.putNextEntry(entry);
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, bytesRead);
                }
                fis.close();
            }
        }
    }

    /**
     * 获取zip中文件的路径
     * @param sourceDirPath 源目录
     * @param file 文件
     * @return zip中文件的路径
     */
    private static String getEntryName(String sourceDirPath, File file) {
        String entryName = file.getPath().substring(sourceDirPath.length() + 1);
        return entryName.replace(File.separator, "/");
    }


    /**
     * 生成文章元信息
     * @param article 文章实体
     * @return 文章元信息
     */
    private String writeArticleMeta(Article article) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("title: ").append(article.getTitle()).append("\n");
        stringBuilder.append("summary: ").append(article.getSummary()).append("\n");
        if(StringUtils.isNotEmpty(article.getTags())) {
            stringBuilder.append("tags: ");
            article.getTags().stream().forEach(tag -> stringBuilder.append("#").append(tag.getTagName()).append(" "));
            stringBuilder.append("\n");
        }
        if(article.getCategory() != null) {
            stringBuilder.append("category: ").append(article.getCategory().getCategoryName()).append("\n");
        }
        stringBuilder.append("url: ").append(article.getUrl()).append("\n");
        stringBuilder.append("date: ").append(DateUtils.dateToDateTimeString(article.getCreatedAt())).append("\n");
        stringBuilder.append("last edit: ").append(DateUtils.dateToDateTimeString(article.getUpdatedAt())).append("\n");
        stringBuilder.append("<!--more-->");
        return stringBuilder.toString();
    }

    @Override
    public String uploadImage(MultipartFile file) {
        final String BUCKET = "yeliheng-blog";
        final String IMG_RELATIVE_PATH = "blog-images/new";
        String fileName = FileUtils.encodeFileNameWithUUID(FilenameUtils.getExtension(file.getOriginalFilename()));
        String filePath = String.format("%s/%s", IMG_RELATIVE_PATH, fileName);

        KodoStorageAdapter kodoStorageAdapter = new KodoStorageAdapter(BUCKET);
        FileSystem fileSystem = new FileSystem(kodoStorageAdapter);
        try {
            fileSystem.write(file, filePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }
        return fileSystem.getPublicURL(filePath);
    }

    /**
     *
     * 新增文章标签
     * @param article 文章实体
     */
    public void insertArticleTag(Article article){
        Long[] tags = article.getTagIds();
        if(StringUtils.isNotNull(tags)){
            //新增关联
            List<AritcleTag> list = new ArrayList<>();
            for(Long tagId : tags){
                AritcleTag aritcleTag = new AritcleTag();
                aritcleTag.setArticleId(article.getId());
                aritcleTag.setTagId(tagId);
                list.add(aritcleTag);
            }
            if(list.size() > 0){
                articleTagMapper.setArticleTags(list);
            }
        }
    }

    /**
     *
     * 删除某篇文章的所有标签
     * @param articleId 文章Id
     */
    public void deleteArticleAllTags(Long articleId){
        articleTagMapper.deleteArticleAllTags(articleId);
    }

    private String generateUrl() {
       return RandomStringUtils.random(ARTICLE_URL_LENGTH, "0123456789abcdef");
    }

}
