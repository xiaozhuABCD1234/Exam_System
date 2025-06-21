package com.examsystem.backend.service;

import com.examsystem.backend.dto.TagDto;
import com.examsystem.backend.dto.question.QuestionOut;
import com.examsystem.backend.entity.Question;
import com.examsystem.backend.entity.Tag;
import com.examsystem.backend.repository.TagRepository;
import com.examsystem.backend.utils.DtoUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService implements ITagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagDto addTag(TagDto tag) {
        Tag newTag = new Tag();
        BeanUtils.copyProperties(tag, newTag);
        newTag = tagRepository.save(newTag);
        TagDto tagOut = new TagDto();
        BeanUtils.copyProperties(newTag, tagOut);
        return tagOut;
    }

    @Override
    public boolean deleteTag(Integer id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public TagDto getTagById(Integer id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            TagDto tagOut = new TagDto();
            BeanUtils.copyProperties(tag, tagOut);
            return tagOut;
        }
        return null;
    }

    @Override
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagOuts = new ArrayList<>();
        for (Tag tag : tags) {
            TagDto tagOut = new TagDto();
            BeanUtils.copyProperties(tag, tagOut);
            tagOuts.add(tagOut);
        }
        return tagOuts;
    }

    @Override
    public TagDto updateTag(TagDto tag) {
        Optional<Tag> tagOptional = tagRepository.findById(tag.getId());
        if (tagOptional.isPresent()) {
            Tag existingTag = tagOptional.get();
            if (tag.getName() != null) {
                existingTag.setName(tag.getName());
            }
            existingTag = tagRepository.save(existingTag);
            TagDto tagOut = new TagDto();
            BeanUtils.copyProperties(existingTag, tagOut);
            return tagOut;
        }
        return null;
    }

    @Override
    public List<QuestionOut> getQuestionsByTagId(Integer tagId) {
        List<Question> questions = tagRepository.findQuestionsByTagId(tagId);
        return questions.stream()
                .map(DtoUtils::questionToQuestionOut)
                .collect(Collectors.toList());
    }
}
