package pl.edu.ug.search;

import org.springframework.data.jpa.domain.Specification;
import pl.edu.ug.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {

    private SearchCriteria criteria;

    public UserSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        switch (criteria.getOperation()) {
            case EQUALITY:
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION:
                return cb.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN:
                return cb.greaterThan(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN:
                return cb.lessThan(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            case LIKE:
                return cb.like(root.<String> get(
                        criteria.getKey()), criteria.getValue().toString());
            default:
                return null;
        }
    }
}
