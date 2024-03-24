package com.patika.kredinbizdeservice.mapper;

import com.patika.kredinbizdeservice.controller.model.*;
import com.patika.kredinbizdeservice.model.*;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class ModelMapper {
    public static final ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    public abstract CreditCard toCreditCard(CreditCardDto creditCardDto);

    public abstract CreditCardDto toCreditCardDto(CreditCard creditCard);

    public abstract List<CreditCardDto> toCreditCardDtoList(List<CreditCard> creditCards);

    public abstract Bank toBank(BankDto bankDto);

    public abstract BankDto toBankDto(Bank bank);

    public abstract List<BankDto> toBankDtoList(List<Bank> banks);

    public abstract Campaign toCampaign(CampaignDto campaignDto);

    public abstract CampaignDto toCampaignDto(Campaign campaign);

    public abstract List<CampaignDto> toCampaigntoList(List<Campaign> campaigns);

    public abstract User toUser(UserDto userDto);

    public abstract UserDto toUserDto(User user);

    public abstract List<UserDto> toUserDtoList(List<User> users);

}
