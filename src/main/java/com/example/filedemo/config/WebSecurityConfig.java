package com.example.filedemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder BCryptPasswordEncoder;
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(BCryptPasswordEncoder);
//		 auth.userDetailsService(userDetailsService)
//	        .passwordEncoder(passwordEncoder());
	}

    @Bean
    public JWTAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
		http.authorizeRequests().antMatchers("/swagger-ui/**", "/v2/api-docs", "/configuration/ui",
				"/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll();
        http.authorizeRequests().antMatchers("/register/**","/authenticate","/api/excel/upload/**","/login/**","/confirm/**","/ws","/topic/messages","/our-websocket","/","/pay","/bills/export","/ajouteretaffecterCartaUser/{iduser}","/addproducttocart/product/{idproduit}/cart/{idcart}/qte/{qte}","/addproducttocart/product/{idproduit}/cart/{idcart}/qte/{qte}","/passercommand/cart/{idc}","/web/import/excel","/web/import/excel/colis").permitAll();
        http.authorizeRequests().antMatchers("/user/update-user","/imageUser/{id}","/imagePersonnel/{id}","/generateUniqueUsername/{nom}").permitAll();
        http.authorizeRequests().antMatchers("/carteGrisePersonnel/{id}").permitAll();
        http.authorizeRequests().antMatchers("/permisPersonnel/{id}").permitAll();
        http.authorizeRequests().antMatchers("/authenticate").permitAll();
        http.authorizeRequests().antMatchers("/changepwd/{pwd}").permitAll();
        http.authorizeRequests().antMatchers("/Forum/Commentaire22/{idCom}").permitAll();
        http.authorizeRequests().antMatchers("/logoFournisseur/{id}").permitAll();
        http.authorizeRequests().antMatchers("/patenteFournisseur/{id}").permitAll();
        
        http.authorizeRequests().antMatchers("admin").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("Publication/ajouterCommentaire/{idPub}").hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers("all1").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/Forum/Publication/Commentaire/{idCom}").hasAuthority("ADMIN");

        

        http.authorizeRequests().antMatchers("user/getpubById/{User_id}").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/payin6months/cmd/{idcmd}/compte/{idcpt1}/{idcpt2}/{iddeliv}","/addandassigndelveryabill/{iddeliv}","/genrateAndDownloadQRCode/{codeText}/{width}/{height}","/currency-converter/from/{from}/to/{to}/amount/{amount}","/currency-exchange/from/{from}/to/{to}","/dell/{userid}").permitAll();
      http.authorizeRequests().antMatchers("forum/findbyid").hasAuthority("ADMIN");

        http.authorizeRequests().antMatchers("/Forum/Publication/{idPub}/{idUser}/**","/user/deletePublicationById/{idpub}","deleteCommentaireById/{idcomment}").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("ajouterContrat**","//affecterContratAEmploye/{idcontrat}/{iduser}/**","/getNombreEmployeJPQL/**","/getSalaireMoyen/**","/Forum/Publications/rating/**","/aff","/affecter/{iduser}/{idRole}","/all","getSalaireparid/{idemp}","getCarburant1","/allpub").hasAuthority("ADMIN");
        
        http.authorizeRequests().antMatchers("user/getAllPublicationJPQL/**","user/updatePublicationById/{id}/{newtopic}","/user/getAllCommentaireJPQL","mettreAjourPublicationById/{id}/{newdescription}","/evaluate/{idpub}","/modifie-user","/searchuser/{username}","allpub1","/Forum/AddPublication").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers("AddPublication/{idUser}","/Publication/{idPub}/{idUser}/ajouterCommentaire","/Listepardate/**","/searchtopic","/bestcomment/**","/Forum/file","/bestevaluation/**","/Forum/Publication/AddRating/**","/Forum/Send/{idSender}/{idRecipient}/**","/Forum/Receive/{idSender}/{idRecipient}/**","/AddPublication2/{idUser}").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().anyRequest().authenticated();
             //   .anyRequest().authenticated().and()
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(userDetailsService, jwtTokenUtil,
                "Authorization");
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/authenticate/**","/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

//    @Bean
//    public BCryptPasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/*"));
        configuration.setAllowedMethods(Arrays.asList("GET,POST,DELETE,PUT,OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }}
