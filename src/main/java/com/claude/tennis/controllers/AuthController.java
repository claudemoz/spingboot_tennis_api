package com.claude.tennis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import com.claude.tennis.dto.LoginDto;
import com.claude.tennis.security.JwtConfig;

@Tag(name = "Auth API")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtConfig jwtConfig;

  private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

  @Operation(summary = "Authenticates user", description = "Authenticates user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User is logged in."),
      @ApiResponse(responseCode = "403", description = "User credentials are not valid."),
      @ApiResponse(responseCode = "400", description = "Login or password is not provided.")
  })

  @PostMapping("/login")
  public ResponseEntity<LoginDto> login(@RequestBody @Valid LoginDto credentials, HttpServletRequest request,
      HttpServletResponse response) {
    // UsernamePasswordAuthenticationToken authenticationToken = new
    // UsernamePasswordAuthenticationToken(
    // credentials.login(), credentials.password());
    // Authentication authentication =
    // authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    // SecurityContext securityContext = SecurityContextHolder.getContext();
    // securityContext.setAuthentication(authentication);
    // securityContextRepository.saveContext(securityContext, request, response);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        credentials.login(), credentials.password());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    String jwt = jwtConfig.createToken(authentication);
    return new ResponseEntity<>(new LoginDto(authentication.getName(), jwt), HttpStatus.OK);
  }

  // @Operation(summary = "Logs off authenticated user", description = "Logs off
  // authenticated user")
  // @ApiResponses(value = {
  // @ApiResponse(responseCode = "200", description = "User is logged out."),
  // @ApiResponse(responseCode = "403", description = "No user is logged in.")
  // })
  // @GetMapping("/logout")
  // public void logout(Authentication authentication, HttpServletRequest request,
  // HttpServletResponse response) {
  // securityContextLogoutHandler.logout(request, response, authentication);
  // }

}
