package com.erp.accescontrol.service;

import com.erp.gateway.model.AuthenticationRequest;
import com.erp.gateway.model.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest authRequest);
}
