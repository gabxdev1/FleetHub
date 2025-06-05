package br.com.gabxdev.iam.controller;

import br.com.gabxdev.core.common.HeaderUtils;
import br.com.gabxdev.iam.mapper.IamUserMapper;
import br.com.gabxdev.iam.request.IamUserPostRequest;
import br.com.gabxdev.iam.response.IamUserGetResponse;
import br.com.gabxdev.iam.response.IamUserResponse;
import br.com.gabxdev.iam.service.IamUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iam")
@RequiredArgsConstructor
@Validated
public class IamUserController {

    private final IamUserService iamUserService;

    private final IamUserMapper iamUserMapper;

    private final HeaderUtils headerUtils;

    @PostMapping(headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<IamUserResponse> createIamUser(@Valid @RequestBody IamUserPostRequest request) {
        var iamUserRequest = iamUserMapper.toEntity(request);

        var iamUser = iamUserService.save(iamUserRequest, request.serviceCatalogPerm());

        var headerLocation = headerUtils.createHeaderLocation(iamUser.getId().toString());

        var response = iamUserMapper.toIamUserResponse(iamUser);

        return ResponseEntity.created(headerLocation).body(response);
    }

    @GetMapping(path = "/{id}", headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<IamUserGetResponse> findIamUserWithPermissions(@NotNull @PathVariable Long id) {
        var iamUserWithPermissions = iamUserService.findIamUserWithPermissions(id);

        return ResponseEntity.ok(iamUserWithPermissions);
    }
}
