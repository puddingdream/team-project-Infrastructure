package com.startup.domain.example.service;

import com.startup.domain.example.dto.ExampleCreateRequest;
import com.startup.domain.example.dto.ExampleResponse;
import com.startup.domain.example.dto.ExampleStatusUpdateRequest;
import com.startup.domain.example.dto.ExampleUpdateRequest;
import com.startup.domain.example.entity.Example;
import com.startup.domain.example.enums.ExampleStatus;
import com.startup.domain.example.error.ExampleErrorCode;
import com.startup.domain.example.error.ExampleException;
import com.startup.domain.example.repository.ExampleRepository;
import com.startup.domain.example.support.ExampleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;
    private final ExampleReader exampleReader;

    @Transactional
    public ExampleResponse createExample(ExampleCreateRequest request) {
        validateTitleNotDuplicated(request.title());

        ExampleStatus status = request.status() == null || request.status().isBlank()
                ? ExampleStatus.DRAFT
                : ExampleStatus.from(request.status());

        Example example = Example.builder()
                .title(request.title())
                .content(request.content())
                .status(status)
                .build();

        return ExampleResponse.from(exampleRepository.save(example));
    }

    @Transactional(readOnly = true)
    public List<ExampleResponse> getExamples(String status) {
        List<Example> examples = status == null || status.isBlank()
                ? exampleRepository.findAllByOrderByCreatedAtDesc()
                : exampleRepository.findAllByStatusOrderByCreatedAtDesc(ExampleStatus.from(status));

        return examples.stream()
                .map(ExampleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExampleResponse getExample(Long exampleId) {
        return ExampleResponse.from(exampleReader.findById(exampleId));
    }

    @Transactional
    public ExampleResponse updateExample(Long exampleId, ExampleUpdateRequest request) {
        Example example = exampleReader.findById(exampleId);
        validateTitleNotDuplicatedForUpdate(request.title(), exampleId);

        example.update(
                request.title(),
                request.content(),
                ExampleStatus.from(request.status())
        );

        return ExampleResponse.from(example);
    }

    @Transactional
    public ExampleResponse updateExampleStatus(Long exampleId, ExampleStatusUpdateRequest request) {
        Example example = exampleReader.findById(exampleId);
        example.changeStatus(ExampleStatus.from(request.status()));
        return ExampleResponse.from(example);
    }

    @Transactional
    public void deleteExample(Long exampleId) {
        Example example = exampleReader.findById(exampleId);
        exampleRepository.delete(example);
    }

    private void validateTitleNotDuplicated(String title) {
        if (exampleRepository.existsByTitle(title)) {
            throw new ExampleException(ExampleErrorCode.DUPLICATED_EXAMPLE_TITLE);
        }
    }

    private void validateTitleNotDuplicatedForUpdate(String title, Long exampleId) {
        if (exampleRepository.existsByTitleAndIdNot(title, exampleId)) {
            throw new ExampleException(ExampleErrorCode.DUPLICATED_EXAMPLE_TITLE);
        }
    }
}
