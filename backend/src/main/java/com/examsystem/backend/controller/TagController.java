package com.examsystem.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.examsystem.backend.dto.TagDto;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private ITagService tagService;

    @PostMapping()
    public ResponseMessage<TagDto> addTag(@Validated @RequestBody TagDto tag) {
        TagDto newTag = tagService.addTag(tag);
        if (newTag == null) {
            return ResponseMessage.error(502, "创建标签失败");
        }
        return ResponseMessage.success(newTag);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<String> deleteTag(@PathVariable Integer id) {
        boolean isDeleted = tagService.deleteTag(id);
        if (!isDeleted) {
            return ResponseMessage.error(502, "删除标签失败");
        }
        return ResponseMessage.success("标签删除成功");
    }

    @GetMapping("/{id}")
    public ResponseMessage<TagDto> getTagById(@PathVariable Integer id) {
        TagDto tag = tagService.getTagById(id);
        if (tag == null) {
            return ResponseMessage.error(404, "标签未找到");
        }
        return ResponseMessage.success(tag);
    }

    @GetMapping()
    public ResponseMessage<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        if (tags == null || tags.isEmpty()) {
            return ResponseMessage.error(404, "未找到任何标签");
        }
        return ResponseMessage.success(tags);
    }

    @PutMapping()
    public ResponseMessage<TagDto> updateTag(@Validated @RequestBody TagDto tag) {
        TagDto updatedTag = tagService.updateTag(tag);
        if (updatedTag == null) {
            return ResponseMessage.error(502, "更新标签失败");
        }
        return ResponseMessage.success(updatedTag);
    }
}
