package com.pharmacy.healthcare.oauth;

import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.User;
import com.pharmacy.healthcare.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    @Qualifier("patientService")
    PatientService patientService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        OAuthModel userModel;
        Patient patient;
        if (user.getType().equals("patient")) {
            patient = patientService.getTransactionalPatient(user.getUser_id());
            additionalInfo.put("mappedDoctor", patient.getDoctor());
        }
        userModel = new OAuthModel(user.getUser_id(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getType());

        additionalInfo.put("user", userModel);


        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }


}
