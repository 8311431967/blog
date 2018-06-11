package kunrong.kr.blog.web.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthSecurityService authSecurityService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(authSecurityService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/auth").permitAll()
                .antMatchers("/api/v1/auth/logout").permitAll()
                .antMatchers("/api/v1/member").permitAll()
                .antMatchers("/api/v1/hotel").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers("/api/v1/check/**").access("hasAuthority('BLOG_ACTIVE')")
                .antMatchers("/api/v1/manager/**").access("hasAuthority('MANAGER')")
                .antMatchers("/api/v1/reserve/**").access("hasAuthority('MEMBER_PAUSE')")
                .antMatchers("/api/v1/reserve").access("hasAuthority('MEMBER_ACTIVE')")
                .antMatchers("/api/v1/room").access("hasAuthority('BLOG_ACTIVE')")
                .antMatchers("/api/v1/room/**/unfinished").access("hasAuthority('BLOG_PAUSE')")
                .antMatchers("/api/v1/**").access("hasAuthority('USER_BASE')")
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .cors()
        ;
    }

}
