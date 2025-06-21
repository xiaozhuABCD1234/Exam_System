package com.examsystem.backend.service;

import com.examsystem.backend.dto.TagDto;

import java.util.List;

public interface ITagService {

    TagDto addTag(TagDto tag);

    boolean deleteTag(Integer id);

    TagDto getTagById(Integer id);

    List<TagDto> getAllTags();

    TagDto updateTag(TagDto tag);
}
