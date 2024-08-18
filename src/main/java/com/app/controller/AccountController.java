package com.app.controller;

import com.app.constant.AppConstant;
import com.app.constant.AppEnum;
import com.app.constant.ErrorCode;
import com.app.dto.ApiMessageDto;
import com.app.dto.ResponseListDto;
import com.app.dto.account.AccountAdminDto;
import com.app.dto.account.AccountDto;
import com.app.dto.account.AccountForgetPasswordDto;
import com.app.form.account.*;
import com.app.mapper.AccountMapper;
import com.app.model.Account;
import com.app.model.Group;
import com.app.model.criteria.AccountCriteria;
import com.app.repository.AccountRepository;
import com.app.repository.GroupRepository;
import com.app.service.ApiService;
import com.app.utils.ConvertUtils;
import com.app.utils.ZipUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AccountController extends ABasicController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    ApiService apiService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_V')")
    public ApiMessageDto<AccountAdminDto> get(@PathVariable("id") Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        return makeSuccessResponse(accountMapper.fromEntityToAccountAdminDto(account), "Get account success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_L')")
    public ApiMessageDto<ResponseListDto<List<AccountAdminDto>>> list(AccountCriteria accountCriteria, Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(accountCriteria.getCriteria(), pageable);
        ResponseListDto<List<AccountAdminDto>> responseListObj = new ResponseListDto<>();
        responseListObj.setContent(accountMapper.fromEntityListToAccountAdminDtoList(accounts.getContent()));
        responseListObj.setTotalPages(accounts.getTotalPages());
        responseListObj.setTotalElements(accounts.getTotalElements());
        return makeSuccessResponse(responseListObj, "Get list account success");
    }

    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_C_AD')")
    public ApiMessageDto<String> createAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) {
        Account accountByUsername = accountRepository.findAccountByUsername(createAccountAdminForm.getUsername()).orElse(null);
        if (accountByUsername != null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_USERNAME_EXISTED, "Username existed");
        }
        Account accountByEmail = accountRepository.findAccountByEmail(createAccountAdminForm.getEmail()).orElse(null);
        if (accountByEmail != null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_EMAIL_EXISTED, "Email existed");
        }
        Group group = groupRepository.findById(createAccountAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            return makeErrorResponse(ErrorCode.GROUP_ERROR_NOT_FOUND, "Not found group");
        }
        Account account = accountMapper.fromCreateAccountAdminFormToEntity(createAccountAdminForm);
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getPassword()));
        account.setKind(AppEnum.ACCOUNT_KIND_ADMIN);
        account.setGroup(group);
        accountRepository.save(account);
        return makeSuccessResponse(null, "Create account admin success");
    }

    @PutMapping(value = "/update-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_U_AD')")
    public ApiMessageDto<String> updateAdmin(@Valid @RequestBody UpdateAccountAdminForm updateAccountAdminForm, BindingResult bindingResult) {
        Account account = accountRepository.findById(updateAccountAdminForm.getId()).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        if (updateAccountAdminForm.getEmail() != null && !updateAccountAdminForm.getEmail().equals(account.getEmail())) {
            Account accountByEmail = accountRepository.findAccountByEmail(updateAccountAdminForm.getEmail()).orElse(null);
            if (accountByEmail != null) {
                return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_EMAIL_EXISTED, "Email existed");
            }
        }
        Group group = groupRepository.findById(updateAccountAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            return makeErrorResponse(ErrorCode.GROUP_ERROR_NOT_FOUND, "Not found group");
        }
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateAccountAdminForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getAvatar())) {
            if (!updateAccountAdminForm.getAvatar().equals(account.getAvatar())) {
                apiService.deleteFile(account.getAvatar());
            }
            account.setAvatar(updateAccountAdminForm.getAvatar());
        }
        accountMapper.fromUpdateAccountAdminFormToEntity(updateAccountAdminForm, account);
        account.setGroup(group);
        accountRepository.save(account);
        return makeSuccessResponse(null, "Update account admin success");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ACC_D')")
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        if (account.getIsSuperAdmin()) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN, "Not allow to delete super admin");
        }
        apiService.deleteFile(account.getAvatar());
        accountRepository.deleteById(id);
        return makeSuccessResponse(null, "Delete account success");
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountDto> profile() {
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        return makeSuccessResponse(accountMapper.fromEntityToAccountDto(account), "Get profile success");
    }

    @PutMapping(value = "/update-profile-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfileAdmin(@Valid @RequestBody UpdateProfileAdminForm updateProfileAdminForm, BindingResult bindingResult) {
        Account account = accountRepository.findById(getCurrentUser()).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        if (!passwordEncoder.matches(updateProfileAdminForm.getOldPassword(), account.getPassword())) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_WRONG_PASSWORD, "Old password is incorrect");
        }
        if (StringUtils.isNoneBlank(updateProfileAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateProfileAdminForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileAdminForm.getAvatar())) {
            if (!updateProfileAdminForm.getAvatar().equals(account.getAvatar())) {
                //delete old image
                apiService.deleteFile(account.getAvatar());
            }
            account.setAvatar(updateProfileAdminForm.getAvatar());
        }
        accountMapper.fromUpdateProfileAdminFormToEntity(updateProfileAdminForm, account);
        accountRepository.save(account);
        return makeSuccessResponse(null, "Update profile success");

    }

    @PostMapping(value = "/request-forget-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountForgetPasswordDto> requestForgetPassword(@Valid @RequestBody RequestForgetPasswordForm forgetForm, BindingResult bindingResult) {
        Account account = accountRepository.findAccountByEmail(forgetForm.getEmail()).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        String otp = apiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPasswordCode(otp);
        account.setResetPasswordTime(new Date());
        accountRepository.save(account);
        apiService.sendEmail(account.getEmail(), "OTP: " + otp, "Request forget password successful, please check email", false);
        AccountForgetPasswordDto accountForgetPasswordDto = new AccountForgetPasswordDto();
        String zipUserId = ZipUtils.zipString(account.getId() + ";" + otp);
        accountForgetPasswordDto.setUserId(zipUserId);
        return makeSuccessResponse(accountForgetPasswordDto, "Request forget password successful, please check email");
    }

    @PostMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> forgetPassword(@Valid @RequestBody ResetPasswordForm resetForm, BindingResult bindingResult) {
        String[] unzip = Objects.requireNonNull(ZipUtils.unzipString(resetForm.getUserId())).split(";", 2);
        Long id = ConvertUtils.convertStringToLong(unzip[0]);
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_NOT_FOUND, "Not found account");
        }
        if (account.getAttemptCode() >= AppConstant.MAX_ATTEMPT_FORGET_PASSWORD) {
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_EXCEEDED_NUMBER_OF_INPUT_ATTEMPT_OTP, "Exceeded number of input attempt OTP");
        }
        if (!account.getResetPasswordCode().equals(resetForm.getOtp()) ||
                (new Date().getTime() - account.getResetPasswordTime().getTime() >= AppConstant.MAX_ATTEMPT_FORGET_PASSWORD)) {
            account.setAttemptCode(account.getAttemptCode() + 1);
            accountRepository.save(account);
            return makeErrorResponse(ErrorCode.ACCOUNT_ERROR_OTP_INVALID, "OTP code invalid or has expired");
        }
        account.setResetPasswordCode(null);
        account.setResetPasswordTime(null);
        account.setAttemptCode(null);
        account.setPassword(passwordEncoder.encode(resetForm.getNewPassword()));
        accountRepository.save(account);
        return makeSuccessResponse(null, "Reset password success");
    }
}
