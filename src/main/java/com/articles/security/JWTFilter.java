package com.articles.security;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class JWTFilter extends GenericFilterBean {

    private final JWTProvider JWTProvider;

    public JWTFilter(JWTProvider JWTProvider) {
        this.JWTProvider = JWTProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = JWTProvider.resolveToken((HttpServletRequest) servletRequest);
        List<String> updateTokens = null;
        boolean isValidToken = false;
        if (token != null) {
            try {
                isValidToken = JWTProvider.validateToken(token);
            } catch (ExpiredJwtException e) {
                updateTokens = JWTProvider.resolveRefreshToken();
                if (updateTokens != null) {
                    isValidToken = true;
                    token = updateTokens.get(0);
                }
            }
            if (isValidToken) {
                Authentication authentication = JWTProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            if (updateTokens != null && updateTokens.size() == 2) {
                HttpServletResponse response = JWTProvider.setResponse(updateTokens,
                        (HttpServletResponse) servletResponse);
                filterChain.doFilter(servletRequest, response);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
