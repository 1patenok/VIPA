package com.example.vipa.service.specification;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SearchCriteria {
    private String key;
    private String operation;
    private String value;
    private boolean orPredicate;
}
