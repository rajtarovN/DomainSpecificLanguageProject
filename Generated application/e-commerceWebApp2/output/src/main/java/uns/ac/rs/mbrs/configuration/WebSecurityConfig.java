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
                .antMatchers("/api/login").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/api/bill/").permitAll()
                .antMatchers("/api/bill/{id}").permitAll()
                .antMatchers("/api/bill/make-with-id/{id}").permitAll()
                .antMatchers("/api/itemWithPrice/").permitAll()
                .antMatchers("/api/itemWithPrice/{id}").permitAll()
                .antMatchers("/api/basket/").permitAll()
                .antMatchers("/api/basket/{id}").permitAll()
                .antMatchers("/api/basket/{basketId}/{itemId}/{quantity}").permitAll()
                .antMatchers("/api/basket/{basketId}/{itemId}").permitAll()
                .antMatchers("/api/item/").permitAll()
                .antMatchers("/api/item/{id}").permitAll()
                .antMatchers("/api/action/").permitAll()
                .antMatchers("/api/action/{id}").permitAll()
                .antMatchers("/api/address/").permitAll()
                .antMatchers("/api/address/{id}").permitAll()
                .antMatchers("/api/seller/").permitAll()
                .antMatchers("/api/seller/{id}").permitAll()
                .antMatchers("/api/admin/").permitAll()
                .antMatchers("/api/admin/{id}").permitAll()
                .antMatchers("/api/customer/").permitAll()
                .antMatchers("/api/customer/{id}").permitAll()
                .antMatchers("/api/item/getByAction/{actionId}").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/auth/register").permitAll()
                .anyRequest().authenticated().and()
                .cors();


        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/login");
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/register");
        web.ignoring().antMatchers(HttpMethod.GET, "/logout");

        web.ignoring().antMatchers(HttpMethod.PUT, "/api/cusomer");
        web.ignoring().antMatchers(HttpMethod.PUT, "/api/address/{id}");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}