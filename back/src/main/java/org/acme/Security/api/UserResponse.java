package org.acme.Security.api;

import java.util.Set;

public record UserResponse(String username, Set<String> roles) { }

