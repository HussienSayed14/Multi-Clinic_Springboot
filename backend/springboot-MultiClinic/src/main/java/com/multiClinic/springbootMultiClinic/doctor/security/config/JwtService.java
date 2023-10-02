package com.multiClinic.springbootMultiClinic.doctor.security.config;

import com.multiClinic.springbootMultiClinic.doctor.Doctor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "66052FE098BBD41A00FA3AE4FB4C62E935279DC1529FD02AE6749D21600192758032A07837EA20370B9784ADD25BFEC410E126AC876BCCB2CD557AB8911A14AF78A31B376CE02AB94496D2A9FE94491198E8EA6E8C7E91D427F0828B28D8E37C3C1AEBA5D4D907D46AD4D818D6B01E3DA88645A04BA94860E26C97DD3CEE9393";
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }
    public int extractID(String jwtToken) {

        return extractClaim(jwtToken, claims -> claims.get("id", Integer.class));
    }




    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }




    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ){
        Doctor doctor = (Doctor) userDetails;
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("id", doctor.getId())
                .claim("fullName", doctor.getFullName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 *10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }


    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

            return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
