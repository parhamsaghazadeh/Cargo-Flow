package com.cargoflow.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String serverUrl;
    private String realm;
    private String adminClientId;
    private String adminClientSecret;
    private String adminUsername;
    private String adminPassword;

    public String getServerUrl() { return serverUrl; }
    public void setServerUrl(String serverUrl) { this.serverUrl = serverUrl; }
    public String getRealm() { return realm; }
    public void setRealm(String realm) { this.realm = realm; }
    public String getAdminClientId() { return adminClientId; }
    public void setAdminClientId(String adminClientId) { this.adminClientId = adminClientId; }
    public String getAdminClientSecret() { return adminClientSecret; }
    public void setAdminClientSecret(String adminClientSecret) { this.adminClientSecret = adminClientSecret; }
    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }
    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}