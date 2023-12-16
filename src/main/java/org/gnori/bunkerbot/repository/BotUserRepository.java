package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotUserRepository extends CommonRepository<BotUser> {
}
