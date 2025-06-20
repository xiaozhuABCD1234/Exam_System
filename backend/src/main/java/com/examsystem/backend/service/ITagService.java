package com.examsystem.backend.service;

import com.examsystem.backend.dto.tag.TagIn;
import com.examsystem.backend.dto.tag.TagOut;

import java.util.List;

public interface ITagService {

    TagOut addTag(TagIn tag);

    boolean deleteTag(Integer id);

    TagOut getTagById(Integer id);

    List<TagOut> getAllTags();

    TagOut updateTag(Integer id, TagIn tag);
}
