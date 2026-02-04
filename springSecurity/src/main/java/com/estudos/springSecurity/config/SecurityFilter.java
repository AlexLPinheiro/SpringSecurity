package com.estudos.springSecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig){
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizedHeader = request.getHeader("Authorization");//Coleta o header de autorização do request
        if (Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) { //verifica se o header está vazio ou se começa com Bearer
            String token = authorizedHeader.substring(7); //Cria um substring para puxar apenas o jwt
            Optional<JWTUserData> optUser = tokenConfig.validateToken(token);//Cria um objeto optional para não retornar nullException, pois o token config pode ou não retornar um optUser
            if (optUser.isPresent()){//Verifica se o optUser é nulo ou não
                JWTUserData userData = optUser.get();// Como já está verificado, instancia o objeto
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, null, Collections.emptyList()); //Verifica se o userData é valido

                SecurityContextHolder.getContext().setAuthentication(authentication);//Cria um contexto para todos os endpoints autenticados
            }
            filterChain.doFilter(request, response);
        }
        else{
            filterChain.doFilter(request,response);
        }

        }
    }

