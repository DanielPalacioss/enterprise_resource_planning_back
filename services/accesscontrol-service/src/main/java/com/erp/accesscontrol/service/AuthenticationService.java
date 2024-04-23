package com.erp.accesscontrol.service;

import com.erp.accesscontrol.dto.AuthenticationRequestDTO;
import com.erp.accesscontrol.dto.AuthenticationResponseDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO login(AuthenticationRequestDTO authRequest);
}
