package ru.itis.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.shop.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
