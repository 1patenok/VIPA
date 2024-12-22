package com.example.vipa.service.specification;

import com.example.vipa.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostSpecificationBuilder {
    private final List<SearchCriteria> params = new ArrayList<>();

    public PostSpecificationBuilder with(String key, String operation, String value/*, boolean orPredicate*/) {
        log.info("inside with(), key: {}, operation: {}, value: {}", key, operation, value);
        params.add(new SearchCriteria()
                .setKey(key)
                .setOperation(operation)
                .setValue(value));
                //.setOrPredicate(orPredicate));
        return this;
    }

    public Specification<Post> build() {
        if (params.isEmpty()) {
            return null;
        }
        Specification<Post> result = new PostSpecification(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new PostSpecification(params.get(i)));
            /*result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new PostSpecification(params.get(i)))
                    : Specification.where(result).and(new PostSpecification(params.get(i)));*/
        }
        return result;
    }
}
