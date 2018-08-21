package shop.dev.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	@Query(value = "SELECT r.id as id, r.roleName as roleName " + "FROM RoleEntity r "
			+ "WHERE roleName like :name")
	public Map<Object, Object> getRoleByRoleName(@Param("name") String name);

	@Query(nativeQuery = true, value = 
	"SELECT role_id " + 
	"FROM user_role " + 
	"WHERE user_role.user_id = :userId")
	public List<Object[]> getRoleByUser(@Param("userId") Long userId);
	
		
}
