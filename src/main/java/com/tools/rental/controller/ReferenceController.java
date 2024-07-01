package com.tools.rental.controller;

import com.tools.rental.domain.ToolCodeDigest;
import com.tools.rental.service.ReferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Enable the UI to get the reference values from the backend.
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Reference API")
@RequestMapping("v1/reference")
public class ReferenceController {
    private final ReferenceService referenceService;

    @Operation(summary = "Get all tool codes")
    @GetMapping(value = "/tool-codes", produces = "application/json")
    public List<ToolCodeDigest> getToolCodes() {
        return referenceService.getToolCodes();
    }
}
