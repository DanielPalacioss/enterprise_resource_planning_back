package com.erp.accesscontrol.service;

import com.erp.accesscontrol.model.AuthenticationRequest;
import com.erp.accesscontrol.model.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authRequest);
}
