package com.startup.domain.example.support;

import com.startup.domain.example.entity.Example;
import com.startup.domain.example.error.ExampleErrorCode;
import com.startup.domain.example.error.ExampleException;
import com.startup.domain.example.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleReader {

    private final ExampleRepository exampleRepository;

    public Example findById(Long exampleId) {
        return exampleRepository.findById(exampleId)
                .orElseThrow(() -> new ExampleException(ExampleErrorCode.EXAMPLE_NOT_FOUND));
    }
}
