package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.Characteristic;
import org.gnori.bunkerbot.domain.CharacteristicType;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends CommonRepository<Characteristic> {

    List<Characteristic> findAllByCharacteristicType(CharacteristicType characteristicType);
}
