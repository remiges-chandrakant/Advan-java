package tech.remiges.workshop.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tech.remiges.workshop.Entity.User;
import tech.remiges.workshop.Request.WorkshopRequest;
import tech.remiges.workshop.Utils.CommonUtils;
import tech.remiges.workshop.Utils.JwtUtils;
import tech.remiges.workshop.WorkshopResponse.WorkshopResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private JwtUtils jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody WorkshopRequest loginReq) {

        String email = loginReq.getData().get("email").toString();
        String password = loginReq.getData().get("password").toString();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            email = authentication.getName();
            User user = new User(email, "");
            String token = jwtUtil.createToken(user);

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);

            WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                    "");
            response.set_serverTs(Instant.now());

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, "Invalid
            // username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username");
        } catch (Exception e) {
            // ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,
            // e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
