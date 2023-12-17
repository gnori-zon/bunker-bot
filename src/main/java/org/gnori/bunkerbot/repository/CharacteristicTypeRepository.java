package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.CharacteristicType;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicTypeRepository extends CommonRepository<CharacteristicType> {

    Optional<CharacteristicType> findByNameEqualsIgnoreCase(String name);
}
