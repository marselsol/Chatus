package com.chatus.messagerouter.utils.mappers;

import com.chatus.messagerouter.dto.MessageDto;
import com.chatus.messagerouter.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
        MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

        MessageDto messageToMessageDto(Message message);
        Message messageDtoToMessage(MessageDto messageDto);
}
