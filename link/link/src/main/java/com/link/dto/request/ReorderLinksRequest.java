package com.link.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReorderLinksRequest {
    private List<Long> linkIds;
}
