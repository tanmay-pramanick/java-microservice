package com.java.microservice.service;

import com.java.microservice.dto.UserDTO;
import com.java.microservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HelperService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> userSearchByCriteriaQuery(Map<String, String> requestParams) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        for(Map.Entry<String,String> entry: requestParams.entrySet()){
            /*  like query */
            predicates.add(cb.like(cb.lower(user.get(entry.getKey())), "%" + entry.getValue().toLowerCase() + "%"));

            /*  equal query */
//            predicates.add(cb.equal(
//                    cb.lower(user.get(entry.getKey())),
//                    entry.getValue().toLowerCase()
//            ));

        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<User> query = entityManager.createQuery(cq);
        List<User> resultList = query.getResultList();

        return resultList;
    }

    public void copyNonNullFieldsFromDto(UserDTO source, User target) {
        try {
            for (Field dtoField : UserDTO.class.getDeclaredFields()) {
                dtoField.setAccessible(true);
                Object value = dtoField.get(source);
                if (value != null) {
                    try {
                        Field entityField = User.class.getDeclaredField(dtoField.getName());
                        entityField.setAccessible(true);
                        entityField.set(target, value);
                    } catch (NoSuchFieldException ignored) {
                        // ignore fields not present in User entity
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error copying fields", e);
        }
    }
}
