package com.valerio.userpersistency.security.service;

import com.valerio.userpersistency.persistency.dao.RoleRepository;
import com.valerio.userpersistency.persistency.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ProcessingUserService {
    @Autowired
    RoleRepository roleRepository;

    public List<UserDTO> getFormUsersList() {
        Map<String, List<String>> map = roleRepository.findAll().stream().collect(
                Collectors.groupingBy(
                        Roles::getUsername,
                        Collectors.mapping(Roles::getRolename, Collectors.toList())
                )
        );
        Map<String, List<String>> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.<String, List<String>>comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        AtomicInteger counter = new AtomicInteger(1);

        List<UserDTO> userDTOList = sortedMap.entrySet().stream().map(e -> {
            UserDTO user = new UserDTO();
            user.setuserNum(counter.getAndIncrement());
            user.setUserName(e.getKey());
            user.setUserRoles(e.getValue());
            user.setUserRolesAsString(e.getValue().stream().collect(Collectors.joining(", ")));
            return user;
        }).collect(Collectors.toList());

        return userDTOList;
    }
}
