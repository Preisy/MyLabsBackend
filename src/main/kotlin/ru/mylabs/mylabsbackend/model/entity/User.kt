package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.mylabs.mylabsbackend.configuration.RoleHierarchy
import ru.mylabs.mylabsbackend.model.dto.request.ChangeRoleRequest
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRole
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRoleType


@Entity
@JsonIgnoreProperties("upassword", "password")
class User(
    @Column(name = "name", length = 255, nullable = false)
    var uname: String,
    @Column(length = 255, nullable = false)
    var email: String,
    @Column(name = "password", length = 255, nullable = false)
    var uPassword: String,
    var contact: String,
    @Column(length = 255, nullable = false)

    @JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator::class,
        property = "name"
    )
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var roles: MutableSet<UserRole> = mutableSetOf(UserRole(UserRoleType.USER))

) : AbstractEntity(), UserDetails {



    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val roleHierarchy = RoleHierarchy.hierarchyList
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()

        var j = 0
        Loop@ for (i in roleHierarchy.indices) {
            for (role in roles) {
                if (role.name == roleHierarchy[i]) {
                    j = i
                    break@Loop
                }
            }
        }
        for (i in j until roleHierarchy.size)
            authorities.add(SimpleGrantedAuthority("ROLE_${roleHierarchy[i].name}"))
//        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
//        for (role in roles) {
//            authorities.add(SimpleGrantedAuthority("ROLE_${role.name}"))
//        }
        return authorities
    }

    @JsonIgnore
    override fun getPassword() = uPassword

    @JsonIgnore
    override fun getUsername() = email

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
    fun containsRole(user: User, roleRequest: ChangeRoleRequest): Boolean {
        var userHasRole = false
        user.roles.forEach {
            if (it.name == roleRequest.name)
                userHasRole = true
        }
        return userHasRole
    }

    fun removeRole(user: User, roleRequest: ChangeRoleRequest): User {
        user.roles.forEach {
            if (it.name == roleRequest.name)
                user.roles.remove(it)
        }
        return user
    }
}