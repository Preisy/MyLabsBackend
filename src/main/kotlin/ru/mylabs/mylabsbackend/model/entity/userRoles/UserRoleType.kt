package ru.mylabs.mylabsbackend.model.entity.userRoles

enum class UserRoleType {
    ADMIN,
    USER;

    override fun toString(): String {
        return super.toString().uppercase()
    }

//    @Getter
//    @Component("UserRoleType")
//    class SpringComponent {
//        val ADMIN = UserRoleType.ADMIN
//        val MODERATOR = UserRoleType.MODERATOR
//        val USER = UserRoleType.USER
//    }
}