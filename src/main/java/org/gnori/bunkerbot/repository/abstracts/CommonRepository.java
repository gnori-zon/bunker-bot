package org.gnori.bunkerbot.domain.repository.abstracts;

import org.gnori.bunkerbot.domain.abstracts.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
