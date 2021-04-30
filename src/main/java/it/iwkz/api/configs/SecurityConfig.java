package it.iwkz.api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.iwkz.api.configs.jwt.JwtAuthenticationEntryPoint;
import it.iwkz.api.configs.jwt.JwtAuthenticationFilter;
import it.iwkz.api.services.JwtUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, jsr250Enabled = true, prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure( AuthenticationManagerBuilder authenticationManagerBuilder ) throws Exception {
        authenticationManagerBuilder.userDetailsService( jwtUserDetailService ).passwordEncoder( passwordEncoder() );
    }

    @Bean( BeanIds.AUTHENTICATION_MANAGER )
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint( jwtAuthenticationEntryPoint ).and().sessionManagement()
                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and().authorizeRequests()
                        .antMatchers( "/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css",
                                        "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/ws/**", "/**/*.js" )
                        .permitAll().antMatchers( "/api/auth/**" ).permitAll().antMatchers( HttpMethod.GET, "/api/income/**", "/api/bill/**" ).permitAll()
                        .anyRequest().authenticated();
        http.addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
    }
}
