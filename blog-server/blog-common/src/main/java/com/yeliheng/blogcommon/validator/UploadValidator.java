package com.yeliheng.blogcommon.validator;

import com.yeliheng.blogcommon.annotation.Upload;
import com.yeliheng.blogcommon.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UploadValidator implements ConstraintValidator<Upload, MultipartFile> {

    private long maxSize;

    private List<String> allowType;

    private List<String> denyType;


    @Override
    public void initialize(Upload upload) {
        this.maxSize = upload.maxSize();
        this.allowType = Arrays.stream(upload.allowType().split(","))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        this.denyType = Arrays.stream(upload.denyType().split(","))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file == null || file.isEmpty()){
            return false;
        }

        if(file.getSize() > maxSize * 1024 * 1024){
            return false;
        }

        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)){
            return false;
        }

        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (StringUtils.isNotEmpty(denyType) && denyType.contains(fileType.toLowerCase())){
            return false;
        }
        return StringUtils.isEmpty(allowType) || allowType.contains(fileType.toLowerCase());
    }
}
