package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.Characteristic;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends CommonRepository<Characteristic> {
}
