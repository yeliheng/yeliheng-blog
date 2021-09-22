package com.yeliheng.blogsystem.controller.article;

import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Tag;
import com.yeliheng.blogsystem.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private ITagService tagService;

    @PostMapping()
    public CommonResponse<Object> add(@Validated @RequestBody Tag tag){
        tagService.addTag(tag);
        return CommonResponse.success();
    }

    @DeleteMapping()
    public CommonResponse<Object> delete(@RequestParam Long id){
        tagService.deleteTag(id);
        return CommonResponse.success();
    }

    @PutMapping()
    public CommonResponse<Object> update(@RequestBody Tag tag){
        tagService.updateTag(tag);
        return CommonResponse.success();
    }

    @GetMapping()
    public CommonResponse<List<Tag>> getCategories(){
        return CommonResponse.success(tagService.getTags());
    }
}
