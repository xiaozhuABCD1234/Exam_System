package com.examsystem.backend.controller;

import org.springframework.web.bind.annotation.*;
import com.examsystem.backend.dto.tag.TagIn;
import com.examsystem.backend.dto.tag.TagOut;
import com.examsystem.backend.pojo.ResponseMessage;
import com.examsystem.backend.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private ITagService tagService;

    @PostMapping()
    public ResponseMessage<TagOut> addTag(@RequestBody TagIn tag) {
        TagOut newTag = tagService.addTag(tag);
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
    public ResponseMessage<TagOut> getTagById(@PathVariable Integer id) {
        TagOut tag = tagService.getTagById(id);
        if (tag == null) {
            return ResponseMessage.error(404, "标签未找到");
        }
        return ResponseMessage.success(tag);
    }

    @GetMapping()
    public ResponseMessage<List<TagOut>> getAllTags() {
        List<TagOut> tags = tagService.getAllTags();
        if (tags == null || tags.isEmpty()) {
            return ResponseMessage.error(404, "未找到任何标签");
        }
        return ResponseMessage.success(tags);
    }

    @PutMapping("/{id}")
    public ResponseMessage<TagOut> updateTag(@PathVariable Integer id, @RequestBody TagIn tag) {
        TagOut updatedTag = tagService.updateTag(id, tag);
        if (updatedTag == null) {
            return ResponseMessage.error(502, "更新标签失败");
        }
        return ResponseMessage.success(updatedTag);
    }
}
