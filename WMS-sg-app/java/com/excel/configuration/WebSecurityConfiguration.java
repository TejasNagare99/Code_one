package com.excel.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.excel.service.UserMasterAuthenticationImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserMasterAuthenticationImpl UserMasterAuthentication;
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;
	@Autowired
	private Environment env;

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();
	}

	@Bean
	public StrictHttpFirewall httpFirewall() {
		System.out.println("Firewalll");
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowedHttpMethods(Arrays.asList("GET","POST","HEAD"));
		return firewall;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.parentAuthenticationManager(authenticationManagerBean())
				.userDetailsService(UserMasterAuthentication).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(
				// Assets from angular
				"/assets/**", "/**.js", "/**.js.map", "/**.css", "/**.woff2", "/**.woff","/**.jpg","/**.ttf",
				// angular URLs
				"/", "/login",
				// rest APIs login related
				"/rest/isValidationActive", "/rest/get-company-data", "/rest/get-Attempts", "/rest/authenticate",
				"/rest/generateOtp", "/rest/isValidationActive", "/rest/getCaptcha", "/rest/getLoginQuestions",
				"/rest/get-secret-question", "/rest/send-password", "/rest/get-otpattempts", "/rest/EraseOtp",
				"/rest/change-temporary-password", "/rest/sign-in",
				// ERP Rest APIs
				"/rest/confirmDispatch", "/rest/confirmDispatchManually", "/rest/stockReco",
				"/rest/stockRecoQuarantine", "/rest/tranReco", "/rest/tranReco_Quarantine",
				"/rest/get-batch-stk-reco-report", "/rest/stockRecoQuarantine_report", "/rest/ManualDispatchApproval",
				// GoApptive Rest APIs
				"/rest/goApptiveFieldstaff", "/rest/territoryDetailsForGoapptive", "/rest/dispatchDetailsForGoapptive",
				"/rest/get-Goaptive-teambrand-data",
				// Paths for reports & files
				"/dcr-activity-images/**", "/expense-images/**", "/leave-images/**", "/show-report/**", "/show-pdf/**",
				"/show-grn/**", "/show-allocation/**", "/show-stkwth/**", "/show-iaa/**", "/show-crm/**",
				"/show-article-docs/**","/show-article-docs-zipped/**",
				// password Related
				//"/rest/ksybfelsjcywmkghpsef",
				// Mail Approval
				"/rest/saveApprovalDataListViaMail",
				//article delivery
				"/rest/saveArticleSchemeApprovalViaMail",
				//articel scheme master
				"/rest/saveArticleSchemeMasterApprovalViaMail",
				//scheme valdity extension
				"/rest/saveSchemeValidityExtensionApprovalViaMail",
				// captcha validation
				"/captcha-validate",
				// update pod for direct to doctors
				"/rest/doctor-reply-service",
				// update pod for stockists
				"/rest/stockist-reply-service",
				//for SSO pfizer 
				"/sso/pfz_cfastk").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(this.unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).sessionFixation().migrateSession();

		// disable all headers
		// http.headers().disable();

		// Xframe
		http.headers().frameOptions().deny();

		// CSP
//		http.headers().contentSecurityPolicy(
//				"script-src 'self' 'unsafe-inline' 'unsafe-eval' https://www.gstatic.com https://lh3.googleusercontent.com https://sampro-pfizerindia.com https://fonts.googleapis.com https://registry.npmjs.org"
//						+ "form-action 'self';object-src 'none'; "
//						+ "style-src 'self' 'unsafe-inline' https://lh3.googleusercontent.com https://sampro-pfizerindia.com https://fonts.googleapis.com https://registry.npmjs.org"
//						+ "frame-ancestors 'none';block-all-mixed-content;");
		
		http.headers().contentSecurityPolicy(
			    "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://www.gstatic.com https://lh3.googleusercontent.com https://sampro-pfizerindia.com https://fonts.googleapis.com https://registry.npmjs.org;" +
			    "form-action 'self';" +
			    "object-src 'none';" +
			    "style-src 'self' 'unsafe-inline' https://lh3.googleusercontent.com https://sampro-pfizerindia.com https://fonts.googleapis.com https://registry.npmjs.org https://www.gstatic.com;" + // Added https://www.gstatic.com
			    "frame-ancestors 'none';" +
			    "block-all-mixed-content;");


		
		

		// HSTS Security
		http.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);

		// XSS Protection
		http.headers().xssProtection().block(true);

		// ssl
		http.requiresChannel().anyRequest().requiresSecure();

		// referrer policy
		http.headers().referrerPolicy(ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN);
		// featurer policy
		http.headers().featurePolicy("geolocation 'none';microphone 'none';camera 'none'");

//        http.portMapper() do not uncomment following
//        .http(Integer.parseInt(environment.getProperty("8200"))) // http port defined in yml config file
//        .mapsTo(Integer.parseInt(environment.getProperty("8200"))); 

		// Session do not uncomment following
		// http.sessionManagement().maximumSessions(1);
//		 http.sessionManagement().sessionFixation().changeSessionId();
//		 http.sessionManagement()
//		  .invalidSessionUrl("/error");

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	// comment below method to turn security on
	@Override
	public void configure(WebSecurity web) throws Exception {
		// allow all while testing

		String[] activeProfiles = env.getActiveProfiles();
		if (activeProfiles.length > 0) {
			List<String> list = Arrays.asList(activeProfiles);
			if (Arrays.asList(activeProfiles).contains("stkprod")
					|| Arrays.asList(activeProfiles).contains("ngprodpfz")) {
				System.out.println("prod Environment - security on");
				this.configureForProd(web);
			} else {
				System.out.println("dev Environment - security off");
				this.configureForDev(web);
			}
		} else {
			this.configureForDev(web);
		}
	}

	public void configureForDev(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**");
	}

	public void configureForProd(WebSecurity web) throws Exception {
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
				Arrays.asList(
						"https://www.sampro-pfizerindia.com:8180",
						"https://www.sampro-pfizerindia.com:8100",
						"https://www.sampro-pfizerindia.com:8200",
						"https://www.sampro-pfizerindia.com:8280"));
		
		configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
		configuration.setAllowedHeaders(Arrays.asList("Set-Cookie", "authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "x-auth-token"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}

}
