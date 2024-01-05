package si.ape.messaging.models.converters;

import si.ape.messaging.lib.UserConversation;
import si.ape.messaging.models.entities.UserConversationEntity;

/**
 * The UserConversationConverter class is used to convert between the UserConversation DTO and UserConversation Entity
 * classes.
 */
public class UserConversationConverter {

    /**
     * Converts a UserConversationEntity object  to a UserConversation DTO object.
     *
     * @param entity The UserConversationEntity object to convert.
     * @return The converted UserConversation DTO object.
     */
    public static UserConversation toDto(UserConversationEntity entity) {

        UserConversation dto = new UserConversation();
        dto.setUserId(entity.getUserId());
        dto.setConversationId(entity.getConversationId());
        return dto;

    }

    /**
     * Converts a UserConversation DTO object to a UserConversationEntity object.
     *
     * @param dto The UserConversation DTO object to convert.
     * @return The converted UserConversationEntity object.
     */
    public static UserConversationEntity toEntity(UserConversation dto) {

        UserConversationEntity entity = new UserConversationEntity();
        entity.setUserId(dto.getUserId());
        entity.setConversationId(dto.getConversationId());
        return entity;

    }


}
