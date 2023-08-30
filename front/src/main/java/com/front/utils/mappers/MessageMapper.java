package com.front.utils.mappers;

import com.front.dto.MessageDto;
import com.front.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
        MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

        MessageDto messageToMessageDto(Message message);
        Message messageDtoToMessage(MessageDto messageDto);
}
