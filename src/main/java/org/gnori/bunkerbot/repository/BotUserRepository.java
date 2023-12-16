package org.gnori.bunkerbot.repository;

import org.gnori.bunkerbot.domain.BotUser;
import org.gnori.bunkerbot.repository.abstracts.CommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotUserRepository extends CommonRepository<BotUser> {

    Optional<BotUser> findByChatId(Long chatId);

    @Query("""
            select bu
            from BotUser bu
            where bu.isAdmin = true
            """)
    List<BotUser> findAllAdmin();

    List<BotUser> findAllByIsActive(Boolean isActive);
}
