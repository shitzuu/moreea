package dev.shitzuu.moreea.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter implements Filter {

    @Value("${moreea.security.accesstoken}")
    private String accessToken;

    @Override
    public void init(FilterConfig filterConfig) {
        // This method is not implemented, because we do not require any initialization steps.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (this.isAuthorized(request)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your request is not authorized.");
    }

    @Override
    public void destroy() {
        // This method is not implemented, because we do not require any destroy steps.
    }

    public boolean isAuthorized(ServletRequest request) {
        return request instanceof HttpServletRequest httpRequest
                && httpRequest.getHeader("Authorization") != null
                && httpRequest.getHeader("Authorization").equals("Bearer " + accessToken);
    }
}
