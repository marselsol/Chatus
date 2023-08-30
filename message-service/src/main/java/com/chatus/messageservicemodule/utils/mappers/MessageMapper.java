package com.chatus.messageservicemodule.utils.mappers;

import com.chatus.messageservicemodule.dto.MessageDto;
import com.chatus.messageservicemodule.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
        MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

        MessageDto messageToMessageDto(Message message);
        Message messageDtoToMessage(MessageDto messageDto);
}
