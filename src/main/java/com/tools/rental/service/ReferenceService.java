package com.tools.rental.service;

import com.tools.rental.domain.ToolCodeDigest;
import com.tools.rental.enumeration.ToolCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReferenceService {
    public List<ToolCodeDigest> getToolCodes() {
        return Arrays.stream(ToolCode.values())
                .map(t -> new ToolCodeDigest(t.name(),
                                             t.getToolType().name(),
                                             t.getToolType().getDescription(),
                                             t.getBrand().name(),
                                             t.getBrand().getDescription()))
                .toList();
    }
}
