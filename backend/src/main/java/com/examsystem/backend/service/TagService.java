package com.examsystem.backend.service;

import com.examsystem.backend.dto.tag.TagIn;
import com.examsystem.backend.dto.tag.TagOut;
import com.examsystem.backend.entity.Tag;
import com.examsystem.backend.repository.TagRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService implements ITagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagOut addTag(TagIn tag) {
        Tag newTag = new Tag();
        BeanUtils.copyProperties(tag, newTag);
        newTag = tagRepository.save(newTag);
        TagOut tagOut = new TagOut();
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
    public TagOut getTagById(Integer id) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();
            TagOut tagOut = new TagOut();
            BeanUtils.copyProperties(tag, tagOut);
            return tagOut;
        }
        return null;
    }

    @Override
    public List<TagOut> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagOut> tagOuts = new ArrayList<>();
        for (Tag tag : tags) {
            TagOut tagOut = new TagOut();
            BeanUtils.copyProperties(tag, tagOut);
            tagOuts.add(tagOut);
        }
        return tagOuts;
    }

    @Override
    public TagOut updateTag(Integer id, TagIn tag) {
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if (tagOptional.isPresent()) {
            Tag existingTag = tagOptional.get();
            if (tag.getName() != null) {
                existingTag.setName(tag.getName());
            }
            existingTag = tagRepository.save(existingTag);
            TagOut tagOut = new TagOut();
            BeanUtils.copyProperties(existingTag, tagOut);
            return tagOut;
        }
        return null;
    }
}
