package com.quickStart.quickStart.DTOs;

import java.util.UUID;

public record LoginResponse(
        UUID id,
        String username,
        String role
) {
}
