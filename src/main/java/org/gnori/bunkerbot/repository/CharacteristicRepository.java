package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.Characteristic;
import org.gnori.bunkerbot.domain.CharacteristicType;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CharacteristicRepository extends CommonRepository<Characteristic> {

    List<Characteristic> findAllByCharacteristicType(CharacteristicType characteristicType);


    @Query(value = """
            with opposite_ids as (
                select cho.opposite_characteristic_id as id
                from characteristics ch
                    join characteristic_opposites cho on ch.id = cho.characteristic_id
                where ch.id in :uniqueCharacteristicIds
            )
            select ch.*
            from characteristics ch
            where ch.id in (select oids.id from opposite_ids oids) and ch.type_id = :typeId
            """, nativeQuery = true)
    List<Characteristic> findOppositesFor(Long typeId, Set<Long> uniqueCharacteristicIds);

    @Query(value = """
            select ch.*
            from characteristics ch
            where ch.id not in :bouUserCharacteristics and ch.type_id = :typeId
            """, nativeQuery = true)
    List<Characteristic> findAllNotIn(Long typeId, Set<Long> bouUserCharacteristics);
}
