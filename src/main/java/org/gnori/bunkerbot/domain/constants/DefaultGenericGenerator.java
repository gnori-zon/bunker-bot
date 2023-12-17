package org.gnori.bunkerbot.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultGenericGenerator {

    public static final String NAME = "sequence_generator";
    public static final String STRATEGY = "org.hibernate.id.enhanced.SequenceStyleGenerator";

    public static class Params {

        public static final String SEQUENCE_NAME = "sequence_name";
        public static final String INITIAL_VALUE = "initial_value";
        public static final String INCREMENT_SIZE = "increment_size";
        public static final String DEFAULT_VALUE = "1";
    }

    public static  final String POSTGRES_ENTITY_SEQUENCE_SUFFIX = "_id_seq";
}
