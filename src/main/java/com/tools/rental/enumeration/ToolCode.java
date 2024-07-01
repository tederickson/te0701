package com.tools.rental.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ToolCode {
    CHNS(ToolType.CHAINSAW, Brand.STIHL),
    LADW(ToolType.LADDER, Brand.WERNER),
    JAKD(ToolType.JACKHAMMER, Brand.DE_WALT),
    JAKR(ToolType.JACKHAMMER, Brand.RIDGID);

    final ToolType toolType;
    final Brand brand;
}
