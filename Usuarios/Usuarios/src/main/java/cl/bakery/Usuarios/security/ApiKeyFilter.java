package cl.bakery.Usuarios.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.api.client.util.Value;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-API-KEY";
    private static final String VALID_API_KEY = "123456789ABCDEF";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String path = request.getRequestURI();

        // Permitir Swagger completamente
        if (path.contains("/swagger-ui") ||
            path.contains("/v3/api-docs") ||
            path.contains("/swagger-ui.html") ||
            path.contains("/doc")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Validar API Key en el resto de rutas
        String apiKey = request.getHeader(HEADER_NAME);

        if (apiKey == null || !apiKey.equals(VALID_API_KEY)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("API KEY INVALIDA O AUSENTE");
            return;
        }

        filterChain.doFilter(request, response);
    }
}