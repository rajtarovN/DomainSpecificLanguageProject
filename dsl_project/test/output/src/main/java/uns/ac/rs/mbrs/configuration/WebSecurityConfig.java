package uns.ac.rs.mbrs.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uns.ac.rs.mbrs.security.AuthenticationTokenFilter;
import uns.ac.rs.mbrs.security.EntryPointUnauthorizedHandler;
import uns.ac.rs.mbrs.service.UserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private EntryPointUnauthorizedHandler unauthorizedHandler;

    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(
            EntryPointUnauthorizedHandler unauthorizedHandler,
            UserDetailsService userDetailsService
    ) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();

        httpSecurity
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .sessionManagement()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/api/person/").permitAll()
                .antMatchers("/api/person/{id}").permitAll()
                .antMatchers("/api/address/").permitAll()
                .antMatchers("/api/address/{id}").permitAll()
                .antMatchers("/api/jedan/").permitAll()
                .antMatchers("/api/jedan/{id}").permitAll()
                .antMatchers("/api/dva/").permitAll()
                .antMatchers("/api/dva/{id}").permitAll()
                .antMatchers("/api/tri/").permitAll()
                .antMatchers("/api/tri/{id}").permitAll()
                .antMatchers("/api/product/").permitAll()
                .antMatchers("/api/product/{id}").permitAll()
                .antMatchers("/api/novv/").permitAll()
                .antMatchers("/api/novv/{id}").permitAll()
                .antMatchers("/api/szagor/").permitAll()
                .antMatchers("/api/szagor/{id}").permitAll()
                .antMatchers("/logout").permitAll()
                .anyRequest().authenticated().and()
                .cors();


        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/login");
        web.ignoring().antMatchers(HttpMethod.GET, "/logout");

        web.ignoring().antMatchers(HttpMethod.PUT, "/api/person");
        web.ignoring().antMatchers(HttpMethod.PUT, "/api/address/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}