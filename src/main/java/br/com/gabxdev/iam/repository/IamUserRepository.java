package br.com.gabxdev.iam.repository;

import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.iam.response.IamUserWithPermissionsDTO;
import br.com.gabxdev.user.projection.LoginResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IamUserRepository extends JpaRepository<IamUser, Long> {

    Optional<IamUser> findUserByEmailIgnoreCase(String email);

    Boolean existsByEmailIgnoreCaseAndAccountId(String email, UUID accountId);

    @Query("""
            SELECT  new br.com.gabxdev.iam.response.IamUserWithPermissionsDTO(
                    user.firstName,
                    user.lastName,
                    user.email,
                    user.accountId,
                    uscp.serviceCatalog.id,
                    uscp.permissionLevel)
            FROM IamUser user
            INNER JOIN IamUserServiceCatalogPermission uscp
                ON user.id = uscp.iamUser.id
            WHERE user.id = :iamUserId
            AND user.accountId = :accountId
            """)
    List<IamUserWithPermissionsDTO> findIamUserWithPermissions(Long iamUserId, UUID accountId);

    @Query("""
            SELECT new br.com.gabxdev.user.projection.LoginResponse(acc.id, acc.password)
            FROM IamUser acc
            WHERE LOWER(acc.email) = LOWER(:email)
            AND acc.accountId = :accountId
            """)
    Optional<LoginResponse> findIdByEmailIgnoreCase(String email, UUID accountId);
}
