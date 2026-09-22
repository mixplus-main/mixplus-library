package com.mixplus.library.network;

import java.util.Map;

public record Response(
        int statusCode,
        Map<String, Object> body
) {
}
