import jakarta.persistence.*
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity
import ru.mylabs.mylabsbackend.model.entity.User
import kotlin.random.Random


@Entity
class ConfirmationToken(

    @Column(name = "confirmation_token")
    val confirmationToken: String,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
) : AbstractEntity()