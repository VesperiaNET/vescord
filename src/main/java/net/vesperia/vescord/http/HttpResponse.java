package net.vesperia.vescord.http;

import java.util.List;
import java.util.Map;

public record HttpResponse(int statusCode, String body, Map<String, List<String>> headers) {
}
