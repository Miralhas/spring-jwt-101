package github.com.miralhas.jwt101.config.security;

import jakarta.servlet.*;

import java.io.IOException;

public class TestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Este é um filtro de teste");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
