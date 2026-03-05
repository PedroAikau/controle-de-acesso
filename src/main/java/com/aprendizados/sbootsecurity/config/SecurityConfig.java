package com.aprendizados.sbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // para dar permissões via controller
public class SecurityConfig {

    // Usamos um Security Filter Chain, o .build cria ele
    // antes de criar precisamos configurar ele,  já que quando criado, ele substitui a config padrão do spring

    // role -> grupo de usuário (perfil de usuario) -> Master, gerente, vendedor
    // authority -> permissões -> cadastrar usuario, acessar tela de relatorio
    @Bean
    public SecurityFilterChain securityFilterChain
    (HttpSecurity http, SenhaMasterAuthenticationProvider senhaMasterAuthenticationProvider, CustomFilter customFilter, CustomAuthenticationProvider customAuthenticationProvider) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/public").permitAll(); // permite a url publica ser acessada sem auth
                    customizer.requestMatchers("/rh/debug").permitAll();
                    //customizer.requestMatchers("/admin").hasRole("ADMIN"); // protege todas as todas pedindo auth, so posso chamar esse por ultimo
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // cria um customizer padrão
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(senhaMasterAuthenticationProvider)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customFilter, org.springframework.security.web.authentication.www.BasicAuthenticationFilter.class)
                //.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class) // faz com que nosso customFilter seja executado primeiro
                .build();
    }

    // Configurando credenciais de acesso
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails commonUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .authorities("USER")
                .build();
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(commonUser, adminUser);
    }

    // dizendo como fazer a verificação de senha
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    // fazendo não precisar escrever ROLE antes no SenhaMasterAuthentication
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}
