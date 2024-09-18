package com.app.controller;

import com.app.constant.AppConstant;
import com.app.constant.ErrorCode;
import com.app.dto.ApiMessageDto;
import com.app.dto.ResponseListDto;
import com.app.dto.setting.SettingAdminDto;
import com.app.dto.setting.SettingDto;
import com.app.form.setting.CreateSettingForm;
import com.app.form.setting.FindByGroupNameForm;
import com.app.form.setting.FindByKeyNameForm;
import com.app.form.setting.UpdateSettingForm;
import com.app.mapper.SettingMapper;
import com.app.model.Setting;
import com.app.model.criteria.SettingCriteria;
import com.app.repository.SettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/setting")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class SettingController extends ABasicController {
    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private SettingMapper settingMapper;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SET_V')")
    public ApiMessageDto<SettingAdminDto> get(@PathVariable("id") Long id) {
        Setting setting = settingRepository.findById(id).orElse(null);
        if (setting == null) {
            return makeErrorResponse(ErrorCode.SETTING_ERROR_NOT_FOUND, "Not found setting");
        }
        return makeSuccessResponse(settingMapper.fromEntityToSettingAdminDto(setting), "Get setting success");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SET_L')")
    public ApiMessageDto<ResponseListDto<List<SettingAdminDto>>> list(SettingCriteria settingCriteria, Pageable pageable) {
        Page<Setting> settings = settingRepository.findAll(settingCriteria.getCriteria(), pageable);
        ResponseListDto<List<SettingAdminDto>> responseListObj = new ResponseListDto<>();
        responseListObj.setContent(settingMapper.fromEntityListToSettingAdminDtoList(settings.getContent()));
        responseListObj.setTotalPages(settings.getTotalPages());
        responseListObj.setTotalElements(settings.getTotalElements());
        return makeSuccessResponse(responseListObj, "Get list setting success");
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<SettingDto>>> autoComplete(SettingCriteria settingCriteria) {
        Pageable pageable = PageRequest.of(0, 10);
        settingCriteria.setStatus(AppConstant.STATUS_ACTIVE);
        Page<Setting> settings = settingRepository.findAll(settingCriteria.getCriteria(), pageable);
        ResponseListDto<List<SettingDto>> responseListObj = new ResponseListDto<>();
        responseListObj.setContent(settingMapper.fromEntityListToSettingDtoAutoCompleteList(settings.getContent()));
        responseListObj.setTotalPages(settings.getTotalPages());
        responseListObj.setTotalElements(settings.getTotalElements());
        return makeSuccessResponse(responseListObj, "Get list setting success");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SET_C')")
    public ApiMessageDto<String> create(@Valid @RequestBody CreateSettingForm createSettingForm, BindingResult bindingResult) {
        if (settingRepository.findFirstByGroupNameAndKeyName(createSettingForm.getGroupName(), createSettingForm.getKeyName()).isPresent()) {
            return makeErrorResponse(ErrorCode.SETTING_ERROR_EXISTED_GROUP_NAME_AND_KEY_NAME, "Group name and key name existed");
        }
        settingRepository.save(settingMapper.fromCreateSettingFormToEntity(createSettingForm));
        return makeSuccessResponse(null, "Create setting success");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SET_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateSettingForm updateSettingForm, BindingResult bindingResult) {
        Setting setting = settingRepository.findById(updateSettingForm.getId()).orElse(null);
        if (setting == null) {
            return makeErrorResponse(ErrorCode.SETTING_ERROR_NOT_FOUND, "Not found setting");
        }
        if ((!updateSettingForm.getGroupName().equals(setting.getGroupName()) || !updateSettingForm.getKeyName().equals(setting.getKeyName())) &&
                settingRepository.findFirstByGroupNameAndKeyName(updateSettingForm.getGroupName(), updateSettingForm.getKeyName()).isPresent()) {
            return makeErrorResponse(ErrorCode.SETTING_ERROR_EXISTED_GROUP_NAME_AND_KEY_NAME, "Group name and key name existed");
        }
        settingMapper.fromUpdateSettingFormToEntity(updateSettingForm, setting);
        settingRepository.save(setting);
        return makeSuccessResponse(null, "Update setting success");
    }

    @GetMapping(value = "/find-by-key", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<SettingDto>> findByKey(FindByKeyNameForm findByKeyNameForm) {
        List<Setting> settings = settingRepository.findByKeyNames(findByKeyNameForm.getKeyNames(), false);
        return makeSuccessResponse(settingMapper.fromEntityListToSettingDtoList(settings), "Find key name success");
    }

    @GetMapping(value = "/find-by-group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<SettingDto>> findByGroup(FindByGroupNameForm findByGroupNameForm) {
        List<Setting> settings = settingRepository.findByGroupNames(findByGroupNameForm.getGroupNames(), false);
        return makeSuccessResponse(settingMapper.fromEntityListToSettingDtoList(settings), "Find group name success");
    }

    @GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<SettingDto>> listSetting() {
        List<Setting> settings = settingRepository.findAllByIsSystem(false);
        return makeSuccessResponse(settingMapper.fromEntityToSettingDtoPublicList(settings), "Get list setting success");
    }
}
