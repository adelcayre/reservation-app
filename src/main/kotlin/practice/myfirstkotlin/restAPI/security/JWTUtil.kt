package practice.myfirstkotlin.restAPI.security
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.Date
import io.jsonwebtoken.security.Keys

object JWTUtil {
    private val SECRET_KEY =  Keys.hmacShaKeyFor("324y23ijfhdbaljkfbn3luiqhursdlkjvafjehafioasdhfijsdfhlkj".toByteArray())

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
            .signWith(SECRET_KEY)
            .compact()
    }

    fun parseJwt(token: String): Claims {
        val parser = Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()

        return parser.parseClaimsJws(token).body
    }

    fun validateToken(token: String?): Boolean {
        return try {
            val claims = parseJwt(token ?: "")
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }



}