package com.app.mapper;

import com.app.dto.account.AccountAdminDto;
import com.app.dto.account.AccountDto;
import com.app.form.account.CreateAccountAdminForm;
import com.app.form.account.UpdateAccountAdminForm;
import com.app.form.account.UpdateProfileAdminForm;
import com.app.model.Account;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
public interface AccountMapper extends ABasicMapper {
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "kind", target = "kind")
    @Mapping(target = "username", expression = "java(toLowerCase(createAccountAdminForm.getUsername()))")
    @Mapping(target = "email", expression = "java(toLowerCase(createAccountAdminForm.getEmail()))")
    @BeanMapping(ignoreByDefault = true)
    Account fromCreateAccountAdminFormToEntity(CreateAccountAdminForm createAccountAdminForm);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "email", expression = "java(toLowerCase(updateAccountAdminForm.getEmail()))")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateAccountAdminFormToEntity(UpdateAccountAdminForm updateAccountAdminForm, @MappingTarget Account account);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @BeanMapping(ignoreByDefault = true)
    void fromUpdateProfileAdminFormToEntity(UpdateProfileAdminForm updateProfileAdminForm, @MappingTarget Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDto")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAccountAdminDto")
    AccountAdminDto fromEntityToAccountAdminDto(Account account);

    @IterableMapping(elementTargetType = AccountAdminDto.class, qualifiedByName = "fromEntityToAccountAdminDto")
    List<AccountAdminDto> fromEntityListToAccountAdminDtoList(List<Account> accounts);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Named("fromEntityToAccountDto")
    AccountDto fromEntityToAccountDto(Account account);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromEntityToAccountDto")
    List<AccountDto> fromEntityListToAccountDtoList(List<Account> accounts);
}
