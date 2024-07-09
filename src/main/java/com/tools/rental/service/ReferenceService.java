package com.tools.rental.service;

import com.tools.rental.domain.ToolCodeDigest;
import com.tools.rental.enumeration.Brand;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReferenceService {
    public List<ToolCodeDigest> getToolCodes() {
        List<ToolCodeDigest> toolCodeDigests = new ArrayList<>();

        for (var toolCode : ToolCode.values()) {
            final ToolType toolType = toolCode.getToolType();
            final Brand brand = toolCode.getBrand();

            toolCodeDigests.add(new ToolCodeDigest(toolCode.name(),
                                                   toolType.name(),
                                                   toolType.getDescription(),
                                                   brand.name(),
                                                   brand.getDescription(),
                                                   toolType.getWeekdayCharge(),
                                                   toolType.getWeekendCharge(),
                                                   toolType.getHolidayCharge()));
        }
        return toolCodeDigests;
    }
}
