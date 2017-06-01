package org.bsuir.labs.spp.service;

import org.bsuir.labs.spp.domain.Tag;
import org.bsuir.labs.spp.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(Tag tag) {
        Tag result = tagRepository.save(tag);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Tag> findAll(Pageable pageable) {
        Page<Tag> result = tagRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Tag findOne(Long id) {
        Tag tag = tagRepository.findOne(id);
        return tag;
    }

    public void delete(Long id) {
        tagRepository.delete(id);
    }
}
